package com.allianz.kafka.domainservice;

import com.allianz.kafka.dto.UserDetails;

public interface DomainService {

	void sendToUserTopic(UserDetails user);

}
