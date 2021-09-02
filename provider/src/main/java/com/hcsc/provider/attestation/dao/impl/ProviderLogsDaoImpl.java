package com.hcsc.provider.attestation.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hcsc.provider.attestation.dao.IProviderLogsDao;
import com.hcsc.provider.attestation.model.ProviderLogs;

@Repository
public class ProviderLogsDaoImpl implements IProviderLogsDao{

	@Autowired
	private SessionFactory sessionFactory;

	private Session session;

	@SuppressWarnings("unchecked")
	@Override
	public List<ProviderLogs> getProviderLogs(Date date) {
		session = sessionFactory.openSession();
		return session.createQuery("from ProviderLogs  where date=:date")
				.setParameter("date", date)
				.list();
	}

}
