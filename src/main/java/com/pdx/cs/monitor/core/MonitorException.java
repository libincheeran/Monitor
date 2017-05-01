package com.pdx.cs.monitor.core;

public class MonitorException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MonitorException() {
	}

	public MonitorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MonitorException(String message, Throwable cause) {
		super(message, cause);
	}

	public MonitorException(String message) {
		super(message);
	}

	public MonitorException(Throwable cause) {
		super(cause);
	}

}
