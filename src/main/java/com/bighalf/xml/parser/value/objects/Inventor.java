package com.bighalf.xml.parser.value.objects;


public class Inventor {
	private String firstName;
	private String lastName;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public String toString(){
		return this.lastName+" "+this.firstName;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof Patent){
			Inventor inventor = (Inventor)obj;
			if(inventor.getFirstName().equals(this.getFirstName())&&
					inventor.getLastName().equals(this.getLastName())){
				return true;
			}
		}
		return false;
	}
}
