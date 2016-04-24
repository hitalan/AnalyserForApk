package org.hit.util;

import java.util.HashMap;
import java.util.List;

public class MessageInfo {

    private String subtaskId;
	private List<HashMap<String,String>> clientsApp;
	private HashMap<String ,String> channelApp;
	
	public String getSubtaskId() {
		return subtaskId;
	}
	public void setSubtaskId(String subtaskId) {
		this.subtaskId = subtaskId;
	}
	public List<HashMap<String,String>> getClientsApp() {
		return clientsApp;
	}
	public void setClientsApp(List<HashMap<String,String>> clientsApp) {
		this.clientsApp = clientsApp;
	}
	public HashMap<String,String> getChannelApp() {
		return channelApp;
	}
	public void setChannelApp(HashMap<String,String> channelApp) {
		this.channelApp = channelApp;
	}
	
}
