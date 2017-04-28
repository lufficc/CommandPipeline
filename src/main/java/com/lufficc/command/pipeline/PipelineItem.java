package com.lufficc.command.pipeline;


public class PipelineItem {
	private String name;
	private final int order;
	private String command;

	public PipelineItem(String name, int order) {
		this(name, order, null);
	}

	public PipelineItem(String name, int order, String command) {
		this.name = name;
		this.order = order;
		this.command = command;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrder() {
		return order;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
}
