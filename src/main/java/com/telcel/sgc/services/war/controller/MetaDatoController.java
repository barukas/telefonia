package com.telcel.sgc.services.war.controller;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.telcel.sgc.services.war.exceptions.DataNotFoundException;
import com.telcel.sgc.services.war.exceptions.ServiceException;
import com.telcel.sgc.services.war.model.SgcCiMetadato;
import com.telcel.sgc.services.war.model.dto.SolActorResponseDTO;
import com.telcel.sgc.services.war.service.ISgcCiMetadato;
import com.telcel.sgc.services.war.utils.Constantes;

/**
 * Sgc_Ci_Metadato Controller
 */
@RestController
@RequestMapping("/metadato")
public class MetaDatoController {
	/**
	 * LOGGER.
	 */
	private static final Logger LOGGER = LogManager.getLogger();
	/**
	 * "MetaDatosController - "
	 */
	private static final String ETIQUETA_METADATO_CONTROLLER = "MetaDatosController - ";

	/**
	 * Interfaz Metadato.
	 */
	@Autowired
	ISgcCiMetadato iSgcCiMetadato;
	
	/**
	 * Metodo para obtener los Metadatos por id de actor persona.
	 * 
	 * @param idActorPersona
	 * @return
	 */
	@GetMapping("/actorPersona/{idActorPersona}")
	public ResponseEntity<SgcCiMetadato> obtenerMetadatoByIdActor(@PathVariable Long idActorPersona) {
		LOGGER.debug(ETIQUETA_METADATO_CONTROLLER.concat("obtenerMetadatoByIdActor, idActorPersona: {}"), idActorPersona);
		SgcCiMetadato sgcCiMetadato = this.iSgcCiMetadato.obtenerMetadatoByIdActor(idActorPersona/*, idTipoPersona*/);
		if (sgcCiMetadato == null) {
			LOGGER.error(ETIQUETA_METADATO_CONTROLLER.concat("obtenerMetadatoByIdActor - NOT FOUND"));
			throw new DataNotFoundException("No encontrado: Metadatos", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(sgcCiMetadato, HttpStatus.OK);
	}
	
	/**
	 * Guardar metadatos por idSolicitud and idActorPersona.
	 * 
	 * @param body
	 * @return
	 */
	@PutMapping("/actualizar")
	public ResponseEntity<SolActorResponseDTO> guardarMetadatoByIdActor(@RequestBody String body) {
		LOGGER.debug(ETIQUETA_METADATO_CONTROLLER.concat("guardarMetadatoByIdActor"));
		
		JsonObject metadatosJSON = (new JsonParser()).parse(body).getAsJsonObject();
		JsonArray metadatos = metadatosJSON.get("metadatos").getAsJsonArray();
		
		JsonElement idSolActor = metadatosJSON.get("idSolActor");
		JsonElement idSolicitud = metadatosJSON.get("idSolicitud");
		JsonElement idActorPersona = metadatosJSON.get("idActorPersona");
		
		if (idSolicitud == null || idActorPersona ==null || metadatos == null) {
			throw new ServiceException(String.format(Constantes.ERROR_JSON_INVALIDO));
		}
		
		LOGGER.debug(ETIQUETA_METADATO_CONTROLLER.concat("guardarMetadatoByIdActor - idSolActor: {}, idSolicitud: {}, idActorPersona: {}"), idSolActor, idSolicitud, idActorPersona);
		SolActorResponseDTO solActorResponseDTO = this.iSgcCiMetadato.guardarMetadatoByIdActor(idSolActor.getAsLong(), idSolicitud.getAsLong(), idActorPersona.getAsLong(), metadatos.toString());
		if (solActorResponseDTO == null) {
			LOGGER.error(ETIQUETA_METADATO_CONTROLLER.concat("guardarMetadatoByIdActor - NOT FOUND"));
			throw new DataNotFoundException("Error al guardar metadatos.", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(solActorResponseDTO, HttpStatus.OK);
	}
	
	/**
	 * Metodo para aprobar la informacion de la solicitud. 
	 * 
	 * @param idSolicitud
	 * @return
	 */
	@GetMapping("aprobacion/solicitud/{idSolicitud}")
	public ResponseEntity<Map<String, String>> aprobacionSolicitudPorMetadatos(@PathVariable Long idSolicitud) {
		LOGGER.debug(MetaDatoController.ETIQUETA_METADATO_CONTROLLER.concat("aprobacionSolicitudPorMetadatos"));
		return new ResponseEntity<>(this.iSgcCiMetadato.aprobacionSolicitud(idSolicitud), HttpStatus.OK);
	}
}
