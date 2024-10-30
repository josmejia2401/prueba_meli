package com.josmejia2401.dto;

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
public class SectionReqDTO {
	private long id;
	@NotNull(message = "El ID del lugar es requerido.")
	private Long placeId;
	@NotNull(message = "El nombre es requerido.")
	@NotNull(message = "El nombre no puede ser vacío.")
	private String name;
	@NotNull(message = "El precio es requerido.")
	private Double price;
	private Date createdAt;

	private List<SeatReqDTO> seats;
}
