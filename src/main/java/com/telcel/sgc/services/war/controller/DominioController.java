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
import com.telcel.sgc.services.war.model.SgcCiDominio;
import com.telcel.sgc.services.war.service.ISgcCiDominioService;

/**
 * SgcCiDominio Controller.
 */
@RestController
@RequestMapping("/dominio")
public class DominioController {
	/**
	 * LOGGER.
	 */
	private static final Logger LOGGER = LogManager.getLogger();
	
	/**
	 * Interfaz Dominio.
	 */
	@Autowired
	ISgcCiDominioService iSgcCiDominioService;
	
	/**
	 * Metodo para obtener los Dominios por Id de Empresa.
	 * 
	 * @return List SgcCiDominio
	 * 
	 * @throws DataNotFoundException
	 */
	@GetMapping("/{idEmpresa}")
	public ResponseEntity<List<SgcCiDominio>> obtenerDominioByIdEmpresa(@PathVariable Integer idEmpresa) {
		LOGGER.debug("DominioController - obtenerDominioByIdEmpresa");
		List<SgcCiDominio> listSgcCiDominio = this.iSgcCiDominioService.obtenerDominioByIdEmpresa(idEmpresa);
		if (listSgcCiDominio.isEmpty()) {
			LOGGER.error("DominioController - obtenerDominioByIdEmpresa - NOT FOUND");
			throw new DataNotFoundException("No encontrado: Dominio por Empresa", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listSgcCiDominio, HttpStatus.OK);
	}
}
