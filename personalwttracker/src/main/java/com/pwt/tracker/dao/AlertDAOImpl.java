package com.pwt.tracker.dao;

import java.util.List;
import java.util.logging.Logger;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Component;

import com.pwt.tracker.config.MongoDBConfig;
import com.pwt.tracker.entity.Alert;
@Component
public class AlertDAOImpl implements AlertDAO{

	private final Datastore datastore;
	private static final Logger LOGGER = Logger.getLogger(AlertDAOImpl.class.getName());

	public AlertDAOImpl() {
		datastore = MongoDBConfig.getInstance().getDatastore();
	}
	@Override
	public ObjectId createAlert(Alert alert) {
		LOGGER.info("Entered createAlert method");
		datastore.save(alert);
		return alert.getId();
	}
	@Override
	public List<Alert> getAlerts(){
		LOGGER.info("Entered getAlerts method");
		return datastore.find(Alert.class).asList();
	}
	@Override
	public List<Alert> readByTimeRange(Long starttime,Long endtime){
		LOGGER.info("Entered readByTimeRange method");
		Query<Alert> query=datastore.createQuery(Alert.class).field("timeStamp").greaterThan(starttime).field("timeStamp").lessThan(endtime);
		return query.asList();
	}

}
