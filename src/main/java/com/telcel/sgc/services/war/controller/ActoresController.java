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
import com.telcel.sgc.services.war.model.dto.ISgcCiTipoActorDTO;
import com.telcel.sgc.services.war.service.ISgcCiTipoActor;

/**
 * Actores Controller
 */
@RestController
@RequestMapping("/actores")
public class ActoresController {
	/**
	 * LOGGER.
	 */
	private static final Logger LOGGER = LogManager.getLogger();
	/**
	 * Interfaz Tipo de Actor.
	 */
	@Autowired
	ISgcCiTipoActor iSgcCiTipoActor;
	
	/**
	 * Metodo para obtener los actores por tipo de solicitud y por empresa.
	 * 
	 * @param idTipoSolicitud
	 * @param idEmpresa
	 * @return
	 * @throws DataNotFoundException
	 */
	@GetMapping("/solicitud/{idTipoSolicitud}/empresa/{idEmpresa}")
	public ResponseEntity<List<ISgcCiTipoActorDTO>> obtenerActoresPorSolicitudAndEmpresa(@PathVariable Long idTipoSolicitud, @PathVariable Long idEmpresa) {
		LOGGER.debug("ActoresController - obtenerActoresPorSolicitudAndEmpresa");
		List<ISgcCiTipoActorDTO> listSgcCiTipoActor = this.iSgcCiTipoActor.obtenerActoresPorSolicitudAndEmpresa(idTipoSolicitud, idEmpresa);
		if (listSgcCiTipoActor.isEmpty()) {
			LOGGER.error("ActoresController - obtenerActoresPorSolicitudAndEmpresa - NOT FOUND");
			throw new DataNotFoundException("No encontrado: Actores por Solicitud y Empresa", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listSgcCiTipoActor, HttpStatus.OK);
	}
}
