package com.pwt.tracker.entity;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Maps to alerts collection in sensorDB in mongoDB
 * Also, I have added JsonIgnore annotation to avoid 
 * id field in my json response
 * @author Shivam
 *
 */
@Entity("alerts")
public class Alert {
	@Id
	@JsonIgnore
	private ObjectId id;
	private String alertMessage;
	private Long timeStamp;
	private String value;
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getAlertMessage() {
		return alertMessage;
	}
	public void setAlertMessage(String alertMessage) {
		this.alertMessage = alertMessage;
	}
	public Long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "Alert [id=" + id + ", alertMessage=" + alertMessage + ", timeStamp=" + timeStamp + ", value=" + value
				+ "]";
	}
	
	
	
}
