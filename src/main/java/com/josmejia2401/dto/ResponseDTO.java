package com.josmejia2401.dto;

import lombok.*;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDTO<T> {
	private int code;
	private String message;
	private T data;
}
