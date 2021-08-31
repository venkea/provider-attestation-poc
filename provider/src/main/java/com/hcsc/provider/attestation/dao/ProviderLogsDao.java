package com.hcsc.provider.attestation.dao;

import java.io.Serializable;

import com.hcsc.provider.attestation.model.ProviderLogs;

public interface ProviderLogsDao {


	public Serializable createOrUpdateProviderLogs(ProviderLogs providerLogs);

}
