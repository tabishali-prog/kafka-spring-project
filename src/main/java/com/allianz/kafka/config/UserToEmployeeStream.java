package com.allianz.kafka.config;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;

import com.allianz.kafka.domainservice.DomainService;
import com.eaton.kafka.confluent.employee.Employee;
import com.eaton.kafka.confluent.employee.SalaryDetails;
import com.eaton.kafka.confluent.user.User;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;

@Configuration
@EnableKafkaStreams
public class UserToEmployeeStream {

	@Value("${spring.kafka.topics.user}")
	private String userTopic;

	@Value("${spring.kafka.topics.employee}")
	private String employeeTopic;

	@Autowired
	DomainService domainService;

	@Bean
	public KStream<String, User> userEmployeeStream(StreamsBuilder builder, SpecificAvroSerde<User> userSerde,
			SpecificAvroSerde<Employee> employeeSerde) {

		KStream<String, User> userStream = builder.stream(userTopic, Consumed.with(Serdes.String(), userSerde));

		KStream<String, Employee> employeeStream = userStream.mapValues(domainService::userToEmployeeTransformation);

		employeeStream.to(employeeTopic, Produced.with(Serdes.String(), employeeSerde));

		return userStream;
	}
}
