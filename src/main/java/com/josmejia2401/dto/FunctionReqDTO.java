package com.josmejia2401.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class FunctionReqDTO {
	private long id;
	@NotNull(message = "El ID del show es requerido.")
	private Long showId;
	@NotNull(message = "La fecha es requerido.")
	private Date datetime;
	private Date createdAt;
}
