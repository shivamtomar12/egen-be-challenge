package com.pwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.pwt.tracker.business.AlertBusinessService;
import com.pwt.tracker.business.MetricBusinessService;
import com.pwt.tracker.entity.Alert;
import com.pwt.tracker.entity.Metric;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PersonalWtTrackerApplication.class)
@WebAppConfiguration
public class PersonalWtTrackerApplicationTests {


	//private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType());
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

	private MockMvc mockMvc;

	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	private MetricBusinessService metricBusinessService;

	@Autowired
	private AlertBusinessService alertBusinessService;


	@Autowired
	private WebApplicationContext webApplicationContext;

	
	
	private static final Long underWeightAlertTS=1462409400488L;
	private static final Long overWeightAlertTS=1462409400487L;
	
	
	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
				hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

		Assert.assertNotNull("the JSON message converter must not be null",
				this.mappingJackson2HttpMessageConverter);
	}
	@Before
	public void setUp(){
		this.mockMvc =webAppContextSetup(webApplicationContext).build();

	}
	

	@Test
	public void contextLoads() {
		
		Metric metric1=new Metric();
		metric1.setValue("150");
		metric1.setTimeStamp(1462409400486L);

		Metric metric2=new Metric();
		metric2.setValue("177");
		metric2.setTimeStamp(1462409400487L);

		Metric metric3=new Metric();
		metric3.setValue("-90");
		metric3.setTimeStamp(1462409400488L);

		metricBusinessService.createMetric(metric1);
		metricBusinessService.createMetric(metric2);
		metricBusinessService.createMetric(metric3);


	}
	

	@Test
	public void readAllMetric() throws Exception {
		mockMvc.perform(get("/metricsservice/read")).andExpect(status().isOk());
	}
	
	@Test
	public void noMetricDataFoundInReadByTimeRange() throws Exception {
		mockMvc.perform(get("/metricsservice/readByTimeRange/1462409400487/1462409400488")).andExpect(status().isNoContent());

	}
	
	@Test
	public void noAlertDataFoundInReadByTimeRange() throws Exception {
		mockMvc.perform(get("/alertsservice/readByTimeRange/1462409400487/1462409400488")).andExpect(status().isNoContent());

	}
	
	
	
	@Test
	public void testForJustOneData() throws Exception {
		mockMvc.perform(get("/metricsservice/readByTimeRange/1462409400486/1462409400488"))
		.andExpect(status().isOk());

	}
	
	

	@Test
	public void createMetric() throws Exception {
		Metric metric=new Metric();
		metric.setTimeStamp(1462409400470L);
		metric.setValue("30000");
		String metricJson = json(metric);
		System.out.println("metricJson"+metricJson);
		this.mockMvc.perform(post("/metricsservice/create")
				.contentType(contentType)
				.content(metricJson))
		.andExpect(status().isCreated());
	}
	
	@Test
	public void readIfUnderWeightAlertsCreated(){
		for(Alert alert:alertBusinessService.getAlerts().getAlerts()){
			if(alert.getTimeStamp().equals(underWeightAlertTS)){
				assert(true);
				return;
			}
		}
		assert(false);
	}
	
	@Test
	public void readIfOverWeightAlertsCreated(){
		for(Alert alert:alertBusinessService.getAlerts().getAlerts()){
			if(alert.getTimeStamp().equals(overWeightAlertTS)){
				assert(true);
				return;
			}
		}
		assert(false);
	}
	
	 private String json(Object o) throws HttpMessageNotWritableException, IOException  {
	        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
	        this.mappingJackson2HttpMessageConverter.write(
	                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
	        return mockHttpOutputMessage.getBodyAsString();
	    }

}
