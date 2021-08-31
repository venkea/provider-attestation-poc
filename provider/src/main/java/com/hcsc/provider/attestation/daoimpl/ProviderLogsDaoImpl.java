package com.hcsc.provider.attestation.daoimpl;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hcsc.provider.attestation.dao.ProviderLogsDao;
import com.hcsc.provider.attestation.model.ProviderLogs;

@Repository
public class ProviderLogsDaoImpl implements ProviderLogsDao{

	@Autowired
	private SessionFactory sessionFactory;

	private Session session;
	
	@Override
	public  Serializable createOrUpdateProviderLogs(ProviderLogs providerLogs) {
	
		Session sessionOne = sessionFactory.openSession();  
		sessionOne.beginTransaction();
		session = sessionFactory.getCurrentSession();
		return  session.save(providerLogs);
		
	}

}
