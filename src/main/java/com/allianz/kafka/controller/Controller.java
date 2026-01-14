package com.allianz.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.allianz.kafka.domainservice.DomainService;
import com.allianz.kafka.dto.UserDetails;
import com.allianz.kafka.misc.ApplicationError;



@RestController
public class Controller {

	@Autowired
	DomainService domainService;

	@PostMapping("/send-user")
	public ApplicationError sendUser(@RequestBody UserDetails user) {
		ApplicationError error = new ApplicationError();
		try {
			domainService.sendToUserTopic(user);
			error.setErrorCode("200");
			error.setErrorDesc("User sent successfully to Kafka");
		} catch (Exception e) {
			error.setErrorCode("500");
			error.setErrorDesc(e.getMessage());
		}
		return error;
	}
}
