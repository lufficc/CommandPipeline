package com.lufficc.command.core;


public class CommandResult {
	private int code = 1;
	private String output;
	private String exception;
	private boolean isRunning = false;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public boolean isSuccessful() {
		return code == 0;
	}


	public static CommandResult empty() {
		return new CommandResult();
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean running) {
		isRunning = running;
	}
}
