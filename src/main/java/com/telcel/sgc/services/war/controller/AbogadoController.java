package com.telcel.sgc.services.war.controller;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telcel.sgc.services.war.exceptions.DataNotFoundException;
import com.telcel.sgc.services.war.model.SgcCiAbogado;
import com.telcel.sgc.services.war.model.dto.SgcCiAbogadoDTO;
import com.telcel.sgc.services.war.service.ISgcCiAbogado;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

/**
 * Sgc_Ci_Abogado Controller.
 */
@RestController
@RequestMapping("/abogado")
public class AbogadoController {
	/**
	 * LOGGER.
	 */
	private static final Logger LOGGER = LogManager.getLogger();
	/**
	 * "AbogadoController - "
	 */
	private static final String ETIQUETA_ABOGADO_CON = "AbogadoController - ";
	/**
	 * "No encontrado: Abogado"
	 */
	private static final String ETIQUETA_NOT_FOUND = "No encontrado: Abogado";
	
	/**
	 * Interfaz Abogado.
	 */
	@Autowired
	ISgcCiAbogado iSgcCiAbogado;

	/**
	 * Metodo para obtener los Abogados por Id de Empresa.
	 * 
	 * @param idEmpresa
	 * @param page
	 * @param elementos
	 * @return
	 */
	@GetMapping("/{idEmpresa}")
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", 
	                      required = true, dataType = "string", paramType = "header")})
	public ResponseEntity<List<SgcCiAbogado>> obtenerAbogadoByIdEmpresa(@PathVariable Long idEmpresa) {
		LOGGER.debug(ETIQUETA_ABOGADO_CON.concat("obtenerAbogadoByIdEmpresa, idEmpresa: {}"), idEmpresa);
		List<SgcCiAbogado> listSgcCiAbogado = this.iSgcCiAbogado.obtenerAbogadoByIdEmpresa(idEmpresa);
		if (listSgcCiAbogado.isEmpty()) {
			LOGGER.error(ETIQUETA_ABOGADO_CON.concat("obtenerAbogadoByIdEmpresa - NOT FOUND"));
			throw new DataNotFoundException(ETIQUETA_NOT_FOUND, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listSgcCiAbogado, HttpStatus.OK);
	}
	
	/**
	 * Metodo para obtener los Abogados por Id de Empresa paginados.
	 * 
	 * @param idEmpresa
	 * @param page
	 * @param elementos
	 * @return
	 */
	@GetMapping("/{idEmpresa}/page/{page}/elementos/{elementos}")
	public ResponseEntity<Page<SgcCiAbogado>> obtenerAbogadoByIdEmpresa(@PathVariable Long idEmpresa, @PathVariable Integer page, @PathVariable Integer elementos) {
		LOGGER.debug(ETIQUETA_ABOGADO_CON.concat("obtenerAbogadoByIdEmpresa, idEmpresa: {}, page: {}, elementos: {}"), idEmpresa, page, elementos);
		Page<SgcCiAbogado> listSgcCiAbogado = this.iSgcCiAbogado.obtenerAbogadoByIdEmpresa(idEmpresa, page, elementos);
		if (listSgcCiAbogado.isEmpty()) {
			LOGGER.error(ETIQUETA_ABOGADO_CON.concat("obtenerAbogadoByIdEmpresa - NOT FOUND"));
			throw new DataNotFoundException(ETIQUETA_NOT_FOUND, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listSgcCiAbogado, HttpStatus.OK);
	}
	
	/**
	 * Metodo para obtener los Abogados por Id de Empresa y Id de Solicitud
	 * 
	 * @return List SgcCiAbogado
	 * 
	 * @throws DataNotFoundException
	 */
	@GetMapping("/{idEmpresa}/solicitud/{idSolicitud}")
	public ResponseEntity<List<SgcCiAbogado>> obtenerAbogadoByIdEmpresaAndIdSolicitud(@PathVariable Integer idEmpresa, @PathVariable Integer idSolicitud) {
		LOGGER.debug(ETIQUETA_ABOGADO_CON.concat("obtenerAbogadoByIdEmpresaAndIdSolicitud, idEmpresa: {}, idSolicitud: {}"), idEmpresa, idSolicitud);
		List<SgcCiAbogado> listSgcCiAbogado = this.iSgcCiAbogado.obtenerAbogadoByIdEmpresaAndIdSolicitud(idEmpresa, idSolicitud);
		if (listSgcCiAbogado.isEmpty()) {
			LOGGER.error(ETIQUETA_ABOGADO_CON.concat("obtenerAbogadoByIdEmpresaAndIdSolicitud - NOT FOUND"));
			throw new DataNotFoundException(ETIQUETA_NOT_FOUND, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listSgcCiAbogado, HttpStatus.OK);
	}
	
	/**
	 * Metodo para obtener todos los abogados activos.
	 * 
	 * @param page
	 * @param elementos
	 * @Param activo
	 * @return
	 */
	@GetMapping("/page/{page}/elementos/{elementos}/activo/{activo}")
	public ResponseEntity<Page<SgcCiAbogado>> obtenerAbogados(@PathVariable Integer page, @PathVariable Integer elementos, 
			@PathVariable Integer activo) {
		LOGGER.debug(ETIQUETA_ABOGADO_CON.concat("obtenerAbogados, page: {}, elementos: {}, activo: {}"), page, elementos, activo);
		Page<SgcCiAbogado> listSgcCiAbogado = this.iSgcCiAbogado.obtenerAbogados(page, elementos, activo);
		if (listSgcCiAbogado.isEmpty()) {
			LOGGER.error(ETIQUETA_ABOGADO_CON.concat("obtenerAbogados - NOT FOUND"));
			throw new DataNotFoundException(ETIQUETA_NOT_FOUND, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listSgcCiAbogado, HttpStatus.OK);
	}
	
	/**
	 * Metodo para generar un nuevo abogado.
	 * 
	 * @param sgcCiAbogadoDTO
	 * @return
	 */
	@PostMapping
	public ResponseEntity<SgcCiAbogadoDTO> nuevo(@RequestBody SgcCiAbogadoDTO sgcCiAbogadoDTO) {
		LOGGER.debug(ETIQUETA_ABOGADO_CON.concat(" - nuevo Abogado, POJO :::::::: {}"),
				ToStringBuilder.reflectionToString(sgcCiAbogadoDTO, ToStringStyle.JSON_STYLE));
		return new ResponseEntity<>(this.iSgcCiAbogado.save(sgcCiAbogadoDTO, "userAlta"), HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param idAbogado
	 * @param activo
	 * @return
	 */
	@DeleteMapping("{idAbogado}/activo/{activo}")
	public ResponseEntity<SgcCiAbogadoDTO> eliminar(@PathVariable Long idAbogado, @PathVariable Integer activo) {
		LOGGER.debug(ETIQUETA_ABOGADO_CON.concat(" - eliminar, id: {}"), idAbogado);
		return new ResponseEntity<>(this.iSgcCiAbogado.actualizar(idAbogado, activo, "userMod"), HttpStatus.OK);
	}
}
