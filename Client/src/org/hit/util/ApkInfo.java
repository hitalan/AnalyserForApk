package org.hit.util;

import java.util.List;

public class ApkInfo {
	
    private String taskId;
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
	}
}
