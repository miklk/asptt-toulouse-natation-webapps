package com.asptttoulousenatation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternRecherche {

	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("(.*)(\".*\")(.*)");
		String test = "xxxx \"yyyy\" zzzz";
		Matcher matcher = pattern.matcher(test);
		for(int i = 0; i < matcher.groupCount(); i++) {
			System.out.println(matcher.group(i));
		}

	}

}
