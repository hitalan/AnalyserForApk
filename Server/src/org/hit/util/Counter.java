package org.hit.util;

public class Counter 
{
	private int badChannelCount = 0;
	private int badClientCount = 0;
	public int getBadChannelCount() {
	return badChannelCount;
	}
	public void setBadChannelCount(int badChannelCount) {
	this.badChannelCount = badChannelCount;
	}
	public int getBadClientCount() {
	return badClientCount;
	}
	public void setBadClientCount(int badClientCount) {
	this.badClientCount = badClientCount;
	}
}
