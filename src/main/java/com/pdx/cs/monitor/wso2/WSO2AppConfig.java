package com.pdx.cs.monitor.wso2;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.pdx.cs.monitor.core.Monitor;
import com.pdx.cs.monitor.core.PageCrawlService;
import com.pdx.cs.monitor.core.ParseService;
import com.pdx.cs.monitor.core.ReportService;
import com.pdx.cs.monitor.wso2.config.WSO2Config;
import com.pdx.cs.monitor.wso2.config.Wso2ConfigParser;

public class WSO2AppConfig extends AbstractModule {
	protected void configure() {
		bind(Monitor.class).to(WSO2ServicesMonitorImpl.class);
		bind(PageCrawlService.class).to(WSO2StatsPageCrawlerServiceImpl.class);
		bind(ParseService.class).to(WSO2StatParseServiceImpl.class);
		bind(ReportService.class).to(WSO2StatReportServiceImpl.class);
	}

	@Provides
	@Singleton
	public WSO2Config get() {
		return Wso2ConfigParser.getWSO2Config();
	}
}