package com.lufficc.command.core;


import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class CommandManagerImpl implements CommandManager {
	private ExecutorService taskScheduler = Executors.newFixedThreadPool(10);
	private final Command command = Command.getInstance();
	private Map<String, CommandStatus> commandStatuses = new LinkedHashMap<>();

	private Object doExec(String command, boolean sync, OutputCallback callback) {
		Object result;
		String uuid = UUID.randomUUID().toString();
		CommandStatus commandStatus = new CommandStatus();
		commandStatus.setUuid(uuid);
		commandStatus.setCommand(command);
		commandStatuses.put(uuid, commandStatus);
		if (sync) {
			CommandResult commandResult = this.command.exec(commandStatus.getCommand(), new Callback(commandStatus, callback));
			assert !commandResult.isRunning();
			commandStatus.setResult(commandResult);
			result = commandStatus;
		} else {
			taskScheduler.execute(new CommandRunnable(commandStatus, callback));
			result = uuid;
		}
		return result;
	}

	@Override
	public CommandStatus exec(String command) {
		return (CommandStatus) doExec(command, true, null);
	}

	@Override
	public String exec(String command, OutputCallback callback) {
		return (String) doExec(command, false, callback);
	}


	@Override
	public List<CommandStatus> get() {
		return new ArrayList<>(commandStatuses.values());
	}

	@Override
	public Optional<CommandStatus> get(String uuid) {
		return Optional.ofNullable(commandStatuses.get(uuid));
	}

	@Override
	public List<CommandStatus> get(Set<String> uuids) {
		return commandStatuses.entrySet()
				.stream()
				.filter(stringCommandStatusEntry -> uuids.contains(stringCommandStatusEntry.getKey()))
				.map(Map.Entry::getValue)
				.collect(Collectors.toList());
	}


	private class CommandRunnable implements Runnable {
		private final CommandStatus commandStatus;
		private final OutputCallback callback;

		CommandRunnable(CommandStatus commandStatus, OutputCallback callback) {
			this.commandStatus = commandStatus;
			this.callback = callback;
		}

		@Override
		public void run() {
			CommandResult commandResult = command.exec(commandStatus.getCommand(), new Callback(commandStatus, callback));
			commandStatus.setResult(commandResult);
		}
	}

	private class Callback implements OutputCallback {
		private final CommandStatus commandStatus;
		private final OutputCallback callback;

		private Callback(CommandStatus commandStatus) {
			this(commandStatus, null);
		}

		private Callback(CommandStatus commandStatus, OutputCallback callback) {
			this.commandStatus = commandStatus;
			this.callback = callback;
		}

		@Override
		public void onOutput(CommandResult commandResult) {
			commandStatus.setResult(commandResult);
			if (callback != null) {
				callback.onOutput(commandResult);
			}
		}
	}
}
