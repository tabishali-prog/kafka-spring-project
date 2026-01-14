package com.allianz.kafka.domainservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.allianz.kafka.dto.UserDetails;
import com.eaton.kafka.confluent.user.Contact;
import com.eaton.kafka.confluent.user.PersonalDetails;
import com.eaton.kafka.confluent.user.User;

@Service
public class DomainServiceImpl implements DomainService {

	@Value("${spring.kafka.topics.user}")
    private String userTopic;
	
	private final KafkaTemplate<String, User> kafkaTemplate;

	public DomainServiceImpl(KafkaTemplate<String, User> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@Override
	public void sendToUserTopic(UserDetails ur) {
		User user = new User();
		user.setUserId(ur.getUserId());
		user.setStatus(ur.getStatus());
		user.setDepartment(ur.getDepartment());
		PersonalDetails pd = new PersonalDetails();
		pd.setFirstName(ur.getPersonalDetails().getFirstName());
		pd.setLastName(ur.getPersonalDetails().getLastName());
		pd.setDateOfBirth(ur.getPersonalDetails().getDateOfBirth());
		user.setPersonalDetails(pd);
		Contact ct = new Contact();
		ct.setEmail(ur.getContact().getEmail());
		ct.setPhone(ur.getContact().getPhone());
		user.setContact(ct);
		kafkaTemplate.send(userTopic, user);
	}

}
