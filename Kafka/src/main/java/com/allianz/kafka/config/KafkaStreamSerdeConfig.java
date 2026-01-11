package com.allianz.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.eaton.kafka.confluent.employee.Employee;
import com.eaton.kafka.confluent.user.User;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;

@Configuration
public class KafkaStreamSerdeConfig {

	@Bean
	public SpecificAvroSerde<com.eaton.kafka.confluent.user.User> userAvroSerde(@Value("${spring.kafka.properties.schema.registry.url}") String srUrl,
			@Value("${spring.kafka.properties.basic.auth.user.info}") String srAuth) {

		SpecificAvroSerde<User> serde = new SpecificAvroSerde<>();

		Map<String, Object> props = new HashMap<>();
		props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, srUrl);
		props.put(AbstractKafkaSchemaSerDeConfig.BASIC_AUTH_CREDENTIALS_SOURCE, "USER_INFO");
		props.put(AbstractKafkaSchemaSerDeConfig.USER_INFO_CONFIG, srAuth);

		serde.configure(props, false);
		return serde;
	}
	@Bean
	public SpecificAvroSerde<Employee> employeeAvroSerde(
			@Value("${spring.kafka.properties.schema.registry.url}") String srUrl,
			@Value("${spring.kafka.properties.basic.auth.user.info}") String srAuth) {

		SpecificAvroSerde<Employee> serde = new SpecificAvroSerde<>();

		Map<String, Object> props = new HashMap<>();
		props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, srUrl);
		props.put(AbstractKafkaSchemaSerDeConfig.BASIC_AUTH_CREDENTIALS_SOURCE, "USER_INFO");
		props.put(AbstractKafkaSchemaSerDeConfig.USER_INFO_CONFIG, srAuth);

		serde.configure(props, false);
		return serde;
	}
}
