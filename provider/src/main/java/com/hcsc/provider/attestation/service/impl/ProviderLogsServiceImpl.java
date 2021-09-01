package com.hcsc.provider.attestation.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcsc.provider.attestation.dao.IProviderLogsDao;
import com.hcsc.provider.attestation.model.ProviderLogs;
import com.hcsc.provider.attestation.service.IProviderLogsService;

@Service
public class ProviderLogsServiceImpl implements IProviderLogsService {

	@Autowired
	IProviderLogsDao providerLogsDao;

	@Override
	public List<ProviderLogs> listProviderLogs(Date date) {
		return providerLogsDao.getProviderLogs(date);
	}

}
