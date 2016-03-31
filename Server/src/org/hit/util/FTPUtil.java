package org.hit.util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FTPUtil {
	 public static boolean uploadFile(String url, int port, String username,  String password, String path, String filename, InputStream input) {    
		boolean success = false;    
	    FTPClient ftp = new FTPClient();   
	    try {   
		     int reply;   
		     ftp.connect(url, port);   
		     ftp.login(username, password);      
		     reply = ftp.getReplyCode();   
		     System.out.println(reply); 
		     if (!FTPReply.isPositiveCompletion(reply)) {   
		        ftp.disconnect();   
		        return success;   
		     }    
			 ftp.setFileType(FTP.BINARY_FILE_TYPE);
		     ftp.changeWorkingDirectory(path);   
		     ftp.storeFile(filename, input);    
		     input.close();   
		     ftp.logout();    
		     success = true;   
		     } 
	    catch (IOException e) {   
		     e.printStackTrace();   
		} 
	    finally{   
		       if (ftp.isConnected()) {   
		        try {   
		         ftp.disconnect();   
		        } catch (IOException ioe) {   
		        }   
		       }   
		}   
		return success;   
		}     
		        
		        
	 public static boolean downFile(String url, int port, String username,   String password, String remotePath, String fileName,   String localPath) {    
	    boolean success = false;    
		FTPClient ftp = new FTPClient();   
		try {   
		    int reply;    
		    ftp.connect(url, port);   
		    ftp.login(username, password);   
		    reply = ftp.getReplyCode(); 
		    System.out.println(reply);
		    if (!FTPReply.isPositiveCompletion(reply)) {   
		        ftp.disconnect();   
		        return success;   
		     } 
		    ftp.changeWorkingDirectory(remotePath);    
		 	ftp.setFileType(FTP.BINARY_FILE_TYPE);
		    FTPFile[] fs = ftp.listFiles();    
			fileName=new String(fileName.getBytes("GBK"),"iso-8859-1");
		    for (FTPFile ff : fs) {   
		        if (ff.getName().equals(fileName)) {    
		         File localFile = new File(localPath + "/" + new String(ff.getName().getBytes("iso-8859-1"),"GBK"));    
		         OutputStream is = new FileOutputStream(localFile);    
		         ftp.retrieveFile(ff.getName(), is);   
		         is.close();   
		        }   
		    }   
		    ftp.logout();    
		       success = true;   
		    }
		catch (IOException e) {   
		       e.printStackTrace();   
		} 
		finally
		{   
		     if (ftp.isConnected()) {   
		        try {   
		         ftp.disconnect();   
		            } 
		        catch (IOException ioe) {   
		        }   
		       }   
		      }   
		      return success;   
		 }   
}
