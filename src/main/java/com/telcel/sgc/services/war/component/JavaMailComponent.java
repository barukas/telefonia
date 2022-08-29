/**
 * 
 */
package com.telcel.sgc.services.war.component;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import com.telcel.sgc.services.war.service.ISgcCiParametro;

/**
 * Clase que configura el componente de envio de correo electronico.
 *
 */
@Component
@DependsOn({ "sgcCiParametroService" })
public class JavaMailComponent {
	/**
	 * "mail.host"
	 */
	private static final String MAIL_HOST = "mail.host";
	/**
	 * "mail.port"
	 */
	private static final String MAIL_PORT = "mail.port";
	/**
	 * "mail.username"
	 */
	private static final String MAIL_USERNAME = "mail.username";
	/**
	 * "mail.password"
	 */
	private static final String MAIL_PASS = "mail.password";
	/**
	 * "mail.transport.protocol"
	 */
	private static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
	/**
	 * "mail.smtp.auth"
	 */
	private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
	/**
	 * "mail.smtp.starttls.enable"
	 */
	private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
	/**
	 * "mail.debug"
	 */
	private static final String MAIL_DEBUG = "mail.debug";
	/**
	 * mailSender
	 */
	private JavaMailSenderImpl mailSender;

	/**
	 * ISgcCiParametro.
	 */
	@Autowired
	private ISgcCiParametro sgcCiParametro;

	/**
	 * Constructor.
	 */
	public JavaMailComponent() {
		super();
	}

	/**
	 * Metodo que inicializa el componente de envío de correo.
	 */
	@PostConstruct
	public void init() {
		this.mailSender = new JavaMailSenderImpl();
		this.mailSender.setHost(this.sgcCiParametro.findTextoParametroByLlave(MAIL_HOST, StringUtils.EMPTY));
		this.mailSender.setPort(this.sgcCiParametro.findLongParametroByLlave(MAIL_PORT, 0L).intValue());

		this.mailSender.setUsername(this.sgcCiParametro.findTextoParametroByLlave(MAIL_USERNAME, StringUtils.EMPTY));
		this.mailSender.setPassword(this.sgcCiParametro.findTextoParametroByLlave(MAIL_PASS, StringUtils.EMPTY));

		final Properties props = mailSender.getJavaMailProperties();
		props.put(MAIL_TRANSPORT_PROTOCOL,
				this.sgcCiParametro.findTextoParametroByLlave(MAIL_TRANSPORT_PROTOCOL, StringUtils.EMPTY));
		props.put(MAIL_SMTP_AUTH, this.sgcCiParametro.findTextoParametroByLlave(MAIL_SMTP_AUTH, StringUtils.EMPTY));
		props.put(MAIL_SMTP_STARTTLS_ENABLE,
				this.sgcCiParametro.findTextoParametroByLlave(MAIL_SMTP_STARTTLS_ENABLE, StringUtils.EMPTY));
		props.put(MAIL_DEBUG, this.sgcCiParametro.findTextoParametroByLlave(MAIL_DEBUG, StringUtils.EMPTY));
	}

	/**
	 * @return the mailSender
	 */
	public final JavaMailSenderImpl getMailSender() {
		return mailSender;
	}

	/**
	 * @param mailSender the mailSender to set
	 */
	public final void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}
}
