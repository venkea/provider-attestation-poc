package com.hcsc.provider.attestation.writer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

public class ConsoleItemWriter<T> implements ItemWriter<T> { 

	private static final Logger LOG = LoggerFactory.getLogger(ConsoleItemWriter.class);

	@Override
	public void write(List<? extends T> providers) throws Exception {
		LOG.info("Console item writer starts");
		LOG.info("Total Providers to Write " + providers.size());
		for (T provider : providers) {
			LOG.info("Writing  " + provider); 
        } 
		 LOG.info("Console item writer ends");
	} 
}
