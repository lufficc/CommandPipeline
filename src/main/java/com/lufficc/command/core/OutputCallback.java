package com.lufficc.command.core;

/**
 * Callback for command execution
 */
public interface OutputCallback {
	/**
	 * called when new output come
	 *
	 * @param commandResult commandResult
	 */
	void onOutput(CommandResult commandResult);
}