package com.pwt.tracker.business;

import java.util.logging.Logger;

import org.bson.types.ObjectId;
import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pwt.tracker.dao.MetricsDAO;
import com.pwt.tracker.entity.Metric;
import com.pwt.tracker.entity.MetricList;
import com.pwt.tracker.rules.OverWeightRule;
import com.pwt.tracker.rules.UnderWeightRule;




/**
 * @author Shivam
 * This class has the business logic and connects to DAO classes
 *
 */
@Component
public class MetricBusinessService {
	
	@Autowired MetricsDAO metricDAO;
	
	private static final Logger LOGGER = Logger.getLogger(MetricBusinessService.class.getName());
	
	/**
	 * 1.gets all of the metrics from db
	 * @return
	 */
	public MetricList getMetrics(){
		LOGGER.info("Entered getMetrics method");
		MetricList metricList=new MetricList();
		metricList.setMetrics(metricDAO.getMetrics());
		return metricList;
	}
	/**
	 * 1. Returns an instance of RuleEngine
	 * @return
	 */
	private  RulesEngine getRuleEngine(){
		return RulesEngineBuilder.aNewRulesEngine().build();
	}
	/**
	 * 1. Fetches the base weight from db
	 * 2. Apply and execute rules
	 * 3. Persist the new metric into the db
	 * @param metric
	 * @return
	 */
	public ObjectId createMetric(Metric metric){
		//Fetch base weight object
		LOGGER.info("Entered createMetric method");
		Metric basemetric=metricDAO.getBaseWeight();
		LOGGER.info("Base metric object:"+basemetric);
		
		//Apply and execute rules
		if(basemetric!=null){
		Integer baseWeight=Integer.parseInt(basemetric.getValue());
		RulesEngine ruleEngine=getRuleEngine();
		ruleEngine.registerRule(new UnderWeightRule(metric,baseWeight));
		ruleEngine.registerRule(new OverWeightRule(metric,baseWeight));
		ruleEngine.fireRules();
		}
		// Persist into db
		ObjectId id=metricDAO.createMetric(metric);
		
		return id;
	}
	
	/**
	 * 1. Reads data between two timestamps
	 * 2. From and End not inclusive
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	public MetricList readByTimeRange( Long starttime, Long endtime){
		LOGGER.info("Entered readByTimeRange method");
		MetricList metricList=new MetricList();
		metricList.setMetrics(metricDAO.readByTimeRange(starttime, endtime));
		return metricList; 
	}
}
