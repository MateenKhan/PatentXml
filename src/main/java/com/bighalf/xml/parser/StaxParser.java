package com.bighalf.xml.parser;

import java.io.FileReader;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class StaxParser {
	public static void main(String[] args) throws Exception{
		XMLInputFactory factory = XMLInputFactory.newInstance();

		XMLEventReader eventReader =
		    factory.createXMLEventReader(
		        new FileReader("C:\\Users\\kstrata\\Downloads\\ipg150106\\sample.xml"));
		while(eventReader.hasNext()){
			XMLEvent event = eventReader.nextEvent();

		    if(event.getEventType() == XMLStreamConstants.START_ELEMENT){
		        StartElement startElement = event.asStartElement();
		        if(startElement.getName().getLocalPart().equals("us-patent-grant")){
	        		System.out.println(event.asStartElement().toString());
		        }else if(startElement.getName().getLocalPart().equals("doc-number")){
		        	event = eventReader.nextEvent();
		        	if(startElement.isStartElement())
		        		System.out.println(event.asCharacters().getData());
		        }
		    }
		}
	}
}
