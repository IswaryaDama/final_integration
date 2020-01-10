package com.stackroute.keepnote.controller;

import java.util.Map;

import com.stackroute.keepnote.model.User;

@FunctionalInterface
public interface SecurityTokenGenerator {

	Map<String,String> generateToken(User user);
	
}
