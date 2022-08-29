package com.telcel.sgc.services.war.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.telcel.sgc.services.war.exceptions.DataNotFoundException;
import com.telcel.sgc.services.war.model.SgcOpSolicitud;
import com.telcel.sgc.services.war.model.SgcOprSolActor;
import com.telcel.sgc.services.war.model.dto.SolActualizaActorDTO;
import com.telcel.sgc.services.war.model.dto.SolFinalizarResponseDto;
import com.telcel.sgc.services.war.model.dto.SolGuardaActorDTO;
import com.telcel.sgc.services.war.model.dto.SolGuardaActorDocDTO;
import com.telcel.sgc.services.war.model.dto.SolGuardaActorDocRspnsDTO;
import com.telcel.sgc.services.war.model.dto.SolGuardaActorRspnsDTO;
import com.telcel.sgc.services.war.model.dto.SolGuardaParcialDTO;
import com.telcel.sgc.services.war.model.dto.SolGuardaParcialRspnsDTO;
import com.telcel.sgc.services.war.model.dto.SolicitudSimpleDTO;
import com.telcel.sgc.services.war.model.dto.SolicitudSimpleResponseDTO;
import com.telcel.sgc.services.war.service.ISolicitudService;
import com.telcel.sgc.services.war.service.implementation.SolicitudServiceImpl;
import com.telcel.sgc.services.war.utils.JSONUtils;

/**
 * Solicitud Controller
 */
@RestController
@RequestMapping("/solicitud")
public class SolicitudController extends AbstractDownloadController{
	/**
	 * LOGGER.
	 */
	private static final Logger LOGGER = LogManager.getLogger(SolicitudController.class);
	/**
	 * Etiqueta Tipo Solicitud Controller.
	 */
	private static final String ETIQUETA_SOLICITUD_CONTROLLER = "SolicitudController - ";

	/**
	 * Tipo de Solicitud.
	 */
	@Autowired
	ISolicitudService solicitudService;

