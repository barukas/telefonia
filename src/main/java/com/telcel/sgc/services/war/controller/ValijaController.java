package com.telcel.sgc.services.war.controller;

import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.telcel.sgc.services.war.exceptions.DataNotFoundException;
import com.telcel.sgc.services.war.exceptions.ServiceException;
import com.telcel.sgc.services.war.model.SgcOprValija;
import com.telcel.sgc.services.war.model.dto.SgcOprValijaDTO;
import com.telcel.sgc.services.war.model.dto.SgcOprValijaResponseDTO;
import com.telcel.sgc.services.war.service.ISgcOprValijaService;

/**
 * Valija Controller
 */
@RestController
@RequestMapping("/valija")
public class ValijaController {
	/**
	 * LOGGER.
	 */
	private static final Logger LOGGER = LogManager.getLogger();
	/**
	 * "ValijaController - "
	 */
	private static final String ETIQUETA_VALIJA_CONTROLLER = "ValijaController - ";
	/**
	 * SgcOprValija Service
	 */
	@Autowired
	ISgcOprValijaService iSgcOprValijaService;
	
	/**
	 * Metodo para generar el codigo de valija y la informacion para pintar en el modal.
	 * 
	 * @param idContrato
	 * @param tipoValija
	 * @return
	 */
	@GetMapping("contrato/{idContrato}/valija/{tipoValija}")
	public ResponseEntity<Map<String, Object>> controlDeValija(@PathVariable Long idContrato, @PathVariable Long tipoValija) {
		LOGGER.debug(ETIQUETA_VALIJA_CONTROLLER.concat("controlDeValija, tipoValija: {}"), tipoValija);
		Map<String, Object> controlDeValija = this.iSgcOprValijaService.codigoDeValija(idContrato, tipoValija);
		if (controlDeValija == null || controlDeValija.isEmpty()) {
			throw new ServiceException("Error al generar el Control de Valija");
		}
		return new ResponseEntity<>(controlDeValija, HttpStatus.OK);
	}
	
	/**
	 * Metodo para actualizar el estatus de la valija.
	 * 
	 * @param sgcOprValijaDTO
	 * @return
	 */
	@PostMapping("/actualiza")
	public ResponseEntity<SgcOprValijaResponseDTO> actualizaValija(@RequestBody SgcOprValijaDTO sgcOprValijaDTO) {
		LOGGER.debug(ETIQUETA_VALIJA_CONTROLLER.concat("actualizaValija"));
		final SgcOprValijaResponseDTO sgcOprValijaResponseDTO = this.iSgcOprValijaService.actualizaValija(sgcOprValijaDTO);
		return new ResponseEntity<>(sgcOprValijaResponseDTO, HttpStatus.OK);
	}
	
	/**
	 * Metodo para buscar valija por estatus, folio y activo
	 * 
	 * @param estatus
	 * @param folio
	 * @param activo
	 * 		1 : activo
	 *      0 : desactivado
	 * @return
	 */
	@GetMapping("/estatus/{estatus}/folio/{folio}/activo/{activo}")
	public ResponseEntity<SgcOprValija> buscarPorEstatusYFolioYActivo(@RequestParam("estatus") String estatus, @RequestParam("folio") String folio, 
			@RequestParam("activo") Integer activo) {
		LOGGER.debug(ETIQUETA_VALIJA_CONTROLLER.concat("buscarPorEstatusYFolioYActivo, estatus: {}, folio: {}, activo: {}"), estatus, folio, activo);
		Optional<SgcOprValija> sgcOprValija = this.iSgcOprValijaService.buscarPorEstatusYFolioYActivo(estatus, folio, activo);
		
		if (!sgcOprValija.isPresent()) {
			LOGGER.error(ETIQUETA_VALIJA_CONTROLLER.concat("buscarPorFolio - NOT FOUND"));
			throw new DataNotFoundException("No encontrada: Valija", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(sgcOprValija.get(), HttpStatus.OK);
	}
}
