package com.velocity.model.transactions.query.response;

import com.velocity.gson.annotations.SerializedName;


/**
 * 
 * @author ranjitk
 *
 */


public class ServiceTransactionDateTime {
	
	@SerializedName("Date")
	private String date;
	@SerializedName("Time")
	private String time;
	@SerializedName("TimeZone")
	private String timeZone;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
}
