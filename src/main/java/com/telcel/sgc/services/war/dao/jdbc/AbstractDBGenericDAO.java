package com.telcel.sgc.services.war.dao.jdbc;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.telcel.sgc.services.war.exceptions.DataNotFoundException;
import com.telcel.sgc.services.war.model.SgcCiQuery;
import com.telcel.sgc.services.war.repository.ISgcCiQueryRepository;

/**
 * AbstractDBGenericDAO.
 */
public abstract class AbstractDBGenericDAO extends JdbcDaoSupport {
	/**
	 * LOGGER.
	 */
	private static final Logger LOGGER = LogManager.getLogger(AbstractDBGenericDAO.class);
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
	private DataSource dataSource;

	/**
	 * SgcCiQuery Repository.
	 */
	@Autowired
	private ISgcCiQueryRepository sgcCiQueryRepository;

	/**
	 * 
	 */
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.namedParmJdbcTmplt = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * @param queryName
	 * @return
	 */
	public String getQueryByName(String queryName) {
		Optional<SgcCiQuery> query = this.sgcCiQueryRepository.findByNombre(queryName);
		if (query.isPresent()) {
			LOGGER.debug(ToStringBuilder.reflectionToString(query.get(), ToStringStyle.JSON_STYLE));
			return query.get().getSentencia();
		} else {
			throw new DataNotFoundException(String.format(MSGERR01, queryName), HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * @return the namedParmJdbcTmplt
	 */
	public final NamedParameterJdbcTemplate getNamedParmJdbcTmplt() {
		return namedParmJdbcTmplt;
	}

	/**
	 * @param namedParmJdbcTmplt the namedParmJdbcTmplt to set
	 */
	public final void setNamedParmJdbcTmplt(NamedParameterJdbcTemplate namedParmJdbcTmplt) {
		this.namedParmJdbcTmplt = namedParmJdbcTmplt;
	}
}
