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
import com.telcel.sgc.services.war.model.SgcCiAbogadoCorreo;
import com.telcel.sgc.services.war.service.ISgcCiAbogadoCorreo;

/**
 * Abogado Correo Controller.
 */
@RestController
@RequestMapping("/abogado/correo")
public class AbogadoCorreoController {
	/**
	 * LOGGER.
	 */
	private static final Logger LOGGER = LogManager.getLogger();
	/**
	 * "AbogadoCorreoController - "
	 */
	private static final String ETIQUETA_ABOGADO_CORREO_CON = "AbogadoCorreoController - ";

	/**
	 * Interfaz Abogado Correo.
	 */
	@Autowired
	ISgcCiAbogadoCorreo iSgcCiAbogadoCorreo;
	
	/**
	 * Metodo para obtener los correos por empresa.
	 * 
	 * @param idEmpresa
	 * @return
	 * @throws DataNotFoundException
	 */
	@GetMapping("/empresa/{idEmpresa}")
	public ResponseEntity<List<SgcCiAbogadoCorreo>> obtenerCorreoPorPersona(@PathVariable Long idEmpresa) {
		LOGGER.debug(ETIQUETA_ABOGADO_CORREO_CON.concat("obtenerCorreoPorPersona, idEmpresa: {}"), idEmpresa);
		List<SgcCiAbogadoCorreo> listSgcCiAbogadoCorreo = this.iSgcCiAbogadoCorreo.obtenerCorreoByIdEmpresa(idEmpresa);
		if (listSgcCiAbogadoCorreo.isEmpty()) {
			LOGGER.error(ETIQUETA_ABOGADO_CORREO_CON.concat("obtenerCorreoPorPersona - NOT FOUND"));
			throw new DataNotFoundException("No encontrado: Correo por empresa", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listSgcCiAbogadoCorreo, HttpStatus.OK);
	}	
}
