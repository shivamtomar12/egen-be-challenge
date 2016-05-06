package com.pwt.tracker.dao;

import java.util.List;

import org.bson.types.ObjectId;

import com.pwt.tracker.entity.Alert;

public interface AlertDAO {
public ObjectId createAlert(Alert alert);
public List<Alert> getAlerts();
public List<Alert> readByTimeRange(Long starttime,Long endtime);
	

}
