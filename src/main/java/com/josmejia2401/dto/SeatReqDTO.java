package com.josmejia2401.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatReqDTO {
	private long id;
	@NotNull(message = "El ID de la sección es requerido.")
	private Long sectionId;
	@NotNull(message = "El Número del asiento es requerido.")
	@NotBlank(message = "El Número del asiento no puede ser vacío.")
	private String number;
	@Builder.Default
	private boolean availability = true;
	private Date createdAt;
}
