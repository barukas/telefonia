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
import com.telcel.sgc.services.war.model.SgcTwrSolicitudAbogado;
import com.telcel.sgc.services.war.service.ISgcTwrSolicitudAbogado;

/**
 * Twr Abogado Solicitud Controller
 */
@RestController
@RequestMapping("/abogado")
public class TwrSolicitudAbogadoController {
	/**
	 * LOGGER.
	 */
	private static final Logger LOGGER = LogManager.getLogger();
	
	/**
	 * Twr Solicitud Abogado
	 */
	@Autowired
	ISgcTwrSolicitudAbogado iSgcTwrSolicitudAbogado;
	
	/**
	 * Metodo para encontrar los abogados asociados a una solicitud y estan activos.
	 * 
	 * @param idSolicitud
	 * @param activo
	 * @return
	 */
	@GetMapping("/solicitud/{idSolicitud}/activo/{activo}")
	public ResponseEntity<List<SgcTwrSolicitudAbogado>> obtenerAbogadoPorSolicitudActivo(@PathVariable Long idSolicitud, @PathVariable Integer activo) {
		LOGGER.debug("TwrSolicitudAbogadoController - obtenerAbogadoPorSolicitudActivo");
		List<SgcTwrSolicitudAbogado> listSgcTwrSolicitudAbogado = iSgcTwrSolicitudAbogado.obtenerAbogadoPorSolicitudActivo(idSolicitud, activo);
		if (listSgcTwrSolicitudAbogado.isEmpty()) {
			LOGGER.error("TwrSolicitudAbogadoController - obtenerAbogadoPorSolicitudActivo - NOT FOUND");
			throw new DataNotFoundException("No encontrado: Abogados por Solicitud", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listSgcTwrSolicitudAbogado, HttpStatus.OK);
	}
}
