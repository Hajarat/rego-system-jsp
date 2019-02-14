package com.example.common;

import java.util.*;

public class Language {
	public static ResourceBundle bundle;
	public static void setBundle(String language, String location) {
		Locale l = new Locale(language, location);
		bundle = ResourceBundle.getBundle("bundle", l);
	}
	public Language() {
		//Default
		String language = "en";
		String location = "US";
		Locale l = new Locale(language, location);
		bundle = ResourceBundle.getBundle("bundle", l);
	}
}
