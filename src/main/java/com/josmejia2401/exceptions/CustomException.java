package com.josmejia2401.exceptions;

public class CustomException extends RuntimeException {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable e) {
        super(message, e);
    }
    
    public CustomException(Throwable e) {
        super(e);
    }
}