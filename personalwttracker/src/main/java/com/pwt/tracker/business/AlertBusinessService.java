package com.pwt.tracker.business;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pwt.tracker.dao.AlertDAO;
import com.pwt.tracker.entity.AlertList;
@Service
public class AlertBusinessService {
@Autowired AlertDAO alertDAO;
private static final Logger LOGGER = Logger.getLogger(AlertBusinessService.class.getName());
	
	/**
	 * 1. Retrieve all the alerts
	 * @return
	 */
	public AlertList getAlerts(){
		LOGGER.info("Entered getAlerts method ");
		AlertList alertList=new AlertList();
		alertList.setAlerts(alertDAO.getAlerts());
		return alertList;
	}
	
	/**
	 * 1. Reads data between two timestamps
	 * 2. From and End not inclusive
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	public AlertList readByTimeRange( Long starttime, Long endtime){
		LOGGER.info("Entered readByTimeRange method ");
		AlertList alertList=new AlertList();
		alertList.setAlerts(alertDAO.readByTimeRange(starttime, endtime));
		return alertList; 
	}
}
