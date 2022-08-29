package com.telcel.sgc.services.war.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.telcel.sgc.services.war.exceptions.ServiceException;
import com.telcel.sgc.services.war.model.dto.SgcOprConvAnexoDTO;
import com.telcel.sgc.services.war.service.ISgcOprConvAnexService;

/**
 * SolContratoConvAnex Controller
 */
@RestController
@RequestMapping("/solicitud/contrato/convanex")
public class SolContratoConvAnexController {
	/**
	 * LOGGER.
	 */
	private static final Logger LOGGER = LogManager.getLogger();
	/**
	 * "SolContratoConvAnexController - "
	 */
	private static final String ETIQUETA_SOL_CONTRATO_CONVANEX_CONTROLLER = "SolContratoConvAnexController - ";

	/**
	 * SgcOprConvAnex Service
	 */
	@Autowired
	ISgcOprConvAnexService iSgcOprConvAnexService;

	/**
	 * Metodo para eliminar convenios o anexos por contrato.
	 * 
	 * @param id
	 * @param idTipoDocPlantilla
	 * 		2: convenio
	 * 		3: anexo
	 * @return
	 */
	@PutMapping("/eliminar")
	public ResponseEntity<Integer> eliminarConvenioYAnexos(@RequestParam("idContrato") Long id, @RequestParam("idTipoDocPlantilla") Long idTipoDocPlantilla) {
		LOGGER.debug(ETIQUETA_SOL_CONTRATO_CONVANEX_CONTROLLER.concat("eliminarConvenioYAnexos id: {}, tipo doc plantilla: {}"), id, idTipoDocPlantilla);
		int eliminar = this.iSgcOprConvAnexService.eliminarConvenioYAnexos(id, idTipoDocPlantilla, "SYSTEM_TEST");
		if (eliminar == 0) {
			throw new ServiceException("Error al eliminar convenios o anexos.");
		}
		return new ResponseEntity<>(eliminar, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param sgcOprConvAnexoDTO
	 * @return
	 */
	@PostMapping("/eliminar")
	public ResponseEntity<SgcOprConvAnexoDTO> eliminarConvenioOAnexo(@RequestBody SgcOprConvAnexoDTO sgcOprConvAnexoDTO) {
		LOGGER.debug(ETIQUETA_SOL_CONTRATO_CONVANEX_CONTROLLER.concat("eliminarConvenioOAnexo"));
		return new ResponseEntity<>(this.iSgcOprConvAnexService.eliminarConvenioOAnexo(sgcOprConvAnexoDTO, "UserMod"), HttpStatus.OK);
	}
}
