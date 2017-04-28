package com.lufficc.command.core;


import java.io.*;
import java.util.concurrent.TimeUnit;

public abstract class Command {
	private static Command INSTANCE;

	public static Command getInstance() {
		if (INSTANCE == null) {
			synchronized (Command.class) {
				if (INSTANCE == null) {
					String os = System.getProperty("os.name");
					if (os.toLowerCase().startsWith("win")) {
						INSTANCE = new WindowsCommand();
					} else {
						INSTANCE = new LinuxCommand();
					}
				}
			}
		}
		return INSTANCE;
	}


	public abstract CommandResult exec(String commands);

	public abstract CommandResult exec(String commands, OutputCallback callback);

	public abstract CommandResult exec(String commands, String dir);

	public abstract CommandResult exec(String commands, String dir, OutputCallback callback);

	public abstract CommandResult exec(String commands, int timeoutInSeconds);

	public abstract CommandResult exec(String commands, int timeoutInSeconds, OutputCallback callback);

	public abstract CommandResult exec(String commands, String dir, int timeoutInSeconds);

	public abstract CommandResult exec(String commands, String dir, int timeoutInSeconds, OutputCallback callback);

	private String getString(InputStream stream, CommandResult commandResult, OutputCallback outputCallback) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(stream));
		String line;
		StringBuilder stringBuilder = new StringBuilder();
		String lineSeparator = System.getProperty("line.separator");
		while ((line = in.readLine()) != null) {
			stringBuilder
					.append(line)
					.append(lineSeparator);
			if (commandResult != null && outputCallback != null) {
				commandResult.setOutput(stringBuilder.toString());
				outputCallback.onOutput(commandResult);
			}
		}
		in.close();
		return stringBuilder.toString();
	}

	/**
	 * @param rootCommand      win:cmd, linux /bin/bash
	 * @param commands         command to execute
	 * @param directory        work direction
	 * @param timeoutInSeconds timeout in seconds, -1 no time no timeout
	 * @return CommandResult
	 */
	CommandResult execute(String rootCommand, String commands, String directory, int timeoutInSeconds, OutputCallback callback) {
		if (commands == null || commands.isEmpty()) {
			return CommandResult.empty();
		}
		CommandResult commandResult = null;
		int code;
		try {
			ProcessBuilder processBuilder = new ProcessBuilder(rootCommand);
			if (directory != null && !directory.isEmpty()) {
				processBuilder.directory(new File(directory));
			}
			processBuilder.redirectErrorStream(true);
			Process process = processBuilder.start();
			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(process.getOutputStream())), true);
			out.println(commands);
			out.println("exit");
			commandResult = new CommandResult();
			commandResult.setRunning(true);
			String output = getString(process.getInputStream(), commandResult, callback);
			commandResult.setOutput(output);
			if (timeoutInSeconds < 0) {
				code = process.waitFor();
			} else {
				code = process.waitFor(timeoutInSeconds, TimeUnit.SECONDS) ? 0 : 1;
			}
			commandResult.setRunning(false);
			if (callback != null) {
				callback.onOutput(commandResult);
			}
			out.close();
			process.destroy();
		} catch (Exception e) {
			e.printStackTrace();
			code = 1;
			if (commandResult == null) {
				commandResult = new CommandResult();
			}
			commandResult.setException(e.getMessage());
		}
		commandResult.setCode(code);
		commandResult.setRunning(false);
		return commandResult;
	}

}
