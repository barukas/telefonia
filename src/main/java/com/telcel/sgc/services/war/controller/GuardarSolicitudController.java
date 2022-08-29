package com.telcel.sgc.services.war.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telcel.sgc.services.war.exceptions.DataNotFoundException;
import com.telcel.sgc.services.war.service.ISgcOpFolio;

/**
 * Guardar Solicitud Controller
 */
@RestController
@RequestMapping("/guardar")
public class GuardarSolicitudController {
	/**
	 * LOGGER.
	 */
	private static final Logger LOGGER = LogManager.getLogger();
	/**
	 * Sgc Op Folio.
	 */
	@Autowired
	ISgcOpFolio iSgcOpFolio;
	
	/**
	 * Metodo para guardar solicitud.
	 * 
	 * @return String idEmpresa
	 * 
	 * @throws DataNotFoundException
	 */
	@GetMapping("/{idEmpresa}")
	public ResponseEntity<String> guardarSolicitud(@PathVariable Long idEmpresa) {
		LOGGER.debug("GuardarSolicitudController - guardarSolicitud:" + idEmpresa);
		String folioPorEmpresa = this.iSgcOpFolio.generaFolioSolicitud(idEmpresa);
		if (folioPorEmpresa.isEmpty() || folioPorEmpresa.equals("")) {
			LOGGER.error("GuardarSolicitudController - guardarSolicitud - Not Generated");
			throw new DataNotFoundException("No generado: Folio por Empresa", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(folioPorEmpresa, HttpStatus.OK);
	}
}
