package com.pdx.cs.monitor.wso2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pdx.cs.monitor.core.MonitorException;
import com.pdx.cs.monitor.core.ReportService;

public class WSO2StatReportServiceImpl implements ReportService{
	private static final Logger logger = LoggerFactory.getLogger("wso2_sysout");

	public void generateReport(String input) throws MonitorException {
		logger.info(input);
		System.out.print(input);
	}

}
