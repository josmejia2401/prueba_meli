package com.josmejia2401.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;


@Entity
@Table(name = "reserve_seat")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReserveSeatModel {

	/**
	 *  Identificador único de la relación.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	/**
	 * Identificador de la reserva.
	 */
	@Column(name = "reservation_id")
	private Long reservationId;

	/**
	 * Identificador de la reserva.
	 */
	@Column(name = "seat_id")
	private Long seatId;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;
}
