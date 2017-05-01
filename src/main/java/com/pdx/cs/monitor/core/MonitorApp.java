
package com.pdx.cs.monitor.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.pdx.cs.monitor.wso2.WSO2AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitorApp {
	private static final Logger logger = LoggerFactory.getLogger(MonitorApp.class);

	public static void main(String[] args) {
		try {
			Injector injector = Guice.createInjector(new Module[] { new WSO2AppConfig() });
			Monitor monitor = (Monitor) injector.getInstance(Monitor.class);
			monitor.run();
		} catch (MonitorException e) {
			logger.error(e.getMessage(), e);
		}
	}
}