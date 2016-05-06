package com.pwt.tracker.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pwt.tracker.business.AlertBusinessService;
import com.pwt.tracker.entity.AlertList;

@RestController
@RequestMapping("/alertsservice")
public class AlertServiceImpl {
	public static final String NOCONTENTFOUND="No Content Found";

	@Autowired AlertBusinessService alertBS;
	private static final Logger LOGGER = Logger.getLogger(AlertServiceImpl.class.getName());
	
	/**
	 * 1. Read all alerts data from business service
	 * 2. if no content fount return httpstatus code 204
	 * 3. if content found then return 200
	 * 4. In case of any exception return 404
	 * @return
	 */
	@RequestMapping(value="/read",method=RequestMethod.GET)
	public ResponseEntity<?> read(){
		LOGGER.info("Entered read method");
		AlertList alertList=new AlertList();
		try{
			alertList=alertBS.getAlerts();
		}catch(Exception e){
			LOGGER.info("Exception Occured:"+e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if(alertList.getAlerts().size()==0) return new ResponseEntity<String>(NOCONTENTFOUND,HttpStatus.NO_CONTENT);
		return new ResponseEntity<AlertList>(alertList,HttpStatus.OK);
	}
	
	/**
	 * 1. Read alerts data within time range from business service
	 * 2. if no content fount return httpstatus code 204
	 * 3. if content found then return 200
	 * 4. In case of any exception return 404
	 * @return
	 */
	@RequestMapping(value="/readByTimeRange/{starttime}/{endtime}",method=RequestMethod.GET)
	public ResponseEntity<?> readByTimeRange(@PathVariable Long starttime,@PathVariable Long endtime) {
		LOGGER.info("Entered readByTimeRange method {starttime} and {endtime}: "+starttime+" "+endtime);
		AlertList alertList=new AlertList();
		try{
		alertList=alertBS.readByTimeRange(starttime, endtime);
		}catch(Exception e){
			LOGGER.info("Exception Occured:"+e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if(alertList.getAlerts().size()==0) return new ResponseEntity<String>(NOCONTENTFOUND,HttpStatus.NO_CONTENT);
		return new ResponseEntity<AlertList>(alertList,HttpStatus.OK); 
	}
	
	
}
