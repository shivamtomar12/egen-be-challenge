package com.pwt.tracker.dao;

import java.util.List;

import org.bson.types.ObjectId;

import com.pwt.tracker.entity.Metric;



public interface MetricsDAO {

	public ObjectId createMetric(Metric metric);
	
	public List<Metric> getMetrics();
	
	public List<Metric> readByTimeRange(Long starttime,Long endtime);
	
	public Metric getBaseWeight();
}
