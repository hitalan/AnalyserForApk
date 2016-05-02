package org.hit.util;
import java.io.IOException;
import java.util.Properties;

public class GetConfigure {
	
	private String url;
	private String name;
	private String port;
	private String count;
	private String ip;
	private String version;
	private String redis_timeout;
	private String maxCap;
	private String maxCapOriginal;
	private String type;
	private String redis_host;
	private String redis_port;
	private String redis_queue;
	private String path;
	private String redis_result;
	private String cacheSize;
	private String proxy;
	private String downloadChannelPath;
	private String downloadClientPath;
	private String downloadUrl;
	private String shellPath;
	private String analyzerPath;
	private String whiteListUrl;
	private String downloadSecondClientPath;
	public String getDownloadSecondClientPath() {
		return downloadSecondClientPath;
	}

	public void setDownloadSecondClientPath(String downloadSecondClientPath) {
		this.downloadSecondClientPath = downloadSecondClientPath;
	}

	public String getWhiteListUrl() {
		return whiteListUrl;
	}

	public void setWhiteListUrl(String whiteListUrl) {
		this.whiteListUrl = whiteListUrl;
	}

	public String getAnalyzerPath() {
		return analyzerPath;
	}

	public void setAnalyzerPath(String analyzerPath) {
		this.analyzerPath = analyzerPath;
	}

	public String getShellPath() {
		return shellPath;
	}

	public void setShellPath(String shellPath) {
		this.shellPath = shellPath;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public  GetConfigure(){
		  Properties pros = new Properties();
			try {
				    pros.load(GetConfigure.class.getResourceAsStream("/info.properties"));
			} catch (IOException e) {
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
			downloadChannelPath=pros.getProperty("downloadChannelPath");
			downloadClientPath=pros.getProperty("downloadClientPath");
			downloadUrl=pros.getProperty("downloadUrl");
			shellPath=pros.getProperty("shellPath");
			analyzerPath=pros.getProperty("analyzerPath");
			whiteListUrl=pros.getProperty("whiteListUrl");
			downloadSecondClientPath=pros.getProperty("downloadSecondClientPath");
	}
	
	public String getRedis_timeout() {
		return redis_timeout;
	}

	public void setRedis_timeout(String redis_timeout) {
		this.redis_timeout = redis_timeout;
	}

	public String getRedis_result() {
		return redis_result;
	}

	public void setRedis_result(String redis_result) {
		this.redis_result = redis_result;
	}

	public String getCacheSize() {
		return cacheSize;
	}

	public void setCacheSize(String cacheSize) {
		this.cacheSize = cacheSize;
	}

	public String getDownloadChannelPath() {
		return downloadChannelPath;
	}

	public void setDownloadChannelPath(String downloadChannelPath) {
		this.downloadChannelPath = downloadChannelPath;
	}

	public String getDownloadClientPath() {
		return downloadClientPath;
	}

	public void setDownloadClientPath(String downloadClientPath) {
		this.downloadClientPath = downloadClientPath;
	}

	public void setProxy(String proxy) {
		this.proxy = proxy;
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
