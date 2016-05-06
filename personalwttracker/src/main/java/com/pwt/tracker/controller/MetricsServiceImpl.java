package com.pwt.tracker.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pwt.tracker.business.MetricBusinessService;
import com.pwt.tracker.entity.Metric;
import com.pwt.tracker.entity.MetricList;

@RestController
@RequestMapping("/metricsservice")
public class MetricsServiceImpl {
	
	private static final Logger LOGGER = Logger.getLogger(MetricsServiceImpl.class.getName());
	public static final String NOCONTENTFOUND="No Content Found";
	
	@Autowired MetricBusinessService metricBS;
	
	/**
	 * 1. Read all metrics data from business service
	 * 2. if no content fount return httpstatus code 204
	 * 3. if content found then return 200
	 * 4. In case of any exception return 404
	 * @return
	 */
	@RequestMapping(value="/read",method=RequestMethod.GET)
	public ResponseEntity<?> read(){
		LOGGER.info("Entered read method");
		MetricList metricList=new MetricList();
		try{
			metricList=metricBS.getMetrics();
		}catch(Exception e){
			LOGGER.info("Exception occured:"+e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if(metricList.getMetrics().size()==0) return new ResponseEntity<String>(NOCONTENTFOUND,HttpStatus.NO_CONTENT);
		return new ResponseEntity<MetricList>(metricList,HttpStatus.OK);
	}

	/**
	 * 1. Create Metric in the database
	 * 2. Trigger underweight and overweight rules from business service
	 * 3. if created successfully  then return 201
	 * 4. In case of any exception return 404
	 * @return
	 */
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody Metric metric){
		LOGGER.info("Entered create method with metric object parameter:"+metric);
		try{
			metricBS.createMetric(metric);
		}catch(Exception e){
			LOGGER.info("Exception occured:"+e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("Successfully Created!",HttpStatus.CREATED);
	}
	
	/**
	 * 1. Read metrics data within time range from business service
	 * 2. if no content fount return httpstatus code 204
	 * 3. if content found then return 200
	 * 4. In case of any exception return 404
	 * @return
	 */
	@RequestMapping(value="/readByTimeRange/{starttime}/{endtime}",method=RequestMethod.GET)
	public ResponseEntity<?> readByTimeRange(@PathVariable Long starttime,@PathVariable Long endtime){
		LOGGER.info("Entered readByTimeRange method {starttime} and {endtime}: "+starttime+" "+endtime);
		MetricList metricList=new MetricList();
		try{
			metricList=metricBS.readByTimeRange(starttime, endtime);
		}catch(Exception e){
			LOGGER.info("Exception occured:"+e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if(metricList.getMetrics().size()==0) return new ResponseEntity<String>(NOCONTENTFOUND,HttpStatus.NO_CONTENT);
		return new ResponseEntity<MetricList>(metricList,HttpStatus.OK); 
	}


}
