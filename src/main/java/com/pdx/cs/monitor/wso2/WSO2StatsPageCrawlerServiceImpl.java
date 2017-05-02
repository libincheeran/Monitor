package com.pdx.cs.monitor.wso2;

import java.io.RandomAccessFile;
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
import com.pdxinc.sec.DecryptionService;
import com.pdxinc.sec.EncryptionServiceImpl;
import com.pdxinc.sec.ObfuscationInputFile;

public class WSO2StatsPageCrawlerServiceImpl extends AbstractPageCrawlService {
	private static final Logger logger = LoggerFactory.getLogger(WSO2StatsPageCrawlerServiceImpl.class);
	private static final String INPUT_USER_NAME = "username";
	private static final String INPUT_PASSWORD = "password";
	private static final String INPUT_SIGN_IN = "Sign-in";
	private static final String STAT_URL = "/statistics/service_stats_ajaxprocessor.jsp?serviceName=";
	private static final String OBF_FIle = "config/file.obf";
	private final WSO2Config wso2Config;
	private final ParseService parseService;
	private final ReportService reportService;

	@Inject
	public WSO2StatsPageCrawlerServiceImpl(WSO2Config config, ParseService parseService, ReportService reportService) {
		this.wso2Config = config;
		this.parseService = parseService;
		this.reportService = reportService;
	}

	public void delegateCrawl() throws MonitorException {
		
		try(WebClient webClient = login()) {
			
			List<Service> services = this.wso2Config.getMonitor().getServices();

			for (Service s : services) {
				String output = crawlServices(s,webClient);
				String reportInput = this.parseService.parse(output, s.getService());
				this.reportService.generateReport(reportInput);
			}
			
		}catch (MonitorException e ){
			throw e;
		}
	}

	private String crawlServices(Service s, WebClient webClient) throws MonitorException {
		try {
			String url = this.wso2Config.getMonitor().getGlobal().getUrl() + STAT_URL + s.getService().getName();
			logger.debug(url);
			HtmlPage page3 = (HtmlPage) webClient.getPage(url);
			logger.debug(page3.asText());
			return page3.asText();
		} catch (Exception e) {
			throw new MonitorException(e.getMessage(), e);
		}
	}

	private WebClient login() throws MonitorException {
		WebClient webClient = null;
		try {
			webClient = new WebClient();

			webClient.getOptions().setUseInsecureSSL(true);
			webClient.getOptions().setJavaScriptEnabled(false);
			HtmlPage page1 = (HtmlPage) webClient.getPage(wso2Config.getMonitor().getGlobal().getUrl());
			CookieManager cookieMan = new CookieManager();
			cookieMan = webClient.getCookieManager();
			cookieMan.setCookiesEnabled(true);

			HtmlInput uname = (HtmlInput) page1.getElementByName(INPUT_USER_NAME);
			uname.setValueAttribute(this.wso2Config.getMonitor().getGlobal().getLogin().getUname());
			HtmlInput pwd = (HtmlInput) page1.getElementByName(INPUT_PASSWORD);

			// retrieve pwd and decrypt
			ObfuscationInputFile in = new ObfuscationInputFile(new RandomAccessFile(OBF_FIle, "r"));
			byte[] inBuffer = in.readBlock();
			in.close();
			String inString1 = new String(inBuffer, "UTF-8");
			DecryptionService de = new EncryptionServiceImpl();
			String decryptedPwd = de.decrypt(inString1, wso2Config.getMonitor().getGlobal().getLogin().getKey());
			pwd.setValueAttribute(decryptedPwd);

			HtmlForm form = (HtmlForm) page1.getForms().get(0);
			HtmlSubmitInput submit = (HtmlSubmitInput) form.getInputByValue(INPUT_SIGN_IN);
			submit.click();
		} catch (Exception e) {
			throw new MonitorException(e.getMessage(), e);
		}
		return webClient;
	}

	public void init() {
	}

	public void destroy() {
	}

}
