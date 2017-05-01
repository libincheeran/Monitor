package com.pdx.cs.monitor.core;

public interface PageCrawlService {
	public abstract void crawl() throws MonitorException;

	public abstract void init();

	public abstract void destroy();

}
