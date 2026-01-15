package com.allianz.kafka.misc;

public class Utility {

	public static boolean isNullOrBlank(String parameter) {
		if (parameter == null || parameter.isBlank()) {
			return true;
		}
		return false;
	}

	public static boolean isObjectNull(Object T) {
		if (T == null) {
			return true;
		} else {
			return false;
		}
	}

}
