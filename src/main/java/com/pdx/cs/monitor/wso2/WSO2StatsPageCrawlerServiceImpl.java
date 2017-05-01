package com.pdx.cs.monitor.wso2;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.pdx.cs.monitor.core.AbstractPageCrawlService;
import com.pdx.cs.monitor.core.MonitorException;
import com.pdx.cs.monitor.core.ParseService;
import com.pdx.cs.monitor.core.ReportService;
import com.pdx.cs.monitor.wso2.config.Service;
import com.pdx.cs.monitor.wso2.config.WSO2Config;

public class WSO2StatsPageCrawlerServiceImpl extends AbstractPageCrawlService{
	private static final Logger logger = LoggerFactory.getLogger(WSO2StatsPageCrawlerServiceImpl.class);
	private static final String INPUT_USER_NAME = "username";
	private static final String INPUT_PASSWORD = "password";
	private static final String INPUT_SIGN_IN = "Sign-in";
	private static final String STAT_URL = "statistics/service_stats_ajaxprocessor.jsp?serviceName=";
	private final WSO2Config wso2Config;
	private final ParseService parseService;
	private final ReportService reportService;
	private WebClient webClient;

	@Inject
	public WSO2StatsPageCrawlerServiceImpl(WSO2Config config, ParseService parseService, ReportService reportService) {
		this.wso2Config = config;
		this.parseService = parseService;
		this.reportService = reportService;
	}

	public void delegateCrawl() throws MonitorException {
		login();

		List<Service> services = this.wso2Config.getMonitor().getServices();

		for (Service s : services) {
			String output = crawlServices(s);
			String reportInput = this.parseService.parse(output, s.getService());
			this.reportService.generateReport(reportInput);
		}
	}

	private String crawlServices(Service s) throws MonitorException {
		try {
			String url = this.wso2Config.getMonitor().getGlobal().getUrl() + "/"
					+ "statistics/service_stats_ajaxprocessor.jsp?serviceName=" + s.getService().getName();

			logger.debug(url);
			HtmlPage page3 = (HtmlPage) this.webClient.getPage(url);

			logger.debug(page3.asText());

			return page3.asText();
		} catch (Exception e) {
			throw new MonitorException(e.getMessage(), e);
		}
	}

	private void login() throws MonitorException {
		try {
			this.webClient = new WebClient();

			this.webClient.getOptions().setUseInsecureSSL(true);
			this.webClient.getOptions().setJavaScriptEnabled(false);
			HtmlPage page1 = (HtmlPage) this.webClient.getPage(this.wso2Config.getMonitor().getGlobal().getUrl());
			CookieManager cookieMan = new CookieManager();
			cookieMan = this.webClient.getCookieManager();
			cookieMan.setCookiesEnabled(true);

			HtmlInput uname = (HtmlInput) page1.getElementByName("username");
			uname.setValueAttribute(this.wso2Config.getMonitor().getGlobal().getLogin().getUname());
			HtmlInput pwd = (HtmlInput) page1.getElementByName("password");
			pwd.setValueAttribute(this.wso2Config.getMonitor().getGlobal().getLogin().getPwd());
			HtmlForm form = (HtmlForm) page1.getForms().get(0);
			HtmlSubmitInput submit = (HtmlSubmitInput) form.getInputByValue("Sign-in");
			submit.click();
		} catch (Exception e) {
			throw new MonitorException(e.getMessage(), e);
		}
	}

	public void init() {
	}

	public void destroy() {
		if (this.webClient != null)
			this.webClient.close();
	}

}
