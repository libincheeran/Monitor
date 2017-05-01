package com.pdx.cs.monitor.core;

import lombok.NonNull;

public interface ReportService {
	public abstract void generateReport(@NonNull String paramString) throws MonitorException;
}
