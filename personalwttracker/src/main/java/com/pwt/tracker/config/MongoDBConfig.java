package com.pwt.tracker.config;

import java.net.UnknownHostException;
import java.util.logging.Logger;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;


/**
 * @author Shivam
 * This is a configuration file which initializes the datastore 
 * and 
 * provides a handle to perform operations on Mongo Database
 */
public class MongoDBConfig {
	public static final String DATABASEHOST="127.0.0.1";
	public static final int DATABASEPORT=27017;
	public static final String DATABASENAME="sensorDB";
	private static final MongoDBConfig instance=new MongoDBConfig();
	private final Datastore datastore;

	private MongoDBConfig(){
		MongoClientOptions mongoClientOptions=MongoClientOptions.builder().socketTimeout(60000) // Wait 1m for a query to finish, https://jira.mongodb.org/browse/JAVA-1076
				.connectTimeout(15000) // Try the initial connection for 15s, http://blog.mongolab.com/2013/10/do-you-want-a-timeout/
				.maxConnectionIdleTime(600000).readPreference(ReadPreference.primaryPreferred()).build();
		MongoClient mongoClient=null;

		try {
			//Creates a single connection to the database
			mongoClient=new MongoClient(new ServerAddress(DATABASEHOST,DATABASEPORT),mongoClientOptions);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		//creates datastore with the provided database name
		datastore = new Morphia().createDatastore(mongoClient, DATABASENAME);
		
		datastore.ensureIndexes(); // Ensures that indexs are found during mapping of the class
		datastore.ensureCaps(); //creates all collections for @Entity
		

	}


	/**
	 * @return an instance of this class
	 */
	public static MongoDBConfig getInstance(){
		return instance;
	}
	/**
	 * @return a single connection object to the database
	 */
	public Datastore getDatastore(){
		return datastore;
	}

}
