package com.pwt.tracker.rules;

import org.easyrules.core.BasicRule;

import com.pwt.tracker.dao.AlertDAO;
import com.pwt.tracker.dao.AlertDAOImpl;
import com.pwt.tracker.entity.Alert;
import com.pwt.tracker.entity.Metric;


/**
 * 1. Detects if the person is overweight
 * 2. If condition evaluates to true 
 * 3. Triggers an action 
 * @author Shivam
 *
 */
public class OverWeightRule extends BasicRule  {
	
	private static final Double WEIGHTOFFSET = 0.10;
	private static final String ALERTMESSAGE ="YOU ARE OVERWEIGHT!!";
	
	private Metric metric;
	private int baseWeight;
	//Was not able to figure out how to inject alertdao
	private AlertDAO alertdao=new AlertDAOImpl();
	public OverWeightRule(Metric metric,int baseWeight) {
		super("OverWeightRule",
				"Create alert if weight of the person drops above 10% of his base weight");
		this.metric = metric;
		this.baseWeight=baseWeight;
	}



	/* 
	 * 1. Evaluate if baseweight is offset by 10%
	 * @see org.easyrules.core.BasicRule#evaluate()
	 */
	@Override
	public boolean evaluate() {
		return Double.parseDouble(metric.getValue()) > (this.baseWeight+(this.baseWeight*WEIGHTOFFSET));
	}

	/* 
	 * 1. Save alert if condition evaluates to true
	 * @see org.easyrules.core.BasicRule#execute()
	 */
	@Override
	public void execute() {
		Alert alert=new Alert();
		alert.setTimeStamp(metric.getTimestamp());
		alert.setValue(metric.getValue());
		alert.setAlertMessage(ALERTMESSAGE);
		alertdao.createAlert(alert);
	}
}
