package com.lufficc.command.core;


import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CommandManager {
	/**
	 * exec a command synchronously
	 *
	 * @param command command to exec
	 * @return CommandStatus
	 */
	CommandStatus exec(String command);

	/**
	 * exec a command asynchronously
	 *
	 * @param command  command to exec
	 * @param callback OutputCallback
	 * @return uuid for the command
	 */
	String exec(String command, OutputCallback callback);

	/**
	 * get all commands
	 *
	 * @return commands
	 */
	List<CommandStatus> get();

	/**
	 * get a special command
	 *
	 * @param uuid uuid for the command
	 * @return command
	 */
	Optional<CommandStatus> get(String uuid);

	/**
	 * get special commands
	 *
	 * @param uuids uuids of commands
	 * @return commands
	 */
	List<CommandStatus> get(Set<String> uuids);
}
