package com.wellmanage.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class LoadTestWriter {
	
	public static void writeDataInStreaming(byte[] fileContent) {
		
	}
	public static void writeDataWithSize(byte[] fileContent, int size) {
		
	}
	public static void writeDataInWhole(byte[] fileContent) throws IOException {
		File outFile = new File("data.out.txt");
		FileOutputStream fos = new FileOutputStream(outFile);
		fos.write(fileContent, 0, fileContent.length);;
		fos.flush();
		fos.close();
		
	}
	
	private static String DUMMY = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.";

	public static void createFile(String fileName, int size) {
		File file = new File(fileName);
		try (BufferedWriter writer = Files.newBufferedWriter(file.toPath())) {
			while (file.length() < size) {
				writer.write(DUMMY);
				writer.flush();
			}
			System.out.println(file.length() +  " kb of data are written to the file.!");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
