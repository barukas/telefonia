package com.telcel.sgc.services.war.controller;

import java.util.List;
import java.util.Optional;

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
import com.telcel.sgc.services.war.model.SgcCiAsociacion;
import com.telcel.sgc.services.war.model.SgcCiDominio;
import com.telcel.sgc.services.war.model.SgcCiEmpresa;
import com.telcel.sgc.services.war.model.SgcCiTipoAbogado;
import com.telcel.sgc.services.war.model.SgcCiTipoDocPlantilla;
import com.telcel.sgc.services.war.model.SgcCiTipoPersona;
import com.telcel.sgc.services.war.model.SgcCiTipoSolicitud;
import com.telcel.sgc.services.war.service.ICatalagoService;
import com.telcel.sgc.services.war.service.ISgcCiDominioService;
import com.telcel.sgc.services.war.service.ISgcCiEmpresa;
import com.telcel.sgc.services.war.service.ISgcCiTipoPersona;
import com.telcel.sgc.services.war.service.ISgcCiTipoSolicitud;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

/**
 * Catalogo Controller.
 */
@RestController
@RequestMapping("/catalogo")
public class CatalogoController {
	/**
	 * LOGGER.
	 */
	private static final Logger LOGGER = LogManager.getLogger();
	/**
	 * "CatalogoController"
	 */
	private static final String ETIQ_CATALOGO_CONTROLLER = "CatalogoController - ";
	/**
	 * "No encontrado: Asociaciones".
	 */
	private static final String ASOCIACIONES_NOT_FOUND_MSG = "No encontrado: Asociaciones";
	/**
	 * "No encontrado: Tipo Documentacion Plantilla"
	 */
	private static final String TIPO_DOC_PLANTILLA_NOT_FOUND = "No encontrado: Tipo Documentacion Plantilla";
	/**
	 * ""No encontrado: Tipo de Abogado""
	 */
	private static final String TIPO_ABOGADO_NOT_FOUND = "No encontrado: Tipo de Abogado";

	/**
	 * Interfaz Tipo de Persona.
	 */
	@Autowired
	ISgcCiTipoPersona iSgcCiTipoPersona;
	/**
	 * Interfaz Tipo de Solicitud.
	 */
	@Autowired
	ISgcCiTipoSolicitud iSgcCiTipoSolicitud;
	/**
	 * Interfaz Empresa.
	 */
	@Autowired
	ISgcCiEmpresa iSgcCiEmpresa;
	/**
	 * Interfaz Asociacion.
	 */
	@Autowired
	ICatalagoService<SgcCiAsociacion> sgcCiAsociacionService;
	/**
	 * Interfaz Tipo Doc Plantilla.
	 */
	@Autowired
	ICatalagoService<SgcCiTipoDocPlantilla> iCatalogoSgcCiTipoDocPlantilla;
	/**
	 * Interfaz Tipo de Abogado
	 */
	@Autowired
	ICatalagoService<SgcCiTipoAbogado> iCatalogoSgcCiTipoAbogadoService;
	
	/**
	 * Interfaz Dominios.
	 */
	@Autowired
	ISgcCiDominioService iSgcCiDominioService;
	
