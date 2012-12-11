package com.example.itsfire;


public class note 
{
	private int id;
	private String subject;
	private String keywords;
	private String date;
	private boolean read;
 
	public note(){}

	@Override
	public String toString() {
		return "note [id=" + id + ", subject=" + subject + ", keywords="
				+ keywords + ", date=" + date + ", read=" + read + "]";
	}

	public note(int id, String subject, String keywords, String date,boolean read)
	{
		this.id = id;
		this.subject = subject;
		this.keywords = keywords;
		this.date = date;
		this.read = read;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}
 

	

}
