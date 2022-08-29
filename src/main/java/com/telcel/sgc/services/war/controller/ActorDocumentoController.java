package com.telcel.sgc.services.war.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telcel.sgc.services.war.exceptions.DataNotFoundException;
import com.telcel.sgc.services.war.model.SgcTrActorDocumento;
import com.telcel.sgc.services.war.service.ISgcTrActorDocumento;

/**
 * Sgc_Tr_Actor_Documento Controller.
 */
@RestController
@RequestMapping("/actor/documento")
public class ActorDocumentoController {
	/**
	 * LOGGER.
	 */
	private static final Logger LOGGER = LogManager.getLogger();
	/**
	 * "ActorDocumentoController - "
	 */
	private static final String ETIQUETA_ACTOR_DOC_CON = "ActorDocumentoController - ";

	/**
	 * Interfaz Actor Documento.
	 */
	@Autowired
	ISgcTrActorDocumento iSgcTrActorDocumento;
	
	/**
	 * 
	 * @param idTipoPersona
	 * @param idActor
	 * @param idEmpresa
	 * @param idTipoSolicitud
	 * @return
	 */
	@GetMapping("/persona/{idTipoPersona}/actor/{idActor}/empresa/{idEmpresa}/solicitud/{idTipoSolicitud}")
	public ResponseEntity<List<SgcTrActorDocumento>> obtenerActorDocumento(
			@PathVariable Long idTipoPersona, @PathVariable Long idActor, @PathVariable Long idEmpresa, @PathVariable Long idTipoSolicitud) {
		LOGGER.debug(ETIQUETA_ACTOR_DOC_CON.concat("obtenerActorDocumento, idTipoPersona: {}, idActor: {}, idEmpresa: {}, idTipoSolicitud: {}"), idTipoPersona,
				idActor, idEmpresa, idTipoSolicitud);
		List<SgcTrActorDocumento> listSgcTrActorDocumento = this.iSgcTrActorDocumento.obtenerActorDocumento(idTipoPersona, idActor, idTipoSolicitud, idEmpresa);
		if (listSgcTrActorDocumento.isEmpty()) {
			LOGGER.error(ETIQUETA_ACTOR_DOC_CON.concat("obtenerActorDocumento - NOT FOUND"));
			throw new DataNotFoundException("No encontrado: Actor Documento", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listSgcTrActorDocumento, HttpStatus.OK);
	}	
	
	/**
	 * 
	 * @param idTipoPersona
	 * @param idActor
	 * @param idEmpresa
	 * @param idTipoSolicitud
	 * @param page
	 * @param elementos
	 * @return
	 */
	@GetMapping("/persona/{idTipoPersona}/actor/{idActor}/empresa/{idEmpresa}/solicitud/{idTipoSolicitud}/page/{page}/elementos/{elementos}")
	public ResponseEntity<Page<SgcTrActorDocumento>> obtenerActorDocumento(
			@PathVariable Long idTipoPersona, @PathVariable Long idActor, @PathVariable Long idEmpresa, @PathVariable Long idTipoSolicitud,
			@PathVariable Integer page, @PathVariable Integer elementos) {
		LOGGER.debug(ETIQUETA_ACTOR_DOC_CON.concat("obtenerActorDocumento, idTipoPersona: {}, idActor: {}, idEmpresa: {}, idTipoSolicitud: {}"), idTipoPersona,
				idActor, idEmpresa, idTipoSolicitud);
		Page<SgcTrActorDocumento> listSgcTrActorDocumento = this.iSgcTrActorDocumento.obtenerActorDocumento(idTipoPersona, idActor, idTipoSolicitud, idEmpresa, page, elementos);
		if (listSgcTrActorDocumento.isEmpty()) {
			LOGGER.error(ETIQUETA_ACTOR_DOC_CON.concat("obtenerActorDocumento - NOT FOUND"));
			throw new DataNotFoundException("No encontrado: Actor Documento", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listSgcTrActorDocumento, HttpStatus.OK);
	}
}
