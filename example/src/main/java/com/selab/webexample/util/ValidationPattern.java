package com.selab.webexample.util;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class ValidationPattern {

	public static final Pattern NON_WORLD_CHARACTER;
	
	public static final Pattern NON_WORLD_CHARACTER_AND_KOREAN;

	static {
		NON_WORLD_CHARACTER = Pattern.compile("^\\w*$");
		NON_WORLD_CHARACTER_AND_KOREAN = Pattern.compile("^[a-zA-Z_0-9ㄱ-ㅎ가-힣]*$");
	}
	
}
