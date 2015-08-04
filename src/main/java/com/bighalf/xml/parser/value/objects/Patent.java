package com.bighalf.xml.parser.value.objects;

import java.util.LinkedHashSet;
import java.util.List;

public class Patent {
	
	private String applicationNumber;
	private String fileDate;
	private LinkedHashSet<Inventor> inventors;
	private String currentUsClass;
	private List<String> fieldOfSearchs;
	
	public String getApplicationNumber() {
		return applicationNumber;
	}
	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}
	public String getFileDate() {
		return fileDate;
	}
	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}
	public LinkedHashSet<Inventor> getInventors() {
		return inventors;
	}
	public void setInventors(LinkedHashSet<Inventor> inventors) {
		this.inventors = inventors;
	}
	public String getCurrentUsClass() {
		return currentUsClass;
	}
	public void setCurrentUsClass(String currentUsClass) {
		this.currentUsClass = currentUsClass;
	}
	public List<String> getFieldOfSearchs() {
		return fieldOfSearchs;
	}
	public void setFieldOfSearchs(List<String> fieldOfSearchs) {
		this.fieldOfSearchs = fieldOfSearchs;
	}
}
