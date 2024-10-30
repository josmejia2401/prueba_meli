package com.josmejia2401.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.List;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceReqDTO {
	private long id;
	@NotNull(message = "El nombre es requerido.")
	@NotBlank(message = "El nombre no puede ser vacío.")
	private String name;
	@NotNull(message = "La dirección es requerido.")
	@NotBlank(message = "La dirección no puede ser vacío.")
	private String address;
	@NotNull(message = "La capacidad es requerido.")
	private Long capacity;
	private Date createdAt;

	private List<SectionReqDTO> sections;
}
