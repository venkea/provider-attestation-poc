package com.hcsc.provider.attestation.service;

import java.util.Date;
import java.util.List;

import com.hcsc.provider.attestation.model.ProviderLogs;

public interface IProviderLogsService {


	public List<ProviderLogs> listProviderLogs(Date date);

}
