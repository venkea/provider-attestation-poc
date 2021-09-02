package com.hcsc.provider.attestation.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcsc.provider.attestation.model.ProviderLogs;
import com.hcsc.provider.attestation.service.IProviderLogsService;

@RestController
@RequestMapping(value = "/api/logs")
public class ProviderLogsController {

	@Autowired
	IProviderLogsService providerLogsService;

	@GetMapping(value = "/{date}")
	public ResponseEntity<List<ProviderLogs>> fetchAllProcesses(@PathVariable("date")@DateTimeFormat(pattern="yyyy-MM-dd") Date date) throws ParseException {
		ResponseEntity<List<ProviderLogs>> responseEntity = null;		
		System.out.println("Inside Controller DATE -------" + date);
		List<ProviderLogs> logs = providerLogsService.listProviderLogs(date);
		responseEntity = new ResponseEntity<List<ProviderLogs>>(logs, HttpStatus.OK);
		return responseEntity;
	}

}
