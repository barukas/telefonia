package com.telcel.sgc.services.war.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telcel.sgc.services.war.dao.jdbc.JdbcQueryDAO;

/**
 * Consulta Generica Controller
 */
@RestController
@RequestMapping("/consultaGenerica")
public class GenericQueryController {
	/**
	 * LOGGER.
	 */
	private static final Logger LOGGER = LogManager.getLogger(GenericQueryController.class);

	/**
	 * JdbcQueryDAO.
	 */
	@Autowired
	private JdbcQueryDAO genericQueryDAO;

	/**
	 * 
	 * @param nombreQuery
	 * @param mapParms
	 * @return
	 */
	@PostMapping("simple/{nombreQuery}")
	public ResponseEntity<Map<String, Object>> consultaSimple(@PathVariable String nombreQuery,
			@RequestBody Map<String, Object> mapParms) {
		LOGGER.debug("nombreQuery = [{}]", nombreQuery);
		LOGGER.debug("mapParms = [{}]", ToStringBuilder.reflectionToString(mapParms, ToStringStyle.JSON_STYLE));
		return new ResponseEntity<>(mapParms, HttpStatus.OK);
	}

	/**
	 * 
	 * @param nombreQuery
	 * @param mapParms
	 * @return
	 */
	@PostMapping("lista/{nombreQuery}")
	public ResponseEntity<List<Map<String, Object>>> consultaList(@PathVariable String nombreQuery,
			@RequestBody Map<String, Object> mapParms) {
		LOGGER.debug("nombreQuery = [{}]", nombreQuery);
		LOGGER.debug("mapParms = [{}]", ToStringBuilder.reflectionToString(mapParms, ToStringStyle.JSON_STYLE));
		return new ResponseEntity<>(this.genericQueryDAO.findList(nombreQuery, mapParms), HttpStatus.OK);
	}
}
