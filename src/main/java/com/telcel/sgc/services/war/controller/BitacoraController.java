package com.telcel.sgc.services.war.controller;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telcel.sgc.services.war.exceptions.DataNotFoundException;
import com.telcel.sgc.services.war.model.SgcOpBitacora;
import com.telcel.sgc.services.war.service.ISgcOpBitacoraService;

/**
 * Bitacora Controller.
 */
@RestController
@RequestMapping("/bitacora")
public class BitacoraController {
	/**
	 * LOGGER.
	 */
	private static final Logger LOGGER = LogManager.getLogger();
	/**
	 * "No encontrado: Bitacora"
	 */
	private static final String ETIQUETA_NOT_FOUND = "No encontrado: Bitacora";

	/**
	 * Interfaz Bitacora.
	 */
	@Autowired
	ISgcOpBitacoraService iSgcOpBitacoraService;

	/**
	 * Metodo para registrar la bitacora.
	 * 
	 * @param opBitacora
	 * @return
	 */
	@PostMapping
	public ResponseEntity<SgcOpBitacora> registrar(@RequestBody SgcOpBitacora opBitacora) {
		LOGGER.debug(BitacoraController.class.getSimpleName().concat(" - nueva, POJO :::::::: {}"),
				ToStringBuilder.reflectionToString(opBitacora, ToStringStyle.JSON_STYLE));

		return new ResponseEntity<>(this.iSgcOpBitacoraService.save(opBitacora, "userAlta"), HttpStatus.OK);
	}
	
	/**
	 * Metodo para obtener la bitacora.
	 * 
	 * @param page
	 * @param elementos
	 * @return
	 */
	@GetMapping("/page/{page}/elementos/{elementos}")
	public ResponseEntity<Page<SgcOpBitacora>> obtenerBitacora(@PathVariable Integer page, @PathVariable Integer elementos) {
		LOGGER.debug("BITACORA CONTROLLLER obtenerBitacora");
		Page<SgcOpBitacora> listSgcOpBitacora = this.iSgcOpBitacoraService.obtenerBitacora(page, elementos);
		if (listSgcOpBitacora.isEmpty()) {
			LOGGER.error("BITACORA CONTROLLLER obtenerBitacora - NOT FOUND");
			throw new DataNotFoundException(ETIQUETA_NOT_FOUND, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listSgcOpBitacora, HttpStatus.OK);
	}
}
