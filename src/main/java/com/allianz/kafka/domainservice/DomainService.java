package com.allianz.kafka.domainservice;

import com.allianz.kafka.dto.UserDetails;
import com.eaton.kafka.confluent.employee.Employee;
import com.eaton.kafka.confluent.user.User;

public interface DomainService {

	void sendToUserTopic(UserDetails user);
	Employee userToEmployeeTransformation(User user);
}
