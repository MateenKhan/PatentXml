package com.bighalf.xml.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BreakXml {
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		BufferedReader bufferedReader = null;
		try {
			String path = "C:\\Users\\kstrata\\Downloads\\ipg150106\\ipg150106.xml";
			File file = new File(path);
			if(file.exists()){
				StringBuilder output = new StringBuilder();
				bufferedReader = new BufferedReader(new FileReader(file));
				while(bufferedReader.ready()){
					output.append(bufferedReader.readLine());
				}
//				System.out.println(output);
				String temp = output.toString();
				String arr[]=temp.split("\\<\\?xml version=\"1.0\" encoding=\"UTF-8\"\\?\\>");
				String destinationPath="";String data ="";
				File f=null;BufferedWriter bufferedWriter =null;
				for(int i=0;i<arr.length;i++){
					destinationPath="C:\\Users\\kstrata\\Desktop\\work\\patent\\splitted_files\\file_"+i+".xml";
					f=new File(destinationPath);
					if(f.exists()){
						f.delete();
						f.createNewFile();
					}
					data=arr[i];
					data=data.replace("<!DOCTYPE us-patent-grant SYSTEM \"us-patent-grant-v45-2014-04-03.dtd\" [ ]>", "");
					data=data.replace("dtd-version=\"v4.5 2014-04-03\"", "");
					bufferedWriter = new BufferedWriter(new FileWriter(f));
					bufferedWriter.write(data);
					bufferedWriter.flush();
//					System.out.println("data:"+data);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("\n\n total time:"+(System.currentTimeMillis()-startTime)+" milli seconds");
		if(null!=bufferedReader){
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
