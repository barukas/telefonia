package com.telcel.sgc.services.war.controller;

import java.util.List;

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
import com.telcel.sgc.services.war.model.SgcCiTipoPersona;
import com.telcel.sgc.services.war.service.ISgcCiTipoPersona;

/**
 * Tipo de Persona Controller
 */
@RestController
@RequestMapping("/persona")
public class TipoDePersonaController {
	/**
	 * LOGGER.
	 */
	private static final Logger LOGGER = LogManager.getLogger();
	/**
	 * Interfaz Tipo de Actor.
	 */
	@Autowired
	ISgcCiTipoPersona iSgcCiTipoPersona;
	
	@GetMapping("/actor/{idActor}/solicitud/{idTipoSolicitud}/empresa/{idEmpresa}")
	public ResponseEntity<List<SgcCiTipoPersona>> obtenerTipoDePersonaPorActoresAndSolicitudAndEmpresa(@PathVariable Long idActor, @PathVariable Long idTipoSolicitud, @PathVariable Long idEmpresa) {
		LOGGER.debug("TipoDePersonaController - obtenerTipoDePersonaPorActoresAndSolicitudAndEmpresa");
		List<SgcCiTipoPersona> listSgcCiTipoPersona = this.iSgcCiTipoPersona.obtenerTipoDePersonaPorActoresAndSolicitudAndEmpresa(idActor, idTipoSolicitud, idEmpresa);
		if (listSgcCiTipoPersona.isEmpty()) {
			LOGGER.error("TipoDePersonaController - obtenerTipoDePersonaPorActoresAndSolicitudAndEmpresa - NOT FOUND");
			throw new DataNotFoundException("No encontrado: Tipo de Persona por Actores y Solicitud y Empresa", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listSgcCiTipoPersona, HttpStatus.OK);
	}
}
