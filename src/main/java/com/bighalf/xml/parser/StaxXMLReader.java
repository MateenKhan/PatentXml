package com.bighalf.xml.parser;
 
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import com.bighalf.xml.parser.value.objects.Patent;
 
public class StaxXMLReader {
 
    public static void main(String[] args) {
        String fileName = "C:\\Users\\kstrata\\Downloads\\ipg150106\\sample.xml";
        List<Patent> empList = parseXML(fileName);
        for(Patent emp : empList){
            System.out.println(emp.toString());
        }
    }
 
    private static List<Patent> parseXML(String fileName) {
        List<Patent> empList = new ArrayList<Patent>();
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        boolean isApplicationNumberRetrived,isDateRetrived,isInventorsRetrived,isClassificationNumberRetrived;
        isApplicationNumberRetrived=isDateRetrived=isInventorsRetrived=isClassificationNumberRetrived=false;
        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(fileName));
            while(xmlEventReader.hasNext()){
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
               if (xmlEvent.isStartElement()){
                   StartElement startElement = xmlEvent.asStartElement();
                   if(startElement.getName().getLocalPart().equals("us-patent-grant")){
                	   System.out.println(xmlEvent.asStartElement());
                   }
                   //set the other varibles from xml elements
                   else if(!isApplicationNumberRetrived&&startElement.getName().getLocalPart().equals("doc-number")){
                       xmlEvent = xmlEventReader.nextEvent();
                       System.out.println(xmlEvent.toString());
                       isApplicationNumberRetrived=true;
                   }else if(!isDateRetrived&&startElement.getName().getLocalPart().equals("date")){
                       xmlEvent = xmlEventReader.nextEvent();
                       System.out.println(xmlEvent.asCharacters().getData());
                       isDateRetrived=true;
                   }else if(!isInventorsRetrived&&startElement.getName().getLocalPart().equals("inventors")){
                       xmlEvent = xmlEventReader.nextEvent();
                       xmlEvent = xmlEventReader.nextEvent();
                       xmlEvent = xmlEventReader.nextEvent();
                       xmlEvent = xmlEventReader.nextEvent();
                       xmlEvent = xmlEventReader.nextEvent();
                       System.out.println("here");
                       System.out.println(xmlEvent.asCharacters().getData());
                       isInventorsRetrived=true;
                   }else if(!isClassificationNumberRetrived&&startElement.getName().getLocalPart().equals("main-classification")){
                       xmlEvent = xmlEventReader.nextEvent();
                       System.out.println(xmlEvent.asCharacters().getData());
                       isClassificationNumberRetrived=true;
                   }
               }
               //if Patent end element is reached, add Patent object to list
               if(xmlEvent.isEndElement()){
                   EndElement endElement = xmlEvent.asEndElement();
                   if(endElement.getName().getLocalPart().equals("us-patent-grant")){
                	   System.out.println(xmlEvent.asEndElement());
                	   break;
                   }
               }
            }
             
        } catch (Exception e) {
            e.printStackTrace();
        }
        return empList;
    }
 
}