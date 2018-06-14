package com.pdx.cs.monitor.wso2.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "url", "login" })
@Data
public class Global {

	@JsonProperty("urls")
	private List<String> urls;

	@JsonProperty("login")
	private Login login;

}
