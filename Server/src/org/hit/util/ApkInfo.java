package org.hit.util;

public class ApkInfo {
private String apkType;	
private String signaTure;
private String  apkName;
private String packageName;
private String versionCode;
private String versionName;
private String dexHashCode;
private String apkHashCode;
public String getApkName() {
		return apkName;
	}
	public void setApkName(String apkName) {
		this.apkName = apkName;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public String getDexHashCode() {
		return dexHashCode;
	}
	public void setDexHashCode(String dexHashCode) {
		this.dexHashCode = dexHashCode;
	}	
	 public String getApkType() {
			return apkType;
	 }
	public void setApkType(String apkType) {
			this.apkType = apkType;
	}
	public String getApkHashCode() {
		return apkHashCode;
	}
	public void setApkHashCode(String apkHashCode) {
		this.apkHashCode = apkHashCode;
	}
	 public String getSignaTure() {
			return signaTure;
	}
	public void setSignaTure(String signaTure) {
			this.signaTure = signaTure;
	}
}
