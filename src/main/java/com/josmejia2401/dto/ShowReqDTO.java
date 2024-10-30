package com.josmejia2401.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowReqDTO {
	private long id;

	@NotNull(message = "El nombre es requerido.")
	@NotBlank(message = "El nombre no puede ser vacío.")
	private String name;
	@NotNull(message = "La descripción es requerido.")
	@NotBlank(message = "La descripción no puede ser vacío.")
	private String description;
	@NotNull(message = "La fecha inicial es requerido.")
	private Date startDate;
	@NotNull(message = "La fecha final es requerido.")
	private Date endDate;
	@NotNull(message = "El ID del sitio es requerido.")
	private Long placeId;

	private Date createdAt;

	@NotNull(message = "Las funciones son requeridas.")
	@Size(min = 1, message = "Se requiere miínimo una función.")
	private List<FunctionReqDTO> functions;
}
