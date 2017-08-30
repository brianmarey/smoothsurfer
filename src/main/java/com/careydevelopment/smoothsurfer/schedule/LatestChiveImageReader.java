package com.careydevelopment.smoothsurfer.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.careydevelopment.smoothsurfer.chivereader.ChiveReader;

@Configuration
@EnableScheduling
public class LatestChiveImageReader {

	@Autowired
	ThreadPoolTaskExecutor taskExecutor;
	
	@Autowired
	ChiveReader chiveReader;
	
	@Scheduled(fixedDelay=86400000)
	void getFunnyImagesFromReddit() {		
		taskExecutor.execute(() -> {
			chiveReader.launch();
		});
	}
}
