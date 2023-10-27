package com.graphql.selenium;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class ScaleUpPods {
	
	/*
	 * public static void main(String[] args) throws JSchException, IOException {
	 * scaleUp(1); }
	 */
	
	static String pemFile = "aztldevops_key.pem";
	static String user = "azureuser";
	static String ip = "98.70.10.104";
	static int port = 22;
	static Session session;
	static ChannelExec channel;
	
	private static void connect() throws JSchException {
		JSch sch = new JSch();
		sch.addIdentity(pemFile);
		session = sch.getSession(user, ip, port);
		session.setConfig("StrictHostKeyChecking", "no");
		session.connect();
		channel = (ChannelExec)session.openChannel("exec");
		System.out.println("Connection done");
	}

	public static void scaleUp(int numOfPods) throws JSchException, IOException {
	    connect();
	    String output = executeCommand("kubectl scale deployment.apps/selenium-node-chrome --replicas="+numOfPods);
	    System.out.println("Scale-Up Output: " + output);
	    tearDown();
	}

	public static String executeCommand(String command) throws IOException, JSchException {
	    channel.setCommand(command);
	    
	    InputStream in = channel.getInputStream();
	    channel.connect();

	    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	    StringBuilder output = new StringBuilder();
	    String line;
	    
	    while((line = reader.readLine()) != null) {
	        output.append(line).append("\n");
	    }

	    reader.close();
	    channel.disconnect();
	    
	    return output.toString();
	}

	
	public static void tearDown() {
		session.disconnect();
	}

}

