package com.pdx.cs.monitor.core;

import com.pdx.cs.monitor.wso2.config.Service_;

import lombok.NonNull;

public interface ParseService {
	public abstract String parse(@NonNull String paramString, @NonNull Service_ paramService_, @NonNull String url) throws MonitorException;
}
