package com.telcel.sgc.services.war.controller;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telcel.sgc.services.war.exceptions.DataNotFoundException;
import com.telcel.sgc.services.war.service.IBandejaClientService;

/**
 * Bandeja Controller
 */
@RestController
@RequestMapping("/bandeja")
public class BandejaController extends AbstractDownloadController {
	/**
	 * LOGGER.
	 */
	private static final Logger LOGGER = LogManager.getLogger(SolicitudController.class);
	/**
	 * BPM Client Service.
	 */
	@Autowired
    private IBandejaClientService bandejaClientService;

	/**
	 * Metodo para obtener actividades del usuario.
	 * 
	 * @return String idEmpresa
	 * 
	 * @throws DataNotFoundException
	 */
	@PostMapping("/pendientes/asignadas")
	public String obtenerTareasAsignadas(@RequestBody Map<String, Object> busqueda) {
		LOGGER.debug("BandejaController - obtenerTareasAsignadas:" + busqueda.get("UserName"));
		return bandejaClientService.getAssignedTasks(busqueda);
	}
	
	/**
	 * Metodo para obtener actividades del usuario.
	 * 
	 * @return String idEmpresa
	 * 
	 * @throws DataNotFoundException
	 */
	@PostMapping("/pendientes/disponibles")
	public String obtenerTareasDisponibles(@RequestBody Map<String, Object> busqueda) {
		LOGGER.debug("BandejaController - obtenerTareasdisponibles:" + busqueda.get("UserName"));
		return bandejaClientService.getAvailableTasks(busqueda);
	}
	
	/**
	 * 
	 * @param idTarea
	 * @param deploymentId
	 * @param userName
	 * @return
	 */
	@PutMapping("/asignar/{idTarea}/{deploymentId}/{userName}")
	public boolean assignTask(
			@PathVariable Integer idTarea,
			@PathVariable String deploymentId,
			@PathVariable String userName) {
		return bandejaClientService.assignTask(idTarea, userName, deploymentId);
	}
	
	/**
	 * 
	 * @param idTarea
	 * @param deploymentId
	 * @param userName
	 * @return
	 */
	@PutMapping("/liberar/{idTarea}/{deploymentId}/{userName}")
	public boolean releaseTask(
			@PathVariable Integer idTarea,
			@PathVariable String deploymentId,
			@PathVariable String userName) {
		return bandejaClientService.releaseTask(idTarea, userName, deploymentId);
	}
	
	/**
	 * 
	 * @param idTarea
	 * @param deploymentId
	 * @param userName
	 * @param targetUserName
	 * @return
	 */
	@PutMapping("/delegar/{idTarea}/{deploymentId}/{userName}/{targetUserName}")
	public boolean delegateTask(
			@PathVariable Integer idTarea,
			@PathVariable String deploymentId,
			@PathVariable String userName,
			@PathVariable String targetUserName) {
		return bandejaClientService.delegateTask(idTarea, userName, targetUserName, deploymentId);
	}
}
