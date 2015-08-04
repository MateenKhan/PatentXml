package com.bighalf.xml.parser;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.codehaus.jackson.map.ObjectMapper;

import com.bighalf.xml.parser.value.objects.Inventor;
import com.bighalf.xml.parser.value.objects.Patent;

public class StaxExcel {

	public static void main(String[] args) {
		FileOutputStream fout = null;
		try {
			String fileName = "C:\\Users\\kstrata\\Downloads\\ipg150106\\copied.xml";
			List<Patent> patentList = parseXML(fileName);
			createSheetWithListOfPatents(patentList);
			ObjectMapper mapper = new ObjectMapper();
//			fout = new FileOutputStream("C:\\Users\\kstrata\\Desktop\\work\\patent\\ipg150106\\ipg150106_result.json");
			String str = mapper.writeValueAsString(patentList);
			System.out.println(str);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fout != null)
					fout.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// System.out.println(System.currentTimeMillis()-startTime);
	}
	
	private static void createSheetWithListOfPatents(List<Patent> patentList){
		String excelPath="C:\\Users\\kstrata\\Desktop\\work\\patent\\excel\\ipg150106.xls";
		Workbook wb = null;
		FileOutputStream fileOut = null;
		Sheet sheet = null;
		try {
			wb = new HSSFWorkbook();
			fileOut = new FileOutputStream(excelPath);
		    sheet = wb.createSheet("new sheet");
		    Row row = sheet.createRow(0);
	    	row.createCell(0).setCellValue("Application Number");
	    	row.createCell(1).setCellValue("File Date");
	    	row.createCell(2).setCellValue("Inventors");
	    	row.createCell(3).setCellValue("CurrentUs Class");
	    	row.createCell(4).setCellValue("Field Of Searchs");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Iterator<Patent> patentListItr = patentList.iterator();
		while(patentListItr.hasNext()){
			Patent patent = patentListItr.next();
			try {
				Row row = sheet.createRow(sheet.getLastRowNum()+1);
				row.createCell(0).setCellValue(patent.getApplicationNumber());
				row.createCell(1).setCellValue(patent.getFileDate());
				LinkedHashSet<Inventor> inventors = patent.getInventors();
				Iterator<Inventor> itr = inventors.iterator();
				StringBuilder inventorsSb = new StringBuilder();
				while(itr.hasNext()){
					inventorsSb.append(itr.next().toString()+",");
				}
				String inventorsStr = inventorsSb.substring(0,inventorsSb.length()-1);
				row.createCell(2).setCellValue(inventorsStr.toString());
				row.createCell(3).setCellValue(patent.getCurrentUsClass());
				String fieldOfSearches=patent.getFieldOfSearchs().toString();
				fieldOfSearches=fieldOfSearches.substring(1,fieldOfSearches.length()-1);
				row.createCell(4).setCellValue(fieldOfSearches);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			wb.write(fileOut);
			wb.close();
		    fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private static List<Patent> parseXML(String fileName) {
		List<Patent> patentList = new ArrayList<Patent>();
		Patent patent = null;
		LinkedHashSet<Inventor> inventors = null;
		List<String> fieldOfSearchs = null;
		Inventor inventor = null;
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		int classificationCtr = 0;
		boolean isApplicationNumberRetrived, isDateRetrived;
		isApplicationNumberRetrived = isDateRetrived = false;
		try {
			XMLEventReader xmlEventReader = xmlInputFactory
					.createXMLEventReader(new FileInputStream(fileName));
			String applicationNumber, fileDate, lastName, firstName, currentUsClass;
			while (xmlEventReader.hasNext()) {
				XMLEvent xmlEvent = xmlEventReader.nextEvent();
				if (xmlEvent.isStartElement()) {
					StartElement startElement = xmlEvent.asStartElement();
					if (startElement.getName().getLocalPart()
							.equals("us-patent-grant")) {
						patent = new Patent();
						inventors = new LinkedHashSet<Inventor>();
						fieldOfSearchs = new ArrayList<String>();
					}
					if (!isApplicationNumberRetrived
							&& startElement.getName().getLocalPart()
									.equals("doc-number")) {
						xmlEvent = xmlEventReader.nextEvent();
						applicationNumber = xmlEvent.toString();
						applicationNumber = "D"+applicationNumber.substring(2);//updating the string for easy google search
						patent.setApplicationNumber(applicationNumber);
						isApplicationNumberRetrived = true;
					} else if (!isDateRetrived
							&& startElement.getName().getLocalPart()
									.equals("date")) {
						xmlEvent = xmlEventReader.nextEvent();
						fileDate = xmlEvent.asCharacters().getData();
						patent.setFileDate(fileDate);
						isDateRetrived = true;
					} else if (startElement.getName().getLocalPart()
							.equals("inventor")) {
						inventor = new Inventor();
					} else if (startElement.getName().getLocalPart()
							.equals("last-name")) {
						xmlEvent = xmlEventReader.nextEvent();
						lastName = xmlEvent.asCharacters().getData();
						inventor.setLastName(lastName);
						inventors.add(inventor);		
					} else if (startElement.getName().getLocalPart()
							.equals("first-name")) {
						xmlEvent = xmlEventReader.nextEvent();
						firstName = xmlEvent.asCharacters().getData();
						inventor.setFirstName(firstName);
					} else if (startElement.getName().getLocalPart()
							.equals("main-classification")) {
						if (classificationCtr == 1) {
							xmlEvent = xmlEventReader.nextEvent();
							currentUsClass = xmlEvent.asCharacters().getData();
							patent.setCurrentUsClass(currentUsClass);
						} else if (classificationCtr > 1) {
							xmlEvent = xmlEventReader.nextEvent();
							String fieldOfSearch = xmlEvent.asCharacters()
									.getData();
							fieldOfSearchs.add(fieldOfSearch);
						}
						classificationCtr++;
					}
				}
				// if Patent end element is reached, add Patent object to list
				if (xmlEvent.isEndElement()) {
					EndElement endElement = xmlEvent.asEndElement();
					if (endElement.getName().getLocalPart()
							.equals("us-patent-grant")) {
						isApplicationNumberRetrived = isDateRetrived = false;
						classificationCtr = 0;
						patent.setFieldOfSearchs(fieldOfSearchs);
						patent.setInventors(inventors);
						patentList.add(patent);
						// break;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		return patentList;
	}
}