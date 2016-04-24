package org.hit.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GetConfigure {
	
	public String url;
	public String name;
	public String port;
	public String count;
	public String ip;
	public String version;
	public String redis_timeout;
	public String maxCap;
	public String maxCapOriginal;
	public String type;
	public String redis_host;
	public String redis_port;
	public String redis_queue;
	public String path;
	public String redis_result;
	public String cacheSize;
	public String proxy;
	public  GetConfigure(){
		  Properties pros = new Properties();
			try {

				    pros.load(GetConfigure.class.getResourceAsStream("/info.properties"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			url=pros.getProperty("url");
			name=pros.getProperty("name");
			port=pros.getProperty("port");
			count=pros.getProperty("count");
			ip=pros.getProperty("ip");
			version=pros.getProperty("version");
			redis_timeout=pros.getProperty("redis.timeout");
			maxCap=pros.getProperty("maxCap");
			maxCapOriginal=pros.getProperty("maxCapOriginal");
			type=pros.getProperty("type");
			redis_queue=pros.getProperty("redis.queue");
			 redis_host=pros.getProperty("redis.host");
			 redis_port=pros.getProperty("redis.port");
			 path=pros.getProperty("path");
			 redis_result=pros.getProperty("redis_result");
			 cacheSize=pros.getProperty("cacheSize");
			proxy=pros.getProperty("proxy");
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getRedis_timout() {
		return redis_timeout;
	}
	public void setRedis_timout(String redis_timout) {
		this.redis_timeout = redis_timout;
	}
	public String getMaxCap() {
		return maxCap;
	}
	public void setMaxCap(String maxCap) {
		this.maxCap = maxCap;
	}
	public String getMaxCapOriginal() {
		return maxCapOriginal;
	}
	public void setMaxCapOriginal(String maxCapOriginal) {
		this.maxCapOriginal = maxCapOriginal;
	}
	public String getType() {
		return type;
	}
	public String getcacheSize(){
		return cacheSize;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRedis_host() {
		return redis_host;
	}
	public void setRedis_host(String redis_host) {
		this.redis_host = redis_host;
	}
	public String getRedis_port() {
		return redis_port;
	}
	public void setRedis_port(String redis_port) {
		this.redis_port = redis_port;
	}
	public String getRedis_queue() {
		return redis_queue;
	}
	public void setRedis_queue(String redis_queue) {
		this.redis_queue = redis_queue;
	}
	public void setPath(String path){
		this.path=path;
	}
	public String getPath(){
		return path;
	}
	public String getRedis_Result(){
		return redis_result;
	}
	public String getProxy(){return proxy;}
}
