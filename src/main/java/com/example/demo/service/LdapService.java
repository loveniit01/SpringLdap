package com.example.demo.service;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.config.LdapConfiguration;
import com.example.demo.constant.Constant;
import com.google.gson.Gson;

@Service
public class LdapService {

	@Autowired
	LdapConfiguration configuration;

	@Autowired
	LdapTemplate ldapTemplate;

	@Value("${ldap.urls}")
	private String ldapUrls;
	@Value("${ldap.base.dn}")
	private String ldapBaseDn;

	public Object personDetails() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		Object pp = authentication.getPrincipal();
		return pp;
	}

	public List<String> getSubTree(String type) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String uid__c = authentication.getName();
		String baseDn__c = "uid="+uid__c+","+ldapBaseDn;
		
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, Constant.INITIAL_CONTEXT_FACTORY);
		env.put(Context.PROVIDER_URL, ldapUrls +baseDn__c);

		DirContext ctx;
		try {
			ctx = new InitialDirContext(env);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		List<String> list = new LinkedList<String>();
		NamingEnumeration results = null;
		try {
			SearchControls controls = new SearchControls();
			controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			results = ctx.search("", "(objectClass=person)", controls);

			Gson gson = new Gson();
			while (results.hasMore()) {
				Map<String, String> data = new HashMap<>();
				SearchResult searchResult = (SearchResult) results.next();
				Attributes attributes = searchResult.getAttributes();
				Attribute attr = attributes.get("cn");
				String cn = (attributes.get("cn") == null) ? null : attr.get().toString();
				attr = attributes.get("sn");
				String sn = (attributes.get("sn") == null) ? null : attr.get().toString();
				attr = attributes.get("manager");
				String manager = (attributes.get("manager") == null) ? null : attr.get().toString();
				attr = attributes.get("uid");
				String uid = (attributes.get("uid") == null) ? null : attr.get().toString();
				attr = attributes.get("ou");
				String ou = (attributes.get("ou") == null) ? null : attr.get().toString();
//	            attr= attributes.get("dc");
				data.put("uid", uid);
				data.put("ou", ou);
				data.put("cn", cn);
				data.put("sn", sn);
				data.put("Manager", manager);

				if (type.equalsIgnoreCase(Constant.MANAGER) && manager != null) {
					String json = gson.toJson(manager);
					list.add(json);
				} else if (type.equalsIgnoreCase(Constant.SUBTREE) && manager == null) {
					String json = gson.toJson(data);
					list.add(json);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
