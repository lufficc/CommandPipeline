package com.lufficc.command.core;

public class LinuxCommand extends RootCommand {

	public LinuxCommand() {
		super("/bin/bash", "/bin", -1);
	}
}
