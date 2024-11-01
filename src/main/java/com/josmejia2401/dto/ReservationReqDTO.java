package com.josmejia2401.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationReqDTO {
	private long id;
	@NotNull(message = "El usuario es requerido.")
	private UserReqDTO user;
	@NotNull(message = "El ID del Show es requerido.")
	private Long showId;
	@NotNull(message = "El ID de la Función es requerido.")
	private Long functionId;
	@NotNull(message = "La Fecha de reserva es requerido.")
	private Date reservationDate;
	@NotNull(message = "El ID de la butaca es requerido.")
	private Long seatId;
	private String status;
	private Date createdAt;
}
