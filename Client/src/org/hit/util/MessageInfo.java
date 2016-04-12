package org.hit.util;

import java.util.HashMap;
import java.util.List;

public class MessageInfo {
	  private String subtaskId;
		private List<HashMap<String,String>> clientsAppUrls;
		private HashMap<String ,String> channelsAppUrl;
		public String getSubtaskId() {
			return subtaskId;
		}
		public void setSubtaskId(String subtaskId) {
			this.subtaskId = subtaskId;
		}
		public List<HashMap<String,String>> getClientsAppUrls() {
			return clientsAppUrls;
		}
		public void setClientsAppUrls(List<HashMap<String,String>> clientsAppUrls) {
			this.clientsAppUrls = clientsAppUrls;
		}
		public HashMap<String,String> getChannelsAppUrl() {
			return channelsAppUrl;
		}
		public void setChannelsAppUrl(HashMap<String,String> channelsAppUrl) {
			this.channelsAppUrl = channelsAppUrl;
		}
    /*private String taskId;
	private String channelId;
	private List<String> clientsAppUrls;
	private String channelsAppUrl;
	
	
	public List<String> getClientsAppUrls() 
	{
		return clientsAppUrls;
	}
	
	public void setClientsAppUrls(List<String> clientsAppUrls) 
	{
		this.clientsAppUrls = clientsAppUrls;
	}
	
	public String getChannelsAppUrl() 
	{
		return channelsAppUrl;
	}
	
	public void setChannelsAppUrl(String channelsAppUrl) 
	{
		this.channelsAppUrl = channelsAppUrl;
	}
    public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}*/
	
	
	
}
