package com.pdx.cs.monitor.wso2.config;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class Wso2ConfigParser {
	private  static final Logger logger = LoggerFactory.getLogger(Wso2ConfigParser.class);
	private  static final String CONFIG_FILE = "/wso2_service_monitor.yml";
	private static String CONFIG_DIR = "config";

	static {
		String path = System.getProperty("config.path");
		if( path != null && !path.isEmpty()){
			CONFIG_DIR = path;
		}
	}

	public static WSO2Config getWSO2Config() {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		try {
			File f = new File(CONFIG_DIR+CONFIG_FILE);
			WSO2Config conf = (WSO2Config) mapper.readValue(f, WSO2Config.class);
			logger.debug("Config : " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(conf));
			return conf;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return null;
	}
}