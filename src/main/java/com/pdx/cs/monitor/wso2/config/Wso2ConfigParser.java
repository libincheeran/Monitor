/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.pdx.cs.monitor.wso2.config;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class Wso2ConfigParser {
	private static final Logger logger = LoggerFactory.getLogger(Wso2ConfigParser.class);
	private static final String CONFIG_FILE = "config/wso2_service_monitor.yml";

	public static WSO2Config getWSO2Config() {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		try {
			File f = new File("config/wso2_service_monitor.yml");
			WSO2Config conf = (WSO2Config) mapper.readValue(f, WSO2Config.class);
			logger.debug("Config : " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(conf));
			return conf;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
}