package com.telcel.sgc.services.war.dao.jdbc;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Abstract DBGeneric BPM DAO.
 */
public abstract class AbstractDBGenericBPMDAO extends JdbcDaoSupport {
	
	/**
	 * "No existe un query configurado con el nombre =[%1$s].".
	 */
	private static final String MSGERR01 = "No existe un query configurado con el nombre =[%1$s].";

	/**
	 * NamedParameterJdbcTemplate.
	 */
	private NamedParameterJdbcTemplate namedParmJdbcTmplt;

	/**
	 * DataSource.
	 */
	@Autowired
	private DataSource bpmdatasource;

	@PostConstruct
	private void initialize() {
		setDataSource(bpmdatasource);
		this.namedParmJdbcTmplt = new NamedParameterJdbcTemplate(bpmdatasource);
	}
	
	/**
	 * 
	 * 
	 * @return the namedParmJdbcTmplt
	 */
	public final NamedParameterJdbcTemplate getNamedParmJdbcTmplt() {
		return namedParmJdbcTmplt;
	}

	/**
	 * 
	 * 
	 * @param namedParmJdbcTmplt the namedParmJdbcTmplt to set
	 */
	public final void setNamedParmJdbcTmplt(NamedParameterJdbcTemplate namedParmJdbcTmplt) {
		this.namedParmJdbcTmplt = namedParmJdbcTmplt;
	}
}
