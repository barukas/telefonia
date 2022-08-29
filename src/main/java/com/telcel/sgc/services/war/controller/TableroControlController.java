package com.telcel.sgc.services.war.controller;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telcel.sgc.services.war.service.ITableroControlService;

/**
 * Tablero Control Controller
 */
@RestController
@RequestMapping("/tablero")
public class TableroControlController extends AbstractDownloadController{
	/**
	 * LOGGER.
	 */
	private static final Logger LOGGER = LogManager.getLogger(SolicitudController.class);
	/**
	 * Etiqueta "pageSize"
	 */
	private static final String ETIQUETA_PAGESIZE = "PAGE_SIZE";
	/**
	 * Etiqueta "pageNumber"
	 */
	private static final String ETIQUETA_PAGENUMBER = "PAGE_NUMBER";
	/**
	 * Tablero Control Service.
	 */
	@Autowired
	ITableroControlService iTableroControlService;
	
	/**
	 * Metodo para obtener las solicitudes.
	 * 
	 * @param busqueda
	 * @return
	 */
	@PostMapping("/solicitudes")
	public String obtenerSolicitudes(@RequestBody Map<String, Object> busqueda) {
		LOGGER.debug("TableroControlController - obtenerSolicitudes:" + busqueda.get("USER_ALTA"));
		LOGGER.debug(busqueda.get(ETIQUETA_PAGENUMBER)+":"+busqueda.get(ETIQUETA_PAGESIZE));
		LOGGER.debug("mapParms = [{}]", ToStringBuilder.reflectionToString(busqueda, ToStringStyle.JSON_STYLE));
		
		JSONObject json = new JSONObject();
		json.put("data", new JSONArray(iTableroControlService.obtenerSolicitudes(busqueda).toString()));
		
		JSONObject itemPage = new JSONObject();
		Integer totalSize = Integer.parseInt(iTableroControlService.contarSolicitudes(busqueda));
		Integer totalPages = totalSize/Integer.parseInt(busqueda.get(ETIQUETA_PAGESIZE).toString());
		itemPage.put("pageNumber", busqueda.get(ETIQUETA_PAGENUMBER).toString());
		itemPage.put("pageSize", busqueda.get(ETIQUETA_PAGESIZE).toString());
		itemPage.put("totalPages", totalPages);
		itemPage.put("totalSize", totalSize);
		
		json.put("pageResult", itemPage);
		return json.toString() ;
	}
	
	/**
	 * Metodo para obtener la informacion de la tarea para el tablero de control.
	 * 
	 * @param processInstanceId
	 * @return
	 */
	@GetMapping("/bpm/{processInstanceId}")
	public String obtenerTareaInfo(@PathVariable String processInstanceId) {
		return iTableroControlService.obtenerTareaInfo(processInstanceId).toString();
	}
}
