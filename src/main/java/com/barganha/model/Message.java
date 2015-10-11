package com.barganha.model;

public class Message {

	private MessageType type;
	
	private String title;
	
	private String message;

	public Message(MessageType type, String title, String message) {
		this.type = type;
		this.title = title;
		this.message = message;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