	/**
	 * 
	 * @throws DataNotFoundException
	 */
	@PostMapping("/guardarSolicitudSimple")
	public ResponseEntity<SolicitudSimpleResponseDTO> guardarSolicitudSimple(@RequestBody SolicitudSimpleDTO body) {
		LOGGER.debug(SolicitudController.ETIQUETA_SOLICITUD_CONTROLLER.concat("guardarSolicitudSimple"));
		final SolicitudSimpleResponseDTO responseDTO = this.solicitudService.guardarSolicitudSimple(body, "AreaSolicitante");
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * 
	 * @throws DataNotFoundException
	 */
	@PostMapping("/guardarSolicitudParcial")
	public ResponseEntity<SolGuardaParcialRspnsDTO> guardarSolicitudParcial(@RequestBody SolGuardaParcialDTO body) {
		LOGGER.debug(SolicitudController.ETIQUETA_SOLICITUD_CONTROLLER.concat("guardarSolicitudParcial"));
		final SolGuardaParcialRspnsDTO responseDTO = this.solicitudService.guardarSolicitudParcial(body, "TEST");
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * Metodo que guarda al actor.
	 * 
	 * @param body datos del actor.
	 * @return id del actor.
	 */
	@PostMapping("/guardarActor")
	public ResponseEntity<SolGuardaActorRspnsDTO> guardarActor(@RequestBody SolGuardaActorDTO body) {
		LOGGER.debug(SolicitudController.ETIQUETA_SOLICITUD_CONTROLLER.concat("guardarActor"));
		final SolGuardaActorRspnsDTO responseDTO = this.solicitudService.guardarActor(body, "TEST");
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * 
	 * 
	 * @param body
	 * @return
	 */
	@PostMapping("/actualizaActor")
	public ResponseEntity<SolActualizaActorDTO> actualizaActor(@RequestBody SolActualizaActorDTO body) {
		LOGGER.debug("TipoSolicitudController - obtenerTipoSolicitudPorArea");
		final SolActualizaActorDTO responseDTO = this.solicitudService.actualizarActor(body, "TEST");
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * @param file
	 * @param solGuardaActorDocDTO
	 * @return
	 */
	@PostMapping("/cargaArchivoActor")
	public ResponseEntity<SolGuardaActorDocRspnsDTO> cargaArchivoActor(@RequestParam("file") MultipartFile file,
			@RequestParam("solGuardaActorDocDTO") String solGuardaActorDocDTO) {
		LOGGER.debug("Pojo ::::::::  " + solGuardaActorDocDTO);

		final SolGuardaActorDocDTO body = JSONUtils.convert2Object(solGuardaActorDocDTO, SolGuardaActorDocDTO.class);
		final SolGuardaActorDocRspnsDTO responseDTO = this.solicitudService.guardarActorDoc(body, "TEST", file);

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * @param id
	 * @param request
	 * @return
	 */
	@GetMapping(SolicitudServiceImpl.SOL_DOC + "{id:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable Long id, HttpServletRequest request) {
		Resource resource = this.solicitudService.getDocResourceById(id);
		return this.downloadFile(resource, request, LOGGER);
	}

	/**
	 * @param id
	 * @return
	 */
	@DeleteMapping("/borraArchivoSol/{id:.+}")
	public ResponseEntity<Boolean> borraArchivoSol(@PathVariable Long id) {
		return new ResponseEntity<>(this.solicitudService.deleteSolDocById(id), HttpStatus.OK);
	}

	/**
	 * Metodo para eliminar solicitud (baja logica)
	 * 
	 * @param id
	 * @return
	 */
	@PutMapping
	public ResponseEntity<Integer> cambiarEstatus(@RequestParam("idSolicitud") Long idSolicitud, @RequestParam("idEstatus") Long idEstatus,
			@RequestParam("activo") Integer activo) {
		LOGGER.debug(SolicitudController.ETIQUETA_SOLICITUD_CONTROLLER.concat("cambiarEstatus"));
		int cambiarEstatus = solicitudService.cambiarEstatus(idSolicitud, idEstatus, activo, "");
		if (cambiarEstatus == 1) {
			return new ResponseEntity<>(cambiarEstatus, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(cambiarEstatus, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Metodo para buscar solicitud
	 * 
	 * @param id 
	 * 		id de la solicitud
	 * @param idEstatus
	 * 		id del estatus
	 * @param activo
	 * 		solicitud esta activa (1: activa, 0: inactiva)
	 * @param docActiva
	 * 		documentacion esta activa (1: activa, 0: inactiva)
	 * @return
	 */
	@GetMapping("/{id}/estatus/{idEstatus}/activo/{activo}/docActiva/{docActiva}")
	public ResponseEntity<Optional<SgcOpSolicitud>> obtener(@PathVariable Long id, @PathVariable Long idEstatus, @PathVariable Integer activo,
			@PathVariable Integer docActiva) {
		LOGGER.debug(SolicitudController.ETIQUETA_SOLICITUD_CONTROLLER.concat("obtener."));
		Optional<SgcOpSolicitud> sgcOpSolicitud = solicitudService.buscarPorIdAndActivo(id, activo, idEstatus, docActiva);
		if (!sgcOpSolicitud.isPresent()) {
			LOGGER.error((SolicitudController.ETIQUETA_SOLICITUD_CONTROLLER.concat("obtener").concat(" - NOT FOUND")));
			throw new DataNotFoundException("No encontrada: Solicitud", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(sgcOpSolicitud, HttpStatus.OK);
	}

	/**
	 * Metodo para obtener la solicitud por id.
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("puedeFinalizar/{idSolicitud}/tipoSol/{idTipoSol}/empresa/{idEmpresa}")
	public ResponseEntity<List<SolFinalizarResponseDto>> puedeFinalizar(@PathVariable Long idSolicitud, @PathVariable Long idTipoSol, @PathVariable Long idEmpresa) {
		LOGGER.debug(SolicitudController.ETIQUETA_SOLICITUD_CONTROLLER.concat("puede finalizar."));
		return new ResponseEntity<>(this.solicitudService.finalizarSolicitud(idSolicitud, idTipoSol, idEmpresa), HttpStatus.OK);
	}
	
	/**
	 * Metodo para la documentacion por actor
	 * 
	 * @param id
	 * 		id del actor con su documentacion
	 * @param activo
	 * 		1: activa, 0: inactiva
	 * @param docActiva
	 * 		documentacion esta activa (1: activa, 0: inactiva)
	 * @return
	 */
	@GetMapping("/{id}/activo/{activo}/docActiva/{docActiva}")
	public ResponseEntity<Optional<SgcOprSolActor>> obtenerSolActor(@PathVariable Long id, @PathVariable Integer activo,
			@PathVariable Integer docActiva) {
		LOGGER.debug(SolicitudController.ETIQUETA_SOLICITUD_CONTROLLER.concat("obtener."));
		Optional<SgcOprSolActor> sgcOprSolActor = solicitudService.buscarSolActorPorIdAndActivo(id, activo, docActiva);
		if (!sgcOprSolActor.isPresent()) {
			LOGGER.error((SolicitudController.ETIQUETA_SOLICITUD_CONTROLLER.concat("obtener").concat(" - NOT FOUND")));
			throw new DataNotFoundException("No encontrada: Solicitud", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(sgcOprSolActor, HttpStatus.OK);
	}
	
	/**
	 * Metodo para borrar solicitud (logica), por id de solicitud y id actor persona
	 * 
	 * @param idSolicitud
	 * @param idActorPersona
	 * @return
	 */
	@PutMapping("/oprSolActor/eliminar/{idOprSolActor}")
	public ResponseEntity<Integer> eliminarSolActor(@PathVariable Long idOprSolActor) {
		int update = this.solicitudService.deleteSgcTrActorPersonaById(idOprSolActor, "USER_MOD");
		if (update == 1) {
			return new ResponseEntity<>(update, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(update, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Metodo para cambiar estatus de la solicitud para el BPM
	 * 
	 * @Ppara idSolicitud
	 * @param idEstatus
	 * @return
	 */
	@PutMapping("/estatus/bpm")
	public ResponseEntity<Integer> cambiarEstatusBMP(@RequestParam("idSolicitud") Long idSolicitud, @RequestParam("idEstatus") Long idEstatus) {
		LOGGER.debug(SolicitudController.ETIQUETA_SOLICITUD_CONTROLLER.concat("cambiarEstatusBPM"));
		int cambiarEstatus = solicitudService.cambiarEstatusBPM(idSolicitud, idEstatus);
		if (cambiarEstatus == 1) {
			return new ResponseEntity<>(cambiarEstatus, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(cambiarEstatus, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Metodo para eliminar solicitud (baja logica)
	 * 
	 * @param id
	 * @return
	 */
	@PutMapping("/completarTareaBPM")
	public ResponseEntity<Integer> completarTareaBPM(@RequestParam("idSolicitud") Long idSolicitud, @RequestParam("idEstatus") Long idEstatus,
			@RequestParam("activo") Integer activo) {
		LOGGER.debug(SolicitudController.ETIQUETA_SOLICITUD_CONTROLLER.concat("cambiarEstatus"));
		int cambiarEstatus = solicitudService.continuarTareaBPM(idSolicitud, idEstatus, activo);
		if (cambiarEstatus == 1) {
			return new ResponseEntity<>(cambiarEstatus, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(cambiarEstatus, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
