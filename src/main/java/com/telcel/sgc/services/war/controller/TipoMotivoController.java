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
import com.telcel.sgc.services.war.model.SgcCiTipoMotivo;
import com.telcel.sgc.services.war.service.ISgcCiTipoMotivo;

/**
 * SgcCiTipoMotivo Controller.
 */
@RestController
@RequestMapping("/tipo/motivo")
public class TipoMotivoController {
	/**
	 * LOGGER.
	 */
	private static final Logger LOGGER = LogManager.getLogger();
	
	/**
	 * Interfaz Tipo de Motivo.
	 */
	@Autowired
	ISgcCiTipoMotivo iSgcCiTipoMotivo;
	
	/**
	 * Metodo para obtener los tipos de motivo, por id y activo
	 * 
	 * @param idMotivo
	 * @param activo
	 * @return
	 */
	@GetMapping("/{idMotivo}/activo/{activo}")
	public ResponseEntity<List<SgcCiTipoMotivo>> obtenerTipoMotivo(@PathVariable Long idMotivo, @PathVariable Integer activo) {
		LOGGER.debug("TipoMotivoController - obtenerTipoMotivo");
		List<SgcCiTipoMotivo> listSgcCiTipoMotivo = iSgcCiTipoMotivo.obtenerTipoMotivo(idMotivo, activo);
		if (listSgcCiTipoMotivo.isEmpty()) {
			LOGGER.error("TipoMotivoController - obtenerTipoMotivo - NOT FOUND");
			throw new DataNotFoundException("No encontrado: Tipo de Motivo", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listSgcCiTipoMotivo, HttpStatus.OK);
	}
}
