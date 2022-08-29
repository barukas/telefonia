package com.telcel.sgc.services.war.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telcel.sgc.services.war.exceptions.DataNotFoundException;
import com.telcel.sgc.services.war.model.dto.CompartirDocDTO;
import com.telcel.sgc.services.war.model.dto.SolFinalizarResponseDto;
import com.telcel.sgc.services.war.model.dto.SolGuardaDocRevDTO;
import com.telcel.sgc.services.war.model.dto.SolicitudRechCancelDTO;
import com.telcel.sgc.services.war.model.dto.SolicitudRechCancelResponseDTO;
import com.telcel.sgc.services.war.service.IRevisionService;

/**
 * Controller de la revision de documentacion e informacion de la Solicitud.
 */
@RestController
@RequestMapping("/revision")
public class RevisionController extends AbstractDownloadController {
	/**
	 * LOGGER.
	 */
	private static final Logger LOGGER = LogManager.getLogger(RevisionController.class);
	/**
	 * Etiqueta Tipo Solicitud Controller.
	 */
	private static final String ETIQUETA_SOLICITUD_CONTROLLER = "RevisionController - ";

	/**
	 * Tipo de Solicitud.
	 */
	@Autowired
	IRevisionService revisionService;

	/**
	 * Metodo que guarda la calificacion del documento.
	 * 
	 * @param body DTO que contiene el id del docuemnto y su bandera de aprobado.
	 * @return DTO con el id del registro de la calificacion.
	 * @throws DataNotFoundException En caso de no encotrar el idsolDoc en la BD.
	 */
	@PostMapping("/guardaSolDocRev")
	public ResponseEntity<SolGuardaDocRevDTO> guardaSolDocRev(@RequestBody SolGuardaDocRevDTO body) {
		LOGGER.debug(RevisionController.ETIQUETA_SOLICITUD_CONTROLLER.concat("guardarSolicitudSimple"));
		final SolGuardaDocRevDTO responseDTO = this.revisionService.guardarRevisionDoc(body, "AreaSolicitante");
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * Metodo para descargar el zip con todos los docuemtnos activos adjuntos de
	 * todos los actores activos de la solicitud.
	 * 
	 * @param idSolicitud ID de la Solicitud a descargar la documentacion.
	 * @param activo      Estatus activo de la solicitud (1: activo, 0: No activo).
	 * @param activoDoc   Estatus activo de la documentacion (1: activo, 0: No
	 *                    activo).
	 * @param request     Peticion HTTP de la descarga.
	 * @return Descarga del archivo zip.
	 */
	@GetMapping("/descargaZip/{idSolicitud}/{activo}/{activoDoc}")
	public ResponseEntity<Resource> descargaZip(@PathVariable Long idSolicitud, @PathVariable Integer activo,
			@PathVariable Integer activoDoc, HttpServletRequest request) {
		Resource resource = this.revisionService.obtenerZipPorIdSolicitud(idSolicitud, activo, activoDoc);
		return this.downloadFile(resource, request, LOGGER);
	}

	/**
	 * Metodo para rechazar o cancelar solicitud.
	 * 
	 * @param solicitudRechCancelDTO
	 * @return
	 */
	@PostMapping("rechazar/cancelar")
	public ResponseEntity<SolicitudRechCancelResponseDTO> rechazarCancelarSolicitud(
			@RequestBody SolicitudRechCancelDTO solicitudRechCancelDTO) {
		LOGGER.debug("Metodo rechazarCancelarSolicitud, POJO :::::::: {}", solicitudRechCancelDTO.toString());
		SolicitudRechCancelResponseDTO solicitudRechCancelResponseDTO = this.revisionService
				.rechazarCancelarSolicitud(solicitudRechCancelDTO, "SYSTEM");
		return new ResponseEntity<>(solicitudRechCancelResponseDTO, HttpStatus.OK);

	}

	/**
	 * Metodo para descargar el zip con todos los documentos activos adjuntos de
	 * todos los actores activos de la solicitud.
	 * 
	 * @param idSolicitudCompartir ID de la Solicitud a descargar la documentacion.
	 * @param token                token de descarga.
	 * @param request              Peticion HTTP de la descarga.
	 * @return Descarga del archivo zip.
	 */
	@GetMapping("/descargaZipCompartir/{idSolicitudCompartir}/{token}")
	public ResponseEntity<Resource> descargaZipCompartir(@PathVariable Long idSolicitudCompartir,
			@PathVariable String token, HttpServletRequest request) {
		Resource resource = this.revisionService.obtenerZipPorToken(idSolicitudCompartir, token);
		return this.downloadFile(resource, request, LOGGER);
	}

	/**
	 * Metodo para compartir documentacion de solicitud por envío de correo con liga
	 * de descarga.
	 * 
	 * @param solicitudRechCancelDTO DTO con la informacion de la solicitud a
	 *                               compartir doc.
	 * @return DTO con la informacion de la peticion.
	 */
	@PostMapping("/compartirDoc")
	public ResponseEntity<CompartirDocDTO> compartirDoc(@RequestBody CompartirDocDTO solicitudRechCancelDTO) {
		LOGGER.debug("Pojo ::::::::  " + solicitudRechCancelDTO);
		return new ResponseEntity<>(this.revisionService.procesaCompartirDoc(solicitudRechCancelDTO, "TEST"),
				HttpStatus.OK);
	}

	/**
	 * Metodo para validar si la solicitud puede continuar.
	 * 
	 * @param id unico de la solicitud
	 * @return
	 */
	@GetMapping("continuar/{id}")
	public ResponseEntity<Boolean> continuar(@PathVariable Long id) {
		LOGGER.debug(RevisionController.ETIQUETA_SOLICITUD_CONTROLLER.concat("continuar solicitud"));
		return new ResponseEntity<>(this.revisionService.continuarSolicitud(id), HttpStatus.OK);
	}

	/**
	 * Metodo para evaluar la continuacion de la revicion de doc de la solicitud por
	 * id.
	 * 
	 * @param idSolicitud ID de la solicitud
	 * @return Lista de mensajes de evaluacion de la revision.
	 */
	@GetMapping("continuarRevDoc/{idSolicitud}")
	public ResponseEntity<List<SolFinalizarResponseDto>> continuarRevDoc(@PathVariable Long idSolicitud) {
		LOGGER.debug(RevisionController.ETIQUETA_SOLICITUD_CONTROLLER.concat("continuarRevDoc."));
		return new ResponseEntity<>(this.revisionService.evaluaRevisionDocs(idSolicitud), HttpStatus.OK);
	}
}
