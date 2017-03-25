package com.mssyeung.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Launcher {

	private void readFile() {
		FileReader fr= null;
		BufferedReader br = null;
		try {
			File f = new File("input/inputfile.1.txt");
			fr = new FileReader(f);
			br = new BufferedReader(fr);
			
			char[] data = new char[11821288];
			fr.read(data);
			
			System.out.println(Arrays.toString(data));
			
			
			
			fr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally { 
			if (fr !=null)
				try {fr.close();
				} catch (IOException e) {/*ignore*/}
		}
		

	}
	
	
	private void readFileAsStream() {
		File f = new File("input/inputfile.1.txt");
		try {
			int byteToRead=5;
			FileInputStream fis = new FileInputStream(f);
			for (int i = fis.read(); i != -1; i = fis.read()) {
				System.out.write(i);
				System.out.flush();
				if (--byteToRead < 1) {
					break;
				}
			}
			fis.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void readWriteFileInThread(String fileName) {
		byte[] fileContent = null;
		try {
			fileContent = LoadTestReader.readFile(fileName);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("file size: " + fileContent.length);
		
		try {
			new MultiThreadLoadWriter().writandTimeStat(fileContent, fileName);
			System.out.println("write finished.");
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	private void readWriteFileInWhole(String fileName) {
		byte[] fileContent = null;
		try {
			fileContent = LoadTestReader.readFile(fileName);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("file size: " + fileContent.length);
		
		try {
			LoadTestWriter.writeDataInWhole(fileContent);
			System.out.println("write finished.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	    new Thread(new Runnable() {
	        public void run() {
	          while(true) {
	        	  try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	          }
	        }
	      }).start();
	}
	private void createFile(String fileName) {
		LoadTestWriter.createFile(fileName, 1024*1024*70);
	}

	public static void main(String[] args) {
		
		System.out.println(System.getProperty("user.dir"));
		Launcher la =new Launcher();
		
		
		String fileName = "input/inputfile.1.txt";
		String outFileName = "outputfile.txt";
		if (args.length > 0) {
			if ("-w".equalsIgnoreCase(args[0]) ) {
//				la.createFile(fileName);
				
				la.readWriteFileInThread(fileName);
			} else {
				System.out.println("unknown option.");
			}
		}
		
//		fileName = "/Users/ssyeung/Downloads/Movies/25th.Hour.2002.720p.BluRay.999MB.ShAaNiG.com.mkv";
	}

}
