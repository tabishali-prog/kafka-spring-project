package com.allianz.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.allianz.kafka.domainservice.DomainService;
import com.allianz.kafka.dto.UserDetails;
import com.allianz.kafka.misc.ApplicationError;
import com.allianz.kafka.misc.Constant;



@RestController
public class Controller {

	@Autowired
	DomainService domainService;

	@PostMapping("/send-user")
	public ApplicationError sendUser(@RequestBody UserDetails user) {
		ApplicationError error = new ApplicationError();
		try {
			domainService.sendToUserTopic(user);
			error.setErrorCode(Constant.SUCCESS_CODE);
			error.setErrorDesc(Constant.SUCCESS_MSG);
		} catch (Exception e) {
			error.setErrorCode(Constant.FAILURE_CODE);
			error.setErrorDesc(e.getMessage());
		}
		return error;
	}
}
