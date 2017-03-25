package com.wellmanage.util;

public class StatLogger {

	private String STAT_FILE = "stat.txt";
	
	public static void write() {
		
	}
	
	//https://www.dotnetperls.com/format-java
	public static void main(String[] args) {
		System.out.println(String.format("%1$15s", "9"));
	}
}
