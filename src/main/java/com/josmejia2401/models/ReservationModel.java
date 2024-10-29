package com.josmejia2401.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;


@Entity
@Table(name = "reservations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationModel {

	/**
	 * Identificador único de la reserva.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	/**
	 * Identificador del usuario que realiza la reserva.
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * Identificador del show asociado.
	 */
	@Column(name = "show_id")
	private Long showId;

	/**
	 *  Identificador de la función asociada.
	 */
	@Column(name = "function_id")
	private Long functionId;

	/**
	 * Fecha y hora de la reserva.
	 */
	@Column(name = "reservation_date")
	private Date reservationDate;

	/**
	 * (ENUM: 'confirmada', 'cancelada'): Estado de la reserva.
	 */
	@Column(name = "status")
	private Long status;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;
}
