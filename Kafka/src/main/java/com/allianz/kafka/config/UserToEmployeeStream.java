package com.allianz.kafka.config;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;

import com.eaton.kafka.confluent.employee.Employee;
import com.eaton.kafka.confluent.employee.SalaryDetails;
import com.eaton.kafka.confluent.user.User;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;

@Configuration
@EnableKafkaStreams
public class UserToEmployeeStream {

	@Bean
	public KStream<String, User> userEmployeeStream(StreamsBuilder builder,  SpecificAvroSerde<User> userSerde,
			  SpecificAvroSerde<Employee> employeeSerde) {

		KStream<String, User> userStream = builder.stream("user", Consumed.with(Serdes.String(), userSerde));

		KStream<String, Employee> employeeStream = userStream.mapValues(this::mapUserToEmployee);

		employeeStream.to("employee", Produced.with(Serdes.String(), employeeSerde));

		return userStream;
	}

	private Employee mapUserToEmployee(User user) {

		String fullName = user.getPersonalDetails().getFirstName() + " " + user.getPersonalDetails().getLastName();

		SalaryDetails salary = SalaryDetails.newBuilder().setBaseSalary(60000).setCurrency("USD").build();

		return Employee.newBuilder().setEmployeeId(String.valueOf(user.getUserId())).setFullName(fullName)
				.setDepartmentName(user.getDepartment()).setWorkEmail(user.getContact().getEmail())
				.setEmploymentStatus(user.getStatus()).setHireDate("2025-01-01").setSalaryDetails(salary).build();
	}
}
