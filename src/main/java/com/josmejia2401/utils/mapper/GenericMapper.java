package com.josmejia2401.utils.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class GenericMapper {
	
	public static <X> X objectMapper(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		TypeReference<X> ref = new TypeReference<X>(){};
		return (X) mapper.convertValue(object, ref);
	}
}
