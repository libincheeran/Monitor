package com.pdx.cs.monitor.wso2.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "url", "uname", "pwd" })
@Data
public class Login {
	@JsonProperty("url")
	private String url;

	@JsonProperty("uname")
	private String uname;

	@JsonProperty("pwd")
	private String pwd;
}
