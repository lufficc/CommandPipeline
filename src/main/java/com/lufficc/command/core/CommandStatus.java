package com.lufficc.command.core;


public class CommandStatus {

	private String uuid;
	private String command;
	private CommandResult result;

	public CommandStatus() {
	}

	public CommandStatus(CommandResult result) {
		this.result = result;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public CommandResult getResult() {
		return result;
	}

	public void setResult(CommandResult result) {
		this.result = result;
	}
}
