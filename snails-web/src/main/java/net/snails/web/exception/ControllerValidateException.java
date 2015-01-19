package net.snails.web.exception;

/**
 * @author krisjin
 * @date 2014-10-24
 */
public class ControllerValidateException extends Exception {

	private static final long serialVersionUID = 1L;

	public ControllerValidateException(String msg) {
		super(msg);
	}
}
