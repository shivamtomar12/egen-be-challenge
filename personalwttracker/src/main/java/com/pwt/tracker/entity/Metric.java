package com.pwt.tracker.entity;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * Maps to metrics collection in sensorDB in mongoDB
 * Also, I have added JsonIgnore annotation to avoid 
 * id field in my json response
 * @author Shivam
 *
 */
@Entity("metrics")
public class Metric {
@Id
@JsonIgnore
private ObjectId id;
private Long timeStamp;
private String value;

public Long getTimestamp() {
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
public ObjectId getId() {
	return id;
}
public void setId(ObjectId id) {
	this.id = id;
}
@Override
public String toString() {
	return "Metric [id=" + id + ", timeStamp=" + timeStamp + ", value=" + value + "]";
}


}
