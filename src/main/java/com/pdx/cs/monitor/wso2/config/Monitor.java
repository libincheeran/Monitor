package com.pdx.cs.monitor.wso2.config;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "services", "global" })
@Data
public class Monitor {
	@JsonProperty("services")
	private List<Service> services;

	@JsonProperty("global")
	private Global global;
}
