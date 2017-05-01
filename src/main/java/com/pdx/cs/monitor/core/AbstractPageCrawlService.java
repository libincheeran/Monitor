package com.pdx.cs.monitor.core;

public abstract class AbstractPageCrawlService implements PageCrawlService{
	public abstract void delegateCrawl() throws MonitorException;
	public void crawl() throws MonitorException {
		init();
		delegateCrawl();
		destroy();
	}

}
