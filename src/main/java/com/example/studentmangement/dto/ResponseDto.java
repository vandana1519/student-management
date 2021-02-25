package com.example.studentmangement.dto;

import java.util.ArrayList;
import java.util.List;


public class ResponseDto {

	private List<Message> messages = null;
	private Error error = null;
	private String errStatus = "";
	
	public ResponseDto() {
		this.messages = new ArrayList<>();
	}
	
	public List<Message> getMessages() {
		return this.messages;
	}
	
	public void addMessage(Message message) {
		this.messages.add(message);
	}
	
	public void setErrStatus(String status) {
		this.errStatus = status;
	}
	
	public String getErrStatus() {
		return this.errStatus;
	}
	
	public void setError(Error error) {
		this.error = error;
	}
	
	public Error getError() {
		return this.error;
	}
	
}
