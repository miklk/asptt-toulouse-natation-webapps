package com.asptttoulousenatation.core.client.ui;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.StackPanel;

public class StackAwarePanel extends StackPanel {

	private Map<Integer, Command> commands;
	
	
	
	public StackAwarePanel() {
		super();
		commands = new HashMap<Integer, Command>();
	}

	public void addCommand(int pIndex, Command pCommand) {
		commands.put(pIndex, pCommand);
	}

	@Override
	public void showStack(int pIndex) {
		Command command = commands.get(pIndex);
		if(command != null) {
			command.execute();
		}
		super.showStack(pIndex);
		
	}

	
}
