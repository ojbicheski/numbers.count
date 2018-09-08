/**
 * Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package numbers.count.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author orlei
 *
 */
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
@SuppressWarnings("serial")
public class SystemUnavailableException extends RuntimeException {

	public SystemUnavailableException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SystemUnavailableException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SystemUnavailableException(String message, Throwable cause) {
		super(message, cause);
	}

	public SystemUnavailableException(String message) {
		super(message);
	}

	public SystemUnavailableException(Throwable cause) {
		super(cause);
	}

}
