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
import com.telcel.sgc.services.war.model.SgcCiRol;
import com.telcel.sgc.services.war.service.ICatalagoService;

/**
 * Controller para los roles
 */
@RestController
@RequestMapping("/rol")
public class RolController {
	/**
	 * LOGGER.
	 */
	private static final Logger LOGGER = LogManager.getLogger(RevisionController.class);
	/**
	 * Etiqueta "RolController - "
	 */
	private static final String ETIQUETA_ROL_CONTROLLER = "RolController - ";

	/**
	 * Rol Service.
	 */
	@Autowired
	ICatalagoService<SgcCiRol> iCatalagoService;
	
	/**
	 * 
	 * @param activo
	 * @return
	 */
	@GetMapping("/activo/{activo}")
	public ResponseEntity<List<SgcCiRol>> obtenerByActivo(@PathVariable Integer activo) {
		LOGGER.debug(ETIQUETA_ROL_CONTROLLER.concat("obtenerTipoPersonaByActivo, activo: {}"), activo);
		List<SgcCiRol> listSgcCiRol = this.iCatalagoService.findAllByActivo(activo);
		if (listSgcCiRol.isEmpty()) {
			LOGGER.error(ETIQUETA_ROL_CONTROLLER.concat("obtenerByActivo - NOT FOUND"));
			throw new DataNotFoundException("No encontrado: Rol", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listSgcCiRol, HttpStatus.OK);
	}
}
