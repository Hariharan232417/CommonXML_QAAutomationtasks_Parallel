package com.graphql.selenium;

import java.io.IOException;

import org.json.JSONObject;
import org.testng.annotations.Test;

import com.jcraft.jsch.JSchException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetQueueSize {

	
	@Test
	public void scaleUPBasedOnQueue() throws JSchException, IOException
	{
		

		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/json");
		
		 long startTime = System.currentTimeMillis();
	        long durationInMillis = 20 * 60 * 1000;
		
		while(true)
		{
			
			// Construct the GraphQL Body
			RequestBody requestBody = RequestBody.create(mediaType,
				"{\"query\": \" query Summary { grid { nodeCount,maxSession,sessionCount,sessionQueueSize } } \"}");

			// Build the request to the Selenium Grid
			Builder builder = new Request.Builder();
			Request request = builder.url("http://98.70.10.104:32000/graphql").post(requestBody).build();

			// Execute the request
			Response response = client.newCall(request).execute();
			String responseBody = response.body().string();

			// Convert the String to JSON
			JSONObject json = new JSONObject(responseBody);
			int queueSize = json.getJSONObject("data").getJSONObject("grid").getInt("sessionQueueSize");
			int nodeCount = json.getJSONObject("data").getJSONObject("grid").getInt("nodeCount");
			int maxSession = json.getJSONObject("data").getJSONObject("grid").getInt("maxSession");
			int sessionCount = json.getJSONObject("data").getJSONObject("grid").getInt("sessionCount");
			
			
			int threshold = nodeCount *maxSession;
			
			int extraPodsNeeded = queueSize+sessionCount;

			// Scale Up Algorithm
			if (threshold <extraPodsNeeded || queueSize>0) {
				System.out.println("Queue size = "+queueSize);
    			System.out.println("Node Count = "+nodeCount);
    			System.out.println("Max session = "+maxSession);
				
				ScaleUpPods.scaleUp(extraPodsNeeded);
			}
			
			
            
            if (queueSize == 0 && sessionCount == 0) {
				long currentTime = System.currentTimeMillis();
				System.out.println("currentTime = "+currentTime);
				System.out.println("durationInMillis = "+durationInMillis);
				if (currentTime - startTime >= durationInMillis) {
					long diff = currentTime - startTime;
					System.out.println("currentTime - startTime = "+diff);
					System.out.println("Scaling down to 1 as all tests are completed");
					ScaleUpPods.scaleUp(1); // Scale down logic may need to be implemented
					break;
				}
			}
            
            
		}

	}
	
}
