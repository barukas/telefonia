package com.telcel.sgc.services.war.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.telcel.sgc.services.war.exceptions.DataNotFoundException;
import com.telcel.sgc.services.war.model.SgcCiDocPlantilla;
import com.telcel.sgc.services.war.model.dto.CargaDocPlantillaDTO;
import com.telcel.sgc.services.war.model.dto.CargaDocPlantillaRspnsDTO;
import com.telcel.sgc.services.war.service.IDocumentoPlantillaService;
import com.telcel.sgc.services.war.service.implementation.DocumentoPlantillaServiceImpl;
import com.telcel.sgc.services.war.utils.JSONUtils;

/**
 * DocumentoPlantilla Controller.
 */
@RestController
@RequestMapping("/doc/plantilla")
public class DocumentoPlantillaController extends AbstractDownloadController {
	/**
	 * LOGGER.
	 */
	private static final Logger LOGGER = LogManager.getLogger();
	/**
	 * "DocumentoPlantillaController - "
	 */
	private static final String ETIQUETA_DOC_PLANTILLA_CONTROLLER = "DocumentoPlantillaController - ";

	/**
	 * DocumentoPlantilla Service.
	 */
	@Autowired
	private IDocumentoPlantillaService documentoPlantillaService;

	/**
	 * @param file
	 * @param solGuardaActorDocDTO
	 * @return
	 */
	@PostMapping("/carga")
	public ResponseEntity<CargaDocPlantillaRspnsDTO> cargaArchivoActor(@RequestParam("file") MultipartFile file,
			@RequestParam("cargaDocPlantillaDTO") String cargaDocPlantillaDTO) {
		LOGGER.debug("Pojo :::::::: {} ", cargaDocPlantillaDTO);

		final CargaDocPlantillaDTO body = JSONUtils.convert2Object(cargaDocPlantillaDTO, CargaDocPlantillaDTO.class);
		final CargaDocPlantillaRspnsDTO responseDTO = this.documentoPlantillaService.guardaDocPlantilla(body, file,
				"TEST");

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * @param id
	 * @param request
	 * @return
	 */
	@GetMapping(DocumentoPlantillaServiceImpl.DESCARGA + "{id:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable Long id, HttpServletRequest request) {
		Resource resource = this.documentoPlantillaService.getDocResourceById(id);
		return this.downloadFile(resource, request, LOGGER);
	}

	/**
	 * @param id
	 * @param request
	 * @return
	 */
	@DeleteMapping("/borrar/{id:.+}")
	public ResponseEntity<Boolean> borrarDoc(@PathVariable Long id) {
		return new ResponseEntity<>(this.documentoPlantillaService.deleteDocById(id, "TEST"), HttpStatus.OK);
	}

	/**
	 * Metodo para obtener las plantillas por tipo de documento
	 * 
	 * @param page
	 * @param elementos
	 * @param idTipoDocPlantilla
	 * @return
	 */
	@GetMapping("/page/{page}/elementos/{elementos}/tipoDoc/{idTipoDocPlantilla}/activo/{activo}")
	public ResponseEntity<Page<SgcCiDocPlantilla>> obtenerDocumentos(@PathVariable Integer page,
			@PathVariable Integer elementos, @PathVariable Long idTipoDocPlantilla, @PathVariable Integer activo) {
		LOGGER.debug(ETIQUETA_DOC_PLANTILLA_CONTROLLER.concat("obtenerDocumentos"));

		Page<SgcCiDocPlantilla> listSgcCiDocPlantilla = this.documentoPlantillaService.obtenerDocumentos(page,
				elementos, idTipoDocPlantilla,activo);
		if (listSgcCiDocPlantilla.isEmpty()) {
			LOGGER.error(ETIQUETA_DOC_PLANTILLA_CONTROLLER.concat("obtenerDocumentos - NOT FOUND"));
			throw new DataNotFoundException("No encontrado: documentos", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listSgcCiDocPlantilla, HttpStatus.OK);
	}
}