	/**
	 * Metodo para obtener Tipo de Persona.
	 * 
	 * @return List SgcCiTipoPersona
	 * 
	 * @throws DataNotFoundException
	 */
	@GetMapping("/tipo/persona")
	public ResponseEntity<List<SgcCiTipoPersona>> obtenerTipoPersona() {
		LOGGER.debug(ETIQ_CATALOGO_CONTROLLER.concat("obtenerTipoPersona"));
		List<SgcCiTipoPersona> listSgcCiTipoPersona = this.iSgcCiTipoPersona.findAll();
		if (listSgcCiTipoPersona.isEmpty()) {
			LOGGER.error("CatalogoController - obtenerTipoPersona - NOT FOUND");
			throw new DataNotFoundException("No encontrado: Tipo de Persona", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listSgcCiTipoPersona, HttpStatus.OK);
	}

	/**
	 * Metodo para obtener Tipo de Persona por estatus.
	 * 
	 * @param activo 1=activo, 0=inactivo
	 * 
	 * @return List SgcCiTipoPersona
	 * 
	 * @throws DataNotFoundException
	 */
	@GetMapping("/tipo/persona/{activo}")
	public ResponseEntity<List<SgcCiTipoPersona>> obtenerTipoPersonaByActivo(@PathVariable Integer activo) {
		LOGGER.debug(ETIQ_CATALOGO_CONTROLLER.concat("obtenerTipoPersonaByActivo, activo: {}"), activo);
		List<SgcCiTipoPersona> listSgcCiTipoPersona = this.iSgcCiTipoPersona.findAllByActivo(activo);
		if (listSgcCiTipoPersona.isEmpty()) {
			LOGGER.error(ETIQ_CATALOGO_CONTROLLER.concat("obtenerTipoPersonaByActivo - NOT FOUND"));
			throw new DataNotFoundException("No encontrado: Tipo de Persona", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listSgcCiTipoPersona, HttpStatus.OK);
	}

	/**
	 * Metodo para obtener Tipo de Solicitud.
	 * 
	 * @return List SgcCiTipoSolicitud
	 * 
	 * @throws DataNotFoundException
	 */
	@GetMapping("/tipo/solicitud")
	public ResponseEntity<List<SgcCiTipoSolicitud>> obtenerTipoSolicitud() {
		LOGGER.debug(ETIQ_CATALOGO_CONTROLLER.concat("obtenerTipoSolicitud"));
		List<SgcCiTipoSolicitud> listSgcCiTipoSolicitud = this.iSgcCiTipoSolicitud.findAll();
		if (listSgcCiTipoSolicitud.isEmpty()) {
			LOGGER.error(ETIQ_CATALOGO_CONTROLLER.concat("obtenerTipoSolicitud - NOT FOUND"));
			throw new DataNotFoundException("No encontrado: Tipo de Solicitud", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listSgcCiTipoSolicitud, HttpStatus.OK);
	}

	/**
	 * Metodo para obtener Tipo de Solicitud por estatus
	 * 
	 * @param activo activo 1=activo, 0=inactivo
	 * @returnimport org.springframework.data.domain.Page;
	 */
	@GetMapping("/tipo/solicitud/page/{page}/elementos/{elementos}/activo/{activo}")
	public ResponseEntity<Page<SgcCiTipoSolicitud>> obtenerTipoSolicitudByActivo(@PathVariable Integer page, @PathVariable Integer elementos, 
			@PathVariable Integer activo) {
		LOGGER.debug(ETIQ_CATALOGO_CONTROLLER.concat("obtenerTipoSolicitudByActivo, activo: {}"), activo);
		Page<SgcCiTipoSolicitud> listSgcCiTipoSolicitud = this.iSgcCiTipoSolicitud.findAllByActivo(page, elementos, activo);
		if (listSgcCiTipoSolicitud.isEmpty()) {
			LOGGER.error(ETIQ_CATALOGO_CONTROLLER.concat("obtenerTipoSolicitudByActivo - NOT FOUND"));
			throw new DataNotFoundException("No encontrado: Tipo de Solicitud", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listSgcCiTipoSolicitud, HttpStatus.OK);
	}
	
	/**
	 * Metodo para obtener Empresa.
	 * 
	 * @return List SgcCiEmpresa
	 * 
	 * @throws DataNotFoundException
	 */
	@GetMapping("/empresa")
	public ResponseEntity<List<SgcCiEmpresa>> obtenerEmpresa() {
		LOGGER.debug(ETIQ_CATALOGO_CONTROLLER.concat("obtenerEmpresa"));
		List<SgcCiEmpresa> listSgcCiEmpresa = this.iSgcCiEmpresa.findAll();
		if (listSgcCiEmpresa.isEmpty()) {
			LOGGER.error(ETIQ_CATALOGO_CONTROLLER.concat("obtenerEmpresa - NOT FOUND"));
			throw new DataNotFoundException("No encontrado: Empresa", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listSgcCiEmpresa, HttpStatus.OK);
	}

	/**
	 * Metodo para obtener las Empresas por estatus.
	 * 
	 * @param activo
	 * 		 1=activo, 0=inactivo
	 * @param page
	 * @param elementos
	 * @return
	 */
	@GetMapping("/empresa/{activo}/page/{page}/elementos/{elementos}")
	public ResponseEntity<Page<SgcCiEmpresa>> obtenerEmpresaByActivo(@PathVariable Integer activo, @PathVariable Integer page, @PathVariable Integer elementos) {
		LOGGER.debug(ETIQ_CATALOGO_CONTROLLER.concat("obtenerEmpresaByActivo, activo: {}"), activo);
		Page<SgcCiEmpresa> listSgcCiEmpresaByActivo = this.iSgcCiEmpresa.findAllByActivo(activo, page, elementos);
		if (listSgcCiEmpresaByActivo.isEmpty()) {
			LOGGER.error(ETIQ_CATALOGO_CONTROLLER.concat("obtenerEmpresaByActivo - NOT FOUND"));
			throw new DataNotFoundException("No encontrado: Empresa", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listSgcCiEmpresaByActivo, HttpStatus.OK);
	}

	/**
	 * Metodo para obtener Asociaciones.
	 * 
	 * @return
	 */
	@GetMapping("/asociacion")
	public ResponseEntity<List<SgcCiAsociacion>> obtenerAsociaciones() {
		LOGGER.debug(ETIQ_CATALOGO_CONTROLLER.concat("obtenerAsociaciones"));
		List<SgcCiAsociacion> asociacionList = this.sgcCiAsociacionService.findAll();
		if (asociacionList.isEmpty()) {
			LOGGER.error(ETIQ_CATALOGO_CONTROLLER.concat("obtenerAsociaciones - NOT FOUND"));
			throw new DataNotFoundException(ASOCIACIONES_NOT_FOUND_MSG, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(asociacionList, HttpStatus.OK);
	}

	/**
	 * Metodo para obtener las Asociaciones por estatus.
	 * 
	 * @param activo
	 * 		1=activo, 0=inactivo
	 * 
	 * @return List 
	 */
	@GetMapping("/asociacion/{activo}")
	public ResponseEntity<List<SgcCiAsociacion>> obtenerAsociacionesByActivo(@PathVariable Integer activo) {
		LOGGER.debug(ETIQ_CATALOGO_CONTROLLER.concat("obtenerAsociacionesByActivo, activo: {}"), activo);
		List<SgcCiAsociacion> listSgcCiEmpresaByActivo = this.sgcCiAsociacionService.findAllByActivo(activo);
		if (listSgcCiEmpresaByActivo.isEmpty()) {
			LOGGER.error(ETIQ_CATALOGO_CONTROLLER.concat("obtenerAsociacionesByActivo - NOT FOUND"));
			throw new DataNotFoundException(ASOCIACIONES_NOT_FOUND_MSG, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listSgcCiEmpresaByActivo, HttpStatus.OK);
	}
	
	/**
	 * Metodo para obtener todos los tipos de plantilla de la documentacion
	 * 
	 * @return
	 */
	@GetMapping("/documentacion/plantilla")
	public ResponseEntity<List<SgcCiTipoDocPlantilla>> obtenerTipoDocPlantilla() {
		LOGGER.debug(ETIQ_CATALOGO_CONTROLLER.concat("obtenerTipoDocPlantilla"));
		List<SgcCiTipoDocPlantilla> listSgcCiTipoDocPlantilla = this.iCatalogoSgcCiTipoDocPlantilla.findAll();
		if (listSgcCiTipoDocPlantilla.isEmpty()) {
			LOGGER.error(ETIQ_CATALOGO_CONTROLLER.concat("obtenerTipoDocPlantilla - NOT FOUND"));
			throw new DataNotFoundException(TIPO_DOC_PLANTILLA_NOT_FOUND, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listSgcCiTipoDocPlantilla, HttpStatus.OK);
	}
	
	/**
	 * Metodo para obtener el tipo de plantilla de la documentacion por estatus
	 * 
	 * @param activo
	 * 		1: activo, 0: desactivada
	 * @return
	 */
	@GetMapping("/documentacion/plantilla/activo/{activo}")
	public ResponseEntity<List<SgcCiTipoDocPlantilla>> obtenerTipoDocPlantillaActivo(@PathVariable Integer activo) {
		LOGGER.debug(ETIQ_CATALOGO_CONTROLLER.concat("obtenerTipoDocPlantillaActivo, activo: {}"), activo);
		List<SgcCiTipoDocPlantilla> listSgcCiTipoDocPlantilla = this.iCatalogoSgcCiTipoDocPlantilla.findAllByActivo(activo);
		if (listSgcCiTipoDocPlantilla.isEmpty()) {
			LOGGER.error(ETIQ_CATALOGO_CONTROLLER.concat("obtenerTipoDocPlantillaActivo - NOT FOUND"));
			throw new DataNotFoundException(TIPO_DOC_PLANTILLA_NOT_FOUND, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listSgcCiTipoDocPlantilla, HttpStatus.OK);
	}
	
	/**
	 * 
	 * 
	 * @param idTipoDocPlantilla
	 * @param activo
	 * 		1: activo, 0: desactivada
	 * @return
	 */
	@GetMapping("/documentacion/plantilla/id/{idTipoDocPlantilla}/activo/{activo}")
	public ResponseEntity<Optional<SgcCiTipoDocPlantilla>> obtenerTipoDocPlantillaByIdAndActivo(@PathVariable Long idTipoDocPlantilla, @PathVariable Integer activo) {
		LOGGER.debug(ETIQ_CATALOGO_CONTROLLER.concat("obtenerTipoDocPlantillaByIdAndActivo, idTipoDocPlantilla: {}, activo: {}"), idTipoDocPlantilla, activo);
		Optional<SgcCiTipoDocPlantilla> listSgcCiTipoDocPlantilla = this.iCatalogoSgcCiTipoDocPlantilla.findAllByIdAndActivo(idTipoDocPlantilla, activo);
		if (!listSgcCiTipoDocPlantilla.isPresent()) {
			LOGGER.error(ETIQ_CATALOGO_CONTROLLER.concat("obtenerTipoDocPlantillaActivo - NOT FOUND"));
			throw new DataNotFoundException(TIPO_DOC_PLANTILLA_NOT_FOUND, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listSgcCiTipoDocPlantilla, HttpStatus.OK);
	}
	
	/**
	 * Metodo para obtener los dominios por estatus.
	 * 
	 * @param activo 1=activo, 0=inactivo
	 * 
	 * @return List SgcCiDominio
	 * 
	 * @throws DataNotFoundException
	 */
	@GetMapping("/dominio/{activo}")
	public ResponseEntity<List<SgcCiDominio>> obtenerDominioByActivo(@PathVariable Integer activo) {
		LOGGER.debug(ETIQ_CATALOGO_CONTROLLER.concat("obtenerDominioByActivo, activo: {}"), activo);
		List<SgcCiDominio> listSgcCiDominio = this.iSgcCiDominioService.findAllByActivo(activo);
		if (listSgcCiDominio.isEmpty()) {
			LOGGER.error(ETIQ_CATALOGO_CONTROLLER.concat("obtenerDominioByActivo - NOT FOUND"));
			throw new DataNotFoundException("No encontrado: Dominio", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listSgcCiDominio, HttpStatus.OK);
	}
	
	/**
	 * Metodo para obtener los tipos de abogado.
	 * 
	 * @return List 
	 * 
	 * @throws DataNotFoundException
	 */
	@GetMapping("/tipo/abogado")
	public ResponseEntity<List<SgcCiTipoAbogado>> obtenerTipoAbogado() {
		LOGGER.debug(ETIQ_CATALOGO_CONTROLLER.concat("obtenerTipoAbogado"));
		List<SgcCiTipoAbogado> sgcCiTipoAbogado = this.iCatalogoSgcCiTipoAbogadoService.findAll();
		if (sgcCiTipoAbogado.isEmpty()) {
			LOGGER.error(ETIQ_CATALOGO_CONTROLLER.concat("obtenerTipoAbogado - NOT FOUND"));
			throw new DataNotFoundException(TIPO_ABOGADO_NOT_FOUND, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(sgcCiTipoAbogado, HttpStatus.OK);
	}

	/**
	 * Metodo para obtener los tipos de abogado por estatus.
	 * 
	 * @param activo
	 * 		1=activo, 0=inactivo
	 * 
	 * @return List 
	 * 
	 * @throws DataNotFoundException
	 */
	@GetMapping("/tipo/abogado/{activo}")
	public ResponseEntity<List<SgcCiTipoAbogado>> obtenerTipoAbogadoByActivo(@PathVariable Integer activo) {
		LOGGER.debug(ETIQ_CATALOGO_CONTROLLER.concat("obtenerTipoAbogadoByActivo, activo: {}"), activo);
		List<SgcCiTipoAbogado> sgcCiTipoAbogado = this.iCatalogoSgcCiTipoAbogadoService.findAllByActivo(activo);
		if (sgcCiTipoAbogado.isEmpty()) {
			LOGGER.error(ETIQ_CATALOGO_CONTROLLER.concat("obtenerTipoAbogadoByActivo - NOT FOUND"));
			throw new DataNotFoundException(TIPO_ABOGADO_NOT_FOUND, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(sgcCiTipoAbogado, HttpStatus.OK);
	}
	
	/**
	 * Metodo para obtener las Empresas por estatus.
	 * 
	 * @param activo
	 * 		 1=activo, 0=inactivo
	 * @return
	 */
	@GetMapping("/empresa/{activo}")
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", 
	                      required = true, dataType = "string", paramType = "header")})
	public ResponseEntity<List<SgcCiEmpresa>> obtenerEmpresaByActivo(@PathVariable Integer activo) {
		LOGGER.debug(ETIQ_CATALOGO_CONTROLLER.concat("obtenerEmpresaByActivo, activo: {}"), activo);
		List<SgcCiEmpresa> listSgcCiEmpresaByActivo = this.iSgcCiEmpresa.findAllByActivo(activo);
		if (listSgcCiEmpresaByActivo.isEmpty()) {
			LOGGER.error(ETIQ_CATALOGO_CONTROLLER.concat("obtenerEmpresaByActivo - NOT FOUND"));
			throw new DataNotFoundException("No encontrado: Empresa", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listSgcCiEmpresaByActivo, HttpStatus.OK);
	}
}
