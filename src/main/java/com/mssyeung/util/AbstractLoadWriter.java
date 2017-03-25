package com.mssyeung.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class AbstractLoadWriter {
	
	public void writandTimeStat(byte[]content,String fileName) {
		final long start = System.nanoTime();
		writeContent(content,fileName);
		final long end = System.nanoTime();

		System.out.println("Time (seconds) taken is " + (end - start) / 1.0e9);
	}
	protected static void writeFile(byte[] fileContent, String fileName) throws FileNotFoundException, IOException {
		try (FileOutputStream fos = new FileOutputStream(new File(fileName),true)) {
			fos.write(fileContent, 0, fileContent.length);;
			fos.flush();
		}
	}
	
	public abstract void writeContent(byte[]content,String fileName);
}
