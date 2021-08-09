package com.hcsc.provider.attestation.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;

import com.hcsc.provider.attestation.model.Provider;

public class StepSkipListener implements SkipListener<Provider, Provider> {
	
	private static final Logger log = LoggerFactory.getLogger(StepSkipListener.class);

    @Override
    public void onSkipInRead(Throwable t) {
    	log.info("StepSkipListener - onSkipInRead " + t.getMessage());
    }
 
    @Override
    public void onSkipInWrite(Provider item, Throwable t) {
        log.info("StepSkipListener - afterWrite");
    }
 
    @Override
    public void onSkipInProcess(Provider item, Throwable t) {
        log.error("Item with Provider ID : " + item.getProviderId() + " was skipped due to: " + t.getMessage());
    }
    
    
}
