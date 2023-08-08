package com.universal.em.crons;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Configuration
@EnableAsync
public class CommonCrons {

	@Value("${crons-url}")
	private String url;
	Logger logger = LoggerFactory.getLogger(CommonCrons.class);

	@Scheduled(fixedRate = 1000 * 60 * 10)
	public void scheduler() {
		logger.info("Crons Hit Url {}", url);
		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			Request request1 = new Request.Builder().url(url)
					.method("GET", null).addHeader("accept", "*/*").build();
			Response response = client.newCall(request1).execute();
			logger.info("Swagger Api hit sucessfully");
			response.close();
		}catch (Exception e){
			try {
				e.printStackTrace();
				OkHttpClient client = new OkHttpClient().newBuilder().build();
				Request request1 = new Request.Builder().url("https://www.google.com/")
						.method("GET", null).addHeader("accept", "*/*").build();
				Response response = client.newCall(request1).execute();
				logger.info("Google Api hit sucessfully");
				response.close();
			}catch (Exception e1){
				logger.info(e1.getMessage());
			}
		}
	}
}