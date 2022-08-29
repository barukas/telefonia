package com.telcel.sgc.services.war.controller;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telcel.sgc.services.war.model.dto.SgcTrSolicitudDTO;
import com.telcel.sgc.services.war.service.ISgcTrSolicitudService;

/**
 * Tr Solicitud Controller
 */
@RestController
@RequestMapping("/tr/solicitud")
public class TrSolicitudController {
	/**
	 * LOGGER.
	 */
	private static final Logger LOGGER = LogManager.getLogger(SolicitudController.class);
	/**
	 * Etiqueta Tipo Solicitud Controller.
	 */ 
	private static final String ETIQUETA_TR_SOLICITUD_CONTROLLER = "TrSolicitudController - ";

	/**
	 * ISgcTrSolicitud Service
	 */
	@Autowired
	ISgcTrSolicitudService iSgcTrSolicitudService;
	/**
	 * Metodo para guardar una nueva tr solicitud (relacion entre: tipo solicitud, id_empresa, 
	 * id, actor_doc y id_actor_persona)
	 * 
	 * @param sgcTrSolicitudDTO
	 * @return
	 */
	@PostMapping
	public ResponseEntity<SgcTrSolicitudDTO> nueva(@RequestBody SgcTrSolicitudDTO sgcTrSolicitudDTO) {
		LOGGER.debug(ETIQUETA_TR_SOLICITUD_CONTROLLER.concat(" - nueva, POJO :::::::: {}"),
				ToStringBuilder.reflectionToString(sgcTrSolicitudDTO, ToStringStyle.JSON_STYLE));
		
		return new ResponseEntity<>(this.iSgcTrSolicitudService.save(sgcTrSolicitudDTO, "userAlta"), HttpStatus.OK);
	}
}
