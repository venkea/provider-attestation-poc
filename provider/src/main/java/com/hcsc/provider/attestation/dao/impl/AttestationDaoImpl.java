package com.hcsc.provider.attestation.dao.impl;

import java.math.BigInteger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hcsc.provider.attestation.dao.AttestationDao;

@Repository
public class AttestationDaoImpl implements AttestationDao{

	@Autowired
	private SessionFactory sessionFactory;

	private Session session;


	@Override
	public BigInteger getJobExecutionId() {		

		session = sessionFactory.getCurrentSession();
		return (BigInteger) session.createNativeQuery("SELECT JOB_EXECUTION_ID FROM batch_job_execution ORDER BY JOB_EXECUTION_ID DESC LIMIT 1")
				.uniqueResult();
	}

}
