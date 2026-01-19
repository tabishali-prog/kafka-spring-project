package com.allianz.kafka.domainservice;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.allianz.kafka.dto.UserDetails;
import com.eaton.kafka.confluent.employee.Employee;
import com.eaton.kafka.confluent.employee.SalaryDetails;
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
		if (ur == null) {
			return;
		}
		var user = new User();
		user.setUserId(ur.getUserId());
		user.setStatus(Objects.requireNonNullElse(ur.getStatus(), ""));
		user.setDepartment(Objects.requireNonNullElse(ur.getDepartment(), ""));
		var pdReq = ur.getPersonalDetails();
		if (pdReq != null) {
			var pd = new PersonalDetails();
			pd.setFirstName(Objects.requireNonNullElse(pdReq.getFirstName(), ""));
			pd.setLastName(Objects.requireNonNullElse(pdReq.getLastName(), ""));
			pd.setDateOfBirth(Objects.requireNonNullElse(pdReq.getDateOfBirth(), ""));
			user.setPersonalDetails(pd);
		}
		var ctReq = ur.getContact();
		if (ctReq != null) {
			var ct = new Contact();
			ct.setEmail(Objects.requireNonNullElse(ctReq.getEmail(), ""));
			ct.setPhone(Objects.requireNonNullElse(ctReq.getPhone(), ""));
			user.setContact(ct);
		}
		String key = String.valueOf(user.getUserId());
		kafkaTemplate.send(userTopic, key, user);
	}

	@Override
	public Employee userToEmployeeTransformation(User user) {
		String fullName = user.getPersonalDetails() != null
				? user.getPersonalDetails().getFirstName() + " " + user.getPersonalDetails().getLastName()
				: "";
		SalaryDetails salary = SalaryDetails.newBuilder().setBaseSalary(60000).setCurrency("USD").build();
		return Employee.newBuilder().setEmployeeId(String.valueOf(user.getUserId())).setFullName(fullName)
				.setDepartmentName(user.getDepartment())
				.setWorkEmail(user.getContact() != null ? user.getContact().getEmail() : "")
				.setEmploymentStatus(user.getStatus()).setHireDate("2025-01-01").setSalaryDetails(salary).build();
	}

}
