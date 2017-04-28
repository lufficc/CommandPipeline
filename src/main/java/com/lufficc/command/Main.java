package com.lufficc.command;


import com.lufficc.command.pipeline.CommandPipeline;
import com.lufficc.command.pipeline.PipelineItem;

public class Main {
	public static void main(String[] args) throws Exception {
		CommandPipeline commandPipeline = new CommandPipeline();
		commandPipeline.add(new PipelineItem("ping", 3, "ping -n 3 lufficc.com"))
				.add(new PipelineItem("ping", 2, "ping -n 1 lufficc.com"))
				.add(new PipelineItem("java", 1, "java -version"))
				.add(new PipelineItem("java", 1, "java -version"))
				.exec();
		commandPipeline.getCommandManager().exec("abc", commandResult -> {
			if (!commandResult.isRunning()) {
				System.out.println(commandResult.getOutput());
			}
		});
	}
}
