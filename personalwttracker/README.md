PersonalWeightTracker readme file:




1. 	Command to execute Emulator 
	java -jar -Dbase.value=160 -Dapi.url=http://localhost:8080/api/metricsservice/create sensor-emulator-0.0.1-SNAPSHOT.jar


Metrics----------------------------------------------------------------------------------------------------------------------------------------------

1. read url  http://localhost:8080/api/metricsservice/read

2. create url http://localhost:8080/api/metricsservice/create

Request body:
{
"value": "155",
"timeStamp": 1458062848739
}

3. readByTimeRange url  http://localhost:8080/api/metricsservice/readByTimeRange/{starttime}/{endtime}
http://localhost:8080/api/metricsservice/readByTimeRange/1462408951965/1462409400486
 

------------------------------------------------------------------------------------------------------------------------------------------------------

Alerts------------------------------------------------------------------------------------------------------------------------------------------------

1. read url  http://localhost:8080/api/alertsservice/read


2. readByTimeRange url  http://localhost:8080/api/alertsservice/readByTimeRange/{starttime}/{endtime}
 http://localhost:8080/api/alertsservice/readByTimeRange/1462408951965/1462409400486


-------------------------------------------------------------------------------------------------------------------------------------------------------
 
 
 
 
(Shivam Singh Tomar)
