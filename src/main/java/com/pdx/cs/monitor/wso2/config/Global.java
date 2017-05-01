package com.pdx.cs.monitor.wso2.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "url", "login" })
@Data
public class Global {

	@JsonProperty("url")
	private String url;

	@JsonProperty("login")
	private Login login;

}
