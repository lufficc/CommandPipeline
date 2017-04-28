package com.lufficc.command.pipeline;


import com.lufficc.command.core.CommandManager;
import com.lufficc.command.core.CommandManagerImpl;
import com.lufficc.command.core.CommandStatus;

import java.util.*;
import java.util.stream.Collectors;

public class CommandPipeline {
	private List<PipelineItem> pipelineItems = new ArrayList<>();
	private final CommandManager commandManager;

	public CommandPipeline() {
		this(new CommandManagerImpl());
	}

	public CommandPipeline(CommandManager commandManager) {
		this.commandManager = commandManager;
	}

	public CommandPipeline add(PipelineItem item) {
		pipelineItems.add(item);
		return this;
	}

	public void exec() {
		List<Map.Entry<Integer, List<PipelineItem>>> orderedPipelineItems = pipelineItems.stream()
				.collect(Collectors.groupingBy(PipelineItem::getOrder))
				.entrySet()
				.stream()
				.sorted(Comparator.comparing(Map.Entry::getKey))
				.collect(Collectors.toList());

		// print order
		orderedPipelineItems.forEach(sameOrderPipelineItems -> {
			int order = sameOrderPipelineItems.getKey();
			StringBuilder output = new StringBuilder(">>> Order " + order + ", [");
			for (PipelineItem item : sameOrderPipelineItems.getValue()) {
				output.append(item.getName()).append(", ");
			}
			output.delete(output.length() - 2, output.length())
					.append("]\n");
			System.out.println(output);
		});

		// execute
		orderedPipelineItems.forEach(sameOrderPipelineItems -> {
			System.out.println(">>>----------------------------------------");
			int order = sameOrderPipelineItems.getKey();
			System.out.println("executing order: " + order);
			sameOrderPipelineItems.getValue().forEach(pipelineItem -> {
				CommandStatus commandStatus = commandManager.exec(pipelineItem.getCommand());
				if (commandStatus.getCommand() != null) {
					System.out.println("command: " + commandStatus.getCommand());
					System.out.println("output:");
					System.out.println(commandStatus.getResult().getOutput());
				}
			});
			System.out.println("----------------------------------------<<<");
		});
	}

	public CommandManager getCommandManager() {
		return commandManager;
	}
}
