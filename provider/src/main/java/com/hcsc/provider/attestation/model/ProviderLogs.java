package com.hcsc.provider.attestation.model;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name = "provider_logs")
public class ProviderLogs {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	@Column(name = "log_id")
	private Integer id;

	@Column(name = "job_execution_id")
	private BigInteger jobExecutionId;

	@Column(name = "provider_id")
	private Integer providerId;

	@Column(name = "log_description")
	private String description;

	@Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
	@Column(name = "date")
	private Date date;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigInteger getJobExecutionId() {
		return jobExecutionId;
	}

	public void setJobExecutionId(BigInteger jobExecutionId) {
		this.jobExecutionId = jobExecutionId;
	}

	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "ProviderLogs [id=" + id + ", jobExecutionId=" + jobExecutionId + ", providerId=" + providerId
				+ ", description=" + description + ", date=" + date + "]";
	}
	
	


	

}
