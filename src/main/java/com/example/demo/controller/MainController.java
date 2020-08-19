package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.constant.Constant;
import com.example.demo.service.LdapService;

@RestController
public class MainController {

	@GetMapping("/")
	public ResponseEntity<?> login() {
		return ResponseEntity.status(HttpStatus.OK).body(ldapService.personDetails());
	}
	
	@GetMapping("/getManager")
	public ResponseEntity<?> getManager()
	{
		String type = Constant.MANAGER;
		return ResponseEntity.status(HttpStatus.OK).body(ldapService.getSubTree(type));
	}
	
	@GetMapping("/getTeam")
	public ResponseEntity<?> getTeam()
	{
		String type = Constant.SUBTREE;
		return ResponseEntity.status(HttpStatus.OK).body(ldapService.getSubTree(type));
	}

	@Autowired
	LdapService ldapService;

}
