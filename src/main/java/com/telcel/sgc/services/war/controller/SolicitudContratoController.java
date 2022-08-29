package com.telcel.sgc.services.war.controller;

import java.util.Optional;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.telcel.sgc.services.war.exceptions.DataNotFoundException;
import com.telcel.sgc.services.war.exceptions.ServiceException;
import com.telcel.sgc.services.war.model.SgcOprSolContrato;
import com.telcel.sgc.services.war.model.dto.SolContratoEnvioCorreoDTO;
import com.telcel.sgc.services.war.model.dto.SolContratoEnvioCorreoRspnsDTO;
import com.telcel.sgc.services.war.service.ISgcOprSolContratoService;
import com.telcel.sgc.services.war.utils.JSONUtils;

/**
 * SolicitudContrato Controller
 */
@RestController
@RequestMapping("/solicitud/contrato")
public class SolicitudContratoController extends AbstractDownloadController {
	/**
	 * LOGGER.
	 */
	private static final Logger LOGGER = LogManager.getLogger();
	/**
	 * "SolicitudContratoController - "
	 */
	private static final String ETIQUERTA_SOL_CONTRATO_CONTROLLER = "SolicitudContratoController - ";
	
	/**
	 * SgcOprSolContrato Service
	 */
	@Autowired
	ISgcOprSolContratoService iSgcOprSolContratoService;

	/**
	 * Metodo para eliminar un contrato y (si existieran) convenios y anexos.
	 * 
	 * @param id
	 * @return
	 */
	@PutMapping("/eliminar")
	public ResponseEntity<Integer> eliminarContratoConvenioYAnexos(@RequestParam("id") Long id) {
		LOGGER.debug(ETIQUERTA_SOL_CONTRATO_CONTROLLER.concat("eliminarContratoConvenioYAnexos id: {}"), id);
		int eliminar = this.iSgcOprSolContratoService.eliminarContratoConvenioYAnexos(id, "SYSTEM_TEST");
		if (eliminar == 0) {
			throw new ServiceException("Error al eliminar contrato, sus convenios y anexos.");
		}
		return new ResponseEntity<>(eliminar, HttpStatus.OK);
	}
	
	/**
	 * Metodo para obtener contrato por id y activo.
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{idContrato}/activo/{activo}")
	public ResponseEntity<SgcOprSolContrato> obtenerByIdAndActivo(@RequestParam("idContrato") Long id, @RequestParam("activo") Integer activo) {
		LOGGER.debug(ETIQUERTA_SOL_CONTRATO_CONTROLLER.concat("obtenerByIdAndActivo id: {}, activo: {}"), id, activo);
		Optional<SgcOprSolContrato> sgcOprSolContrato = this.iSgcOprSolContratoService.findByIdAndActivo(id, activo);
		
		if (!sgcOprSolContrato.isPresent()) {
			LOGGER.error(ETIQUERTA_SOL_CONTRATO_CONTROLLER.concat("obtenerById - NOT FOUND"));
			throw new DataNotFoundException("No encontrado: Contrato", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(sgcOprSolContrato.get(), HttpStatus.OK);
	}
	
	/**
	 * Metodo para envio de contrato por correo.
	 * 
	 * @param file
	 * @param solContratoEnvioCorreoDTO
	 * @return
	 */
	@PostMapping("/envio/correo")
	public ResponseEntity<SolContratoEnvioCorreoRspnsDTO> enviarCorreo(@RequestParam(value="file", required = false) MultipartFile file, 
			@RequestParam("solContratoEnvioCorreoDTO") String solContratoEnvioCorreoDTO) {
		LOGGER.debug("Pojo ::::::::  " + solContratoEnvioCorreoDTO);
		final SolContratoEnvioCorreoDTO bodySolContratoEnvioCorreoDTO = JSONUtils.convert2Object(solContratoEnvioCorreoDTO, SolContratoEnvioCorreoDTO.class);
		final SolContratoEnvioCorreoRspnsDTO responseSolContratoEnvioCorreoDTO = this.iSgcOprSolContratoService.enviarCorreo(
				bodySolContratoEnvioCorreoDTO, "TEST", file);
		
		return new ResponseEntity<>(responseSolContratoEnvioCorreoDTO, HttpStatus.OK);
	}
	
	/**
	 * Metodo para descargar el zip con todos los documentos activos adjuntos de
	 * contrato.
	 * 
	 * @param idContratoEnvio ID de la Solicitud a descargar la documentacion.
	 * @param token                token de descarga.
	 * @param request              Peticion HTTP de la descarga.
	 * @return Descarga del archivo zip.
	 */
	@GetMapping("/descargaZipContrato/{idContratoEnvio}/{token}")
	public ResponseEntity<Resource> descargaZipContrato(@PathVariable Long idContratoEnvio,
			@PathVariable String token, HttpServletRequest request) {
		Resource resource = this.iSgcOprSolContratoService.obtenerZipPorToken(idContratoEnvio, token);
		return this.downloadFile(resource, request, LOGGER);
	}
}
