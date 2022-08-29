package com.telcel.sgc.services.war.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.telcel.sgc.services.war.model.dto.AgregaConveAnexoDTO;
import com.telcel.sgc.services.war.model.dto.AgregaConveAnexoRspnsDTO;
import com.telcel.sgc.services.war.model.dto.AgregaConveAnexoXComplementarioDTO;
import com.telcel.sgc.services.war.model.dto.ContratoRspnsDTO;
import com.telcel.sgc.services.war.model.dto.ConvAnexRspnsDTO;
import com.telcel.sgc.services.war.model.dto.GeneraContratoDTO;
import com.telcel.sgc.services.war.model.dto.GeneraContratoRspnsDTO;
import com.telcel.sgc.services.war.model.dto.GeneraContratoXComplementoDTO;
import com.telcel.sgc.services.war.service.IContratoService;
import com.telcel.sgc.services.war.service.implementation.ContratoServiceImpl;

/**
 * ContratoController.
 */
@RestController
@RequestMapping("/contrato")
public class ContratoController extends AbstractDownloadController {
	/**
	 * LOGGER.
	 */
	private static final Logger LOGGER = LogManager.getLogger(ContratoController.class);

	/**
	 * IContratoService.
	 */
	@Autowired
	private IContratoService contratoService;

	/**
	 * Metodo para rechazar o cancelar solicitud.
	 * 
	 * @param generaContratoDTO
	 * @return
	 */
	@PostMapping("agregaConvAnex")
	public ResponseEntity<AgregaConveAnexoRspnsDTO> agregaConvAnex(@RequestBody AgregaConveAnexoDTO generaContratoDTO)
			throws Docx4JException, IOException {
		LOGGER.debug("Metodo agregaConvAnex, POJO :::::::: {}",
				ToStringBuilder.reflectionToString(generaContratoDTO, ToStringStyle.JSON_STYLE));
		return new ResponseEntity<>(this.contratoService.agregaConvenioAnexo(generaContratoDTO, "SYSTEM"),
				HttpStatus.OK);
	}

	/**
	 * Metodo para rechazar o cancelar solicitud.
	 * 
	 * @param generaContratoDTO
	 * @return
	 */
	@PostMapping("agregaConvAnexXComplementario")
	public ResponseEntity<AgregaConveAnexoRspnsDTO> agregaConvAnexXComplementario(
			@RequestBody AgregaConveAnexoXComplementarioDTO generaContratoDTO) throws Docx4JException, IOException {
		LOGGER.debug("Metodo agregaConvAnexXComplementario, POJO :::::::: {}",
				ToStringBuilder.reflectionToString(generaContratoDTO, ToStringStyle.JSON_STYLE));
		return new ResponseEntity<>(
				this.contratoService.agregaConvenioAnexoXComplementario(generaContratoDTO, "SYSTEM"), HttpStatus.OK);

	}

	/**
	 * Metodo para generar un nuevo contrato.
	 * 
	 * @param generaContratoDTO
	 * @return
	 */
	@PostMapping("generaNuevo")
	public ResponseEntity<GeneraContratoRspnsDTO> generaNuevo(@RequestBody GeneraContratoDTO generaContratoDTO)
			throws Docx4JException, IOException {
		LOGGER.debug("Metodo generaNuevo, POJO :::::::: {}",
				ToStringBuilder.reflectionToString(generaContratoDTO, ToStringStyle.JSON_STYLE));
		GeneraContratoRspnsDTO contratoRspnsDTO = this.contratoService.generaContrato(generaContratoDTO, "SYSTEM");
		return new ResponseEntity<>(contratoRspnsDTO, HttpStatus.OK);
	}

	/**
	 * Metodo para generar un nuevo contrato.
	 * 
	 * @param generaContratoDTO
	 * @return
	 */
	@PostMapping("generaNuevoXComplementario")
	public ResponseEntity<GeneraContratoRspnsDTO> generaNuevoXComplementario(
			@RequestBody GeneraContratoXComplementoDTO generaContratoDTO) throws Docx4JException, IOException {
		LOGGER.debug("Metodo generaNuevoXComplementario, POJO :::::::: {}",
				ToStringBuilder.reflectionToString(generaContratoDTO, ToStringStyle.JSON_STYLE));
		GeneraContratoRspnsDTO contratoRspnsDTO = this.contratoService.generaContratoXComplementario(generaContratoDTO,
				"SYSTEM");
		return new ResponseEntity<>(contratoRspnsDTO, HttpStatus.OK);

	}

	/**
	 * @param id
	 * @param request
	 * @return
	 */
	@GetMapping(ContratoServiceImpl.CONTRACT_DOC_DWNLD + "{id:.+}")
	public ResponseEntity<Resource> contratoDoc(@PathVariable Long id, HttpServletRequest request) {
		Resource resource = this.contratoService.getContratoVersionResourceById(id);
		return this.downloadFile(resource, request, LOGGER);
	}

	/**
	 * 
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@GetMapping(ContratoServiceImpl.CONVANEX_DOC_DWNLD + "{id:.+}")
	public ResponseEntity<Resource> convAnexDoc(@PathVariable Long id, HttpServletRequest request) {
		Resource resource = this.contratoService.getConvAnexVersionResourceById(id);
		return this.downloadFile(resource, request, LOGGER);
	}

	/**
	 * 
	 * 
	 * @param file
	 * @param idContrato
	 * @return
	 */
	@PostMapping("/cargaContrato")
	public ResponseEntity<ContratoRspnsDTO> cargaContrato(@RequestParam("file") MultipartFile file,
			@RequestParam("idContrato") Long idContrato) throws IOException {
		LOGGER.debug("Pojo :::::::: {} ", idContrato);

		return new ResponseEntity<>(this.contratoService.cargaContrato(idContrato, file, "TEST"), HttpStatus.OK);
	}

	/**
	 * 
	 * 
	 * @param file
	 * @param idConvAnex
	 * @return
	 */
	@PostMapping("/cargaConvAnex")
	public ResponseEntity<ConvAnexRspnsDTO> cargaConvAnex(@RequestParam("file") MultipartFile file,
			@RequestParam("idConvAnex") Long idConvAnex) {
		LOGGER.debug("Pojo :::::::: {} ", idConvAnex);
		return new ResponseEntity<>(this.contratoService.cargaConvAnex(idConvAnex, file, "TEST"), HttpStatus.OK);
	}

	/**
	 * 
	 * 
	 * @param idContrato
	 * @param tipoMarca
	 * @return
	 */
	@PutMapping("/estableceMarca/{idContrato}/{tipoMarca}")
	public ResponseEntity<ContratoRspnsDTO> cargaConvAnex(@PathVariable Long idContrato,
			@PathVariable Integer tipoMarca) {
		LOGGER.debug("Pojo :::::::: {},{} ", idContrato, tipoMarca);
		return new ResponseEntity<>(this.contratoService.estableceMarcaOSello(idContrato, "TEST", tipoMarca),
				HttpStatus.OK);
	}
}
