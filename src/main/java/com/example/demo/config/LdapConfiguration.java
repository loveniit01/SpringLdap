package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;


@Configuration
public class LdapConfiguration {

	
	@Value("${ldap.urls}")
	private String ldapUrls;
	@Value("${ldap.base.dn}")
	private String ldapBaseDn;
	@Value("${ldap.username}")
	private String ldapuser;
	@Value("${ldap.password}")
	private String ldapPrincipalPassword;
	@Value("${ldap.user.dn.pattern}")
	private String ldapUserDnPattern;
	@Value("${ldap.enabled}")
	private String ldapEnabled;
	

	@Bean
	public LdapContextSource contextSource() {
		LdapContextSource contextSource = new LdapContextSource();
		contextSource.setUrl(ldapUrls);
		contextSource.setBase(ldapBaseDn);
		contextSource.setUserDn("");
		contextSource.setPassword("");
		contextSource.setPooled(false);
	    contextSource.afterPropertiesSet();
		

		return contextSource;
	}

	@Bean
	public LdapTemplate ldapTemplate() {
		return new LdapTemplate(contextSource());
	}


	

}