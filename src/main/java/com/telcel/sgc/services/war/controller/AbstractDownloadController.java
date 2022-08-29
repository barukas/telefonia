package com.telcel.sgc.services.war.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.telcel.sgc.services.war.service.IFileDownloadResourceService;

/**
 * Abstract de controller de descarga de archivos.
 */
public abstract class AbstractDownloadController implements IFileDownloadResourceService {
	/**
	 * "No se puede determinar el tipo de archivo.".
	 */
	private static final String MSG_ERR01 = "No se puede determinar el tipo de archivo.";
	/**
	 * "attachment; filename=\"%1$s\"".
	 */
	private static final String ATTACHMENT = "attachment; filename=\"%1$s\"";

	/**
	 * @see com.telcel.sgc.services.war.service.IFileDownloadResourceService#downloadFile(org.springframework.core.io.Resource,
	 *      javax.servlet.http.HttpServletRequest, org.apache.logging.log4j.Logger)
	 */
	@Override
	public ResponseEntity<Resource> downloadFile(Resource resource, HttpServletRequest request, final Logger logger) {
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			logger.error(ex.getMessage(), ex);
			logger.info(MSG_ERR01);
		}
		if (contentType == null) {
			contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, String.format(ATTACHMENT, resource.getFilename()))
				.body(resource);
	}
}
