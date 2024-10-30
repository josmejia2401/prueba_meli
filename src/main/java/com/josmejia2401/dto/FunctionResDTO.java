package com.josmejia2401.dto;

import lombok.*;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FunctionResDTO {
	private long id;
	private Long showId;
	private Date datetime;
	private Date createdAt;
}
