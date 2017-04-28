package com.lufficc.command.core;


public class RootCommand extends Command {
	private String rootCommand;
	private String defaultDirection;

	public RootCommand(String rootCommand, String defaultDirection, int defaultTimeout) {
		this.rootCommand = rootCommand;
		this.defaultDirection = defaultDirection;
		this.defaultTimeout = defaultTimeout;
	}

	private int defaultTimeout;


	@Override
	public CommandResult exec(String commands) {
		return execute(rootCommand, commands, defaultDirection, defaultTimeout, null);
	}

	@Override
	public CommandResult exec(String commands, OutputCallback callback) {
		return execute(rootCommand, commands, defaultDirection, defaultTimeout, callback);
	}

	@Override
	public CommandResult exec(String commands, String dir) {
		return execute(rootCommand, commands, dir, defaultTimeout, null);
	}

	@Override
	public CommandResult exec(String commands, String dir, OutputCallback callback) {
		return execute(rootCommand, commands, dir, defaultTimeout, callback);
	}

	@Override
	public CommandResult exec(String commands, int timeoutInSeconds) {
		return execute(rootCommand, commands, defaultDirection, timeoutInSeconds, null);
	}

	@Override
	public CommandResult exec(String commands, int timeoutInSeconds, OutputCallback callback) {
		return execute(rootCommand, commands, defaultDirection, timeoutInSeconds, callback);
	}

	@Override
	public CommandResult exec(String commands, String dir, int timeoutInSeconds) {
		return execute(rootCommand, commands, dir, timeoutInSeconds, null);
	}

	@Override
	public CommandResult exec(String commands, String dir, int timeoutInSeconds, OutputCallback callback) {
		return execute(rootCommand, commands, dir, timeoutInSeconds, callback);
	}
}
