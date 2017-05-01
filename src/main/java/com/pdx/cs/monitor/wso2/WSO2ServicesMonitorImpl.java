package com.pdx.cs.monitor.wso2;

import javax.inject.Inject;

import com.pdx.cs.monitor.core.Monitor;
import com.pdx.cs.monitor.core.MonitorException;
import com.pdx.cs.monitor.core.PageCrawlService;

public class WSO2ServicesMonitorImpl implements Monitor {
	private final PageCrawlService pageCrawlService;

	@Inject
	public WSO2ServicesMonitorImpl(PageCrawlService pageCrawlService) {
		this.pageCrawlService = pageCrawlService;
	}

	public void run() throws MonitorException {
		this.pageCrawlService.crawl();
	}

}
