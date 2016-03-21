
package com.bupt.listviewtest;

public class Fruit {
private String name;
private int imageId;
public Fruit(String name,int imagedId){
	this.name=name;
	this.imageId = imagedId;	
}
public String getName(){
	return name;
}

public int getImageId(){
	return imageId;
}
}
