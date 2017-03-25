package com.wellmanage.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class LoadTestWriter {
	
	public static void writeDataWithThread(byte[] fileContent) {
		
		ExecutorService exec = Executors.newFixedThreadPool(4);
		final int partSize = (1024*1024*10);
		int part = fileContent.length / partSize;
		
		final List<Callable<Boolean>> partitions = new ArrayList<Callable<Boolean>>();
		
		for (int i = 0; i < part; i++) {
			final int lower = (i * partSize);
			final int upper = (i == part - 1) ? fileContent.length : lower + partSize;

			System.out.println(lower + " , " + upper + " - " + (upper-lower));
			
			partitions.add(new Callable<Boolean>() {

				@Override
				public Boolean call() throws Exception {
					byte[]content = new byte[(upper-lower)];
					System.arraycopy(fileContent, lower, content, 0, (upper-lower));
					
					String t = new SimpleDateFormat("HH:mm").format(new Date());
					writeFile(content, String.format("data.out.%s.txt",Thread.currentThread().getName()));
					return true;
				}
				
			});
			
			
		}//
		System.out.println("# of part: " + partitions.size());
		try {
			List<Future<Boolean>> results = exec.invokeAll(partitions);
			exec.shutdown();
			
			for (Future<Boolean> w : results) {
				try {
					System.out.println(w.get());
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(part);
	}
	private static void writeFile(byte[] fileContent, String fileName) throws FileNotFoundException, IOException {
		try (FileOutputStream fos = new FileOutputStream(new File(fileName),true)) {
			fos.write(fileContent, 0, fileContent.length);;
			fos.flush();
		}
	}
	
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
	
	private static String DUMMY = "Lorem Ipsum is simply dummy text of the printing and typesetting industry." 
			                    + " Lorem Ipsum is simply dummy text of the printing and typesetting industry." 
			                    + " Lorem Ipsum is simply dummy text of the printing and typesetting industry." 
			                    + " Lorem Ipsum is simply dummy text of the printing and typesetting industry." 
			                    + " Lorem Ipsum is simply dummy text of the printing and typesetting industry." 
			                    + " Lorem Ipsum is simply dummy text of the printing and typesetting industry." 
			                    + " Lorem Ipsum is simply dummy text of the printing and typesetting industry.";

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
