package com.hcsc.provider.attestation.dao;

import java.util.Date;
import java.util.List;

import com.hcsc.provider.attestation.model.ProviderLogs;

public interface IProviderLogsDao {

	public List<ProviderLogs> getProviderLogs(Date date);

}