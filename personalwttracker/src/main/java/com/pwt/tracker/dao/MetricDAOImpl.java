package com.pwt.tracker.dao;

import java.util.List;
import java.util.logging.Logger;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Component;

import com.pwt.tracker.config.MongoDBConfig;
import com.pwt.tracker.entity.Metric;
@Component
public class MetricDAOImpl implements MetricsDAO{
	private final Datastore datastore;
	private static final Logger LOGGER = Logger.getLogger(MetricDAOImpl.class.getName());
	public MetricDAOImpl() {
		datastore = MongoDBConfig.getInstance().getDatastore();
	}

	@Override
	public ObjectId createMetric(Metric metric) {
		LOGGER.info("Entered create method");
		datastore.save(metric);
		return metric.getId();
	}

	@Override
	public List<Metric> getMetrics() {
		LOGGER.info("Entered getMetrics method");
		return datastore.find(Metric.class).asList();
	}

	@Override
	public List<Metric> readByTimeRange(Long starttime, Long endtime) {
		LOGGER.info("Entered readByTimeRange method");
		Query<Metric> query=datastore.createQuery(Metric.class).field("timeStamp").greaterThan(starttime).field("timeStamp").lessThan(endtime);
		return query.asList();
	}
	
	/* Returns the base metric object having base weight 
	 * to compare with the new weight
	 * @see com.pwt.tracker.dao.MetricsDAO#getBaseWeight()
	 */
	@Override
	public Metric getBaseWeight(){
		LOGGER.info("Entered getBaseWeight method");
		List<Metric> list = datastore.find(Metric.class).order("timeStamp").asList();
		if(list.size()==0){
			return null;
		}else{
			return list.get(0);
		}
	}

}
