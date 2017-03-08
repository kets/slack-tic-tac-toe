package com.slack.tictactoe.models;

import java.util.ArrayList;
import java.util.List;

public abstract class SlackResponse {	
	private String text;
	private List<String> attachments;
	
	public abstract String getResponse_type();
	public SlackResponse(String text) {
		this.text = text;
	}
	
	public SlackResponse(String text, List<String> attachments) {
		this.text =  text;
		this.attachments = attachments;
		
	}
	public String getText() {
		return text;
	}
	
	public List<String> getAttachments() {
		return this.attachments;
	}
	
	public void setAttachment(String text) {
		if (this.attachments == null) {
			this.attachments = new ArrayList<String>();
		}
		
		this.attachments.add(text);
	}
}
