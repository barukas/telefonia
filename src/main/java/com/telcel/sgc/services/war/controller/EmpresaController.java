package com.telcel.sgc.services.war.controller;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telcel.sgc.services.war.model.dto.SgcCiEmpresaDTO;
import com.telcel.sgc.services.war.model.dto.SgcTrEmpresaAbogadoDTO;
import com.telcel.sgc.services.war.service.ISgcCiEmpresa;
import com.telcel.sgc.services.war.service.ISgcTrEmpresaAbogadoService;

/**
 * Empresa Controller
 */
@RestController
@RequestMapping("/empresa")
public class EmpresaController {
	/**
	 * LOGGER.
	 */
	private static final Logger LOGGER = LogManager.getLogger();
	/**
	 * "EmpresaController"
	 */
	private static final String LABEL_EMPRESA_CONTROLLER = "EmpresaController";
	/**
	 * Interface SgcCiempresa
	 */
	@Autowired
	ISgcCiEmpresa iSgcCiEmpresa;
	/**
	 * Interface SgcTrEmpresaAbogado
	 */
	@Autowired
	ISgcTrEmpresaAbogadoService iSgcTrEmpresaAbogadoService;
	
	/**
	 * Metodo para guardar una nueva empresa
	 * 
	 * @param sgcCiEmpresaDTO
	 * @return
	 */
	@PostMapping
	public ResponseEntity<SgcCiEmpresaDTO> nueva(@RequestBody SgcCiEmpresaDTO sgcCiEmpresa) {
		LOGGER.debug(LABEL_EMPRESA_CONTROLLER.concat(" - nueva, POJO :::::::: {}"),
				ToStringBuilder.reflectionToString(sgcCiEmpresa, ToStringStyle.JSON_STYLE));
		
		return new ResponseEntity<>(this.iSgcCiEmpresa.save(sgcCiEmpresa, "userAlta"), HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param idEmpresa
	 * @return
	 */
	@DeleteMapping("/{idEmpresa}")
	public ResponseEntity<SgcCiEmpresaDTO> eliminar(@PathVariable Long idEmpresa) {
		LOGGER.debug(LABEL_EMPRESA_CONTROLLER.concat(" - eliminar, id: {}"), idEmpresa);
		
		return new ResponseEntity<>(this.iSgcCiEmpresa.actualizar(idEmpresa, 0, "userMod"), HttpStatus.OK);
	}
	
	/**
	 * Metodo para guardar una nueva relacion de empresa y abogado
	 * 
	 * @param sgcCiEmpresaDTO
	 * @return
	 */
	@PostMapping("/abogado")
	public ResponseEntity<SgcTrEmpresaAbogadoDTO> nuevaEmpresaAbogado(@RequestBody SgcTrEmpresaAbogadoDTO sgcTrEmpresaAbogadoDTO) {
		LOGGER.debug(LABEL_EMPRESA_CONTROLLER.concat(" - nuevaEmpresaAbogado, POJO :::::::: {}"),
				ToStringBuilder.reflectionToString(sgcTrEmpresaAbogadoDTO, ToStringStyle.JSON_STYLE));
		
		return new ResponseEntity<>(this.iSgcTrEmpresaAbogadoService.save(sgcTrEmpresaAbogadoDTO, "userAlta"), HttpStatus.OK);
	}
}
