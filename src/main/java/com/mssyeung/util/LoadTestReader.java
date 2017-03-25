package com.mssyeung.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class LoadTestReader {
	
	
	public static byte[]readFile(String fileName) throws IOException {
		
		File f = new File(fileName);
		if (!f.exists()) {
			throw new FileNotFoundException(fileName);
		}
		
		int fileSize = (int)f.length();
		
		if (fileSize< 0 ) {
			throw new RuntimeException("File is not supported (max:2GB)");
		}
		
		byte[] data = new byte[fileSize]; //read the entire file. up to 2GB
		FileInputStream fis = new FileInputStream(f);
		fis.read(data);
		fis.close();
		
		return data;
	}
}
