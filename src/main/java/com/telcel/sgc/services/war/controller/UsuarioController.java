package com.telcel.sgc.services.war.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telcel.sgc.services.war.model.ldap.Usuario;
import com.telcel.sgc.services.war.service.IUsuarioService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

/**
 * Usuario Controller
 */
@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	/**
	 * LOGGER.
	 */
	private static final Logger LOGGER = LogManager.getLogger();
	/**
	 * "UsuarioController - "
	 */
	private static final String ETIQ_USUARIO_CONTROLLER = "UsuarioController - ";

	/**
	 * Usuario Service
	 */
	@Autowired
	IUsuarioService iUsuarioService;
	
	@PostMapping
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", 
	                      required = true, dataType = "string", paramType = "header")})
	public ResponseEntity<Usuario> autenticar(HttpServletRequest request) {
		LOGGER.debug(ETIQ_USUARIO_CONTROLLER.concat("autenticar"));
		return  new ResponseEntity<>(this.iUsuarioService.autenticar(request), HttpStatus.OK);
	}
	
	/**
	 * Metodo para autenticar a un usuario
	 * 
	 * @param usuario
	 * @return
	 */
	/*@PostMapping
	public ResponseEntity<Boolean> autenticar(@RequestBody Usuario usuario) {
		LOGGER.debug(ETIQ_USUARIO_CONTROLLER.concat("autenticar, usuario: {}"), usuario.getUid());
		//LOGGER.debug(this.iOUsuarioService.autenticar(usuario));
		return new ResponseEntity<>(this.iUsuarioService.autenticar(usuario), HttpStatus.OK);
	}*/
	
	@GetMapping("validateToken")
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Bearer", value = "Authorization token", 
	                      required = true, dataType = "string", paramType = "header") })
	public ResponseEntity<Boolean> validarToken(HttpServletRequest request) {
		System.out.println("Validar Token:" + request);
		System.out.println("request.getAuthType():" + request.getAuthType());
		System.out.println("Request getheader authorization:" + request.getHeader("Authorization"));
		System.out.println("Request getheader bearer:" + request.getHeader("Bearer"));
		//String token = header.replace(jwtConfig.getPrefix(), "");
		request.getHeader("Bearer");
		
		
		
		return new ResponseEntity<>(true, HttpStatus.OK);
	}
}
