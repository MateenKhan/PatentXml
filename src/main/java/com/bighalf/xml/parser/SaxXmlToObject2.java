package com.bighalf.xml.parser;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * this class is used to read the bigger xml(patent) and write data into smaller
 * xml files
 * 
 * @author Mateen
 *
 */
public class SaxXmlToObject2 {

	public static void main(String argv[]) {

		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			DefaultHandler handler = new DefaultHandler() {
				boolean bstaff = false;
				
				public void startElement(
						String uri, String localName,
						String qName, Attributes attributes
						)
						throws SAXException {
					System.out.println("Start Element :" + qName);
					if (qName.equals("staff")) {
						bstaff = true;
					}
				}

				public void endElement(
						String uri, String localName,
						String qName
						) throws SAXException {
					System.out.println("End Element :" + qName);
				}

				public void characters(char ch[], int start, int length)
						throws SAXException {
					if (bstaff) {
						System.out.println("staff : "+ new String(ch, start, length));
						bstaff = false;
					}
				}
			};
			saxParser.parse("D:\\file.xml", handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
