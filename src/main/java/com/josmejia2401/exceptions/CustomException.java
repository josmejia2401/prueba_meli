package com.josmejia2401.exceptions;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private int status = 500;

	public CustomException(String message) {
        super(message);
    }

    public CustomException(int status, String message) {
        super(message);
        this.status = status;
    }

    public CustomException(String message, Throwable e) {
        super(message, e);
    }
    
    public CustomException(Throwable e) {
        super(e);
    }



}