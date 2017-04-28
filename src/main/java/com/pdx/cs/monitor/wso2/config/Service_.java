package com.pdx.cs.monitor.wso2.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "msg-crit-pending", "avg-warn-res-time", "name", "url", "avg-crit-res-time", "msg-warn-pending" })
@Data 
public class Service_ {
	@JsonProperty("msg-crit-pending")
	private long msgCritPending;

	@JsonProperty("avg-warn-res-time")
	private long avgWarnResTime;

	@JsonProperty("name")
	private String name;

	@JsonProperty("url")
	private String url;

	@JsonProperty("avg-crit-res-time")
	private long avgCritResTime;

	@JsonProperty("msg-warn-pending")
	private long msgWarnPending;
}
