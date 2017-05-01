package com.pdx.cs.monitor.wso2;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.pdx.cs.monitor.core.MonitorException;
import com.pdx.cs.monitor.core.ParseService;
import com.pdx.cs.monitor.wso2.config.Monitor;
import com.pdx.cs.monitor.wso2.config.Service;
import com.pdx.cs.monitor.wso2.config.Service_;
import com.pdx.cs.monitor.wso2.config.WSO2Config;

public class WSO2PareServiceTest {
	
/*	Statistics
	Request Count	19
	Response Count	0
	Fault Count	0
	Maximum Response Time	0ms
	Minimum Response Time	< 1.00 ms
	Average Response Time	0.0 ms*/
	
	private static WSO2Config config = null;
	
	@BeforeClass
	public static void initConfig(){
		Service_ s1 = new Service_();
		s1.setName("s1");
		s1.setAvgCritResTime(1000);
		s1.setMsgCritPending(10);
		
		Service_ s2 = new Service_();
		s2.setName("s2");
		s2.setAvgCritResTime(500);
		s2.setMsgCritPending(25);

		Service service1 = new Service();
		service1.setService(s1);
		Service service2 = new Service();
		service2.setService(s2);
		
		List<Service> sList = new ArrayList<Service>();
		sList.add(service1);
		sList.add(service2);
		
		Monitor monitor = new Monitor();
		monitor.setServices(sList);
		config = new WSO2Config();
		config.setMonitor(monitor);
	}
	
	@Test
	public void testParseForAVGResponseTimeHealthBad() {
		ParseService parseService = new WSO2StatParseServiceImpl();
		String input = "Average Response Time	2000 ms";
		String output = null;
		try {
			output = parseService.parse(input, config.getMonitor().getServices().get(0).getService());
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		String[] lines = output.split("\n");
		assertTrue(lines[0].indexOf("HEALTH_BAD")!= -1);
	}
	
	@Test
	public void testParseForAVGResponseTimeHealthGood() {
		ParseService parseService = new WSO2StatParseServiceImpl();
		String input = "Average Response Time	500 ms";
		String output = null;
		try {
			output = parseService.parse(input, config.getMonitor().getServices().get(0).getService());
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		String[] lines = output.split("\n");
		assertTrue(lines[0].indexOf("HEALTH_OK")!= -1);
	}
	
	@Test
	public void testParseForPendingCountHealthGood() {
		ParseService parseService = new WSO2StatParseServiceImpl();
		String input = 	"Request Count	25 \n Response Count	20";
		String output = null;
		try {
			output = parseService.parse(input, config.getMonitor().getServices().get(0).getService());
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		String[] lines = output.split("\n");
		assertTrue(lines[1].indexOf("HEALTH_OK")!= -1);
	}
	
	@Test
	public void testParseForPendingCountHealthBad() {
		ParseService parseService = new WSO2StatParseServiceImpl();
		String input = 	"Request Count	25 \n Response Count	10";
		String output = null;
		try {
			output = parseService.parse(input, config.getMonitor().getServices().get(0).getService());
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		String[] lines = output.split("\n");
		assertTrue(lines[1].indexOf("HEALTH_BAD")!= -1);
	}
	
}
