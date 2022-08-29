package com.telcel.sgc.services.war.controller;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.telcel.sgc.services.war.model.SgcCiTipoSolicitud;
import com.telcel.sgc.services.war.model.dto.SgcCiTipoSolicitudDTO;
import com.telcel.sgc.services.war.service.ISgcCiTipoSolicitud;

/**
 * Tipo de Solicitud Controller
 */
@RestController
@RequestMapping("/tipo/solicitud")
public class TipoSolicitudController {
	/**
	 * LOGGER.
	 */
	private static final Logger LOGGER = LogManager.getLogger();
	/**
	 * "TipoSolicitudController"
	 */
	private static final String ETIQUETA_TIPO_SOL_CONTROLLER = "TipoSolicitudController";
	/**
	 * Tipo de Solicitud.
	 */
	@Autowired
	ISgcCiTipoSolicitud iSgcCiTipoSolicitud;
	
	/**
	 * Metodo para obtener Tipo de Solicitud por  Empresa.
	 * 
	 * @param activo
	 * 		1=activo, 0=inactivo
	 * @param idEmpresa
	 * 		Id Empresa o Area Solicitante
	 * @return List SgcCiTipoSolicitud
	 * 
	 * @throws DataNotFoundException
	 */
	@GetMapping("/empresa/{idEmpresa}/{activo}")
	public ResponseEntity<List<SgcCiTipoSolicitud>> obtenerTipoSolicitudPorArea(@PathVariable Integer activo, @PathVariable Long idEmpresa) {
		LOGGER.debug(ETIQUETA_TIPO_SOL_CONTROLLER.concat(" - obtenerTipoSolicitudPorArea, idEmpresa: {}, activo: {}"), idEmpresa, activo);
		List<SgcCiTipoSolicitud> listSgcCiTipoSolicitudPorArea = this.iSgcCiTipoSolicitud.tipoSolicitudPorEmpresa(idEmpresa, activo);
		if (listSgcCiTipoSolicitudPorArea.isEmpty()) {
			LOGGER.error(ETIQUETA_TIPO_SOL_CONTROLLER.concat(" - obtenerTipoSolicitudPorArea - NOT FOUND"));
			throw new DataNotFoundException("No encontrado: Tipo de Solicitud por Area", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listSgcCiTipoSolicitudPorArea, HttpStatus.OK);
	}
	
	/**
	 * Metodo para guardar un nuevo tipo de solicitud
	 * 
	 * @param sgcCiEmpresaDTO
	 * @return
	 */
	@PostMapping
	public ResponseEntity<SgcCiTipoSolicitudDTO> nueva(@RequestBody SgcCiTipoSolicitudDTO sgcCiTipoSolicitudDTO) {
		LOGGER.debug(ETIQUETA_TIPO_SOL_CONTROLLER.concat(" - nueva, POJO :::::::: {}"),
				ToStringBuilder.reflectionToString(sgcCiTipoSolicitudDTO, ToStringStyle.JSON_STYLE));
		
		return new ResponseEntity<>(this.iSgcCiTipoSolicitud.save(sgcCiTipoSolicitudDTO, "userAlta"), HttpStatus.OK);
	}
	
	/**
	 * Metodo para eliminar un tipo de solicitud (baja logica)
	 * 
	 * @param idEmpresa
	 * @return
	 */
	@DeleteMapping("/{idTipoSolicitud}")
	public ResponseEntity<SgcCiTipoSolicitudDTO> eliminar(@PathVariable Long idTipoSolicitud) {
		LOGGER.debug(ETIQUETA_TIPO_SOL_CONTROLLER.concat(" - eliminar, id: {}"), idTipoSolicitud);
		
		return new ResponseEntity<>(this.iSgcCiTipoSolicitud.actualizar(idTipoSolicitud, 0, "userMod"), HttpStatus.OK);
	}
}
