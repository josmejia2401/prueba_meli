package com.josmejia2401.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;


@Entity
@Table(name = "seats")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatModel {

	/**
	 * Identificador único de la butaca.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	/**
	 *  Identificador de la sección a la que pertenece la butaca.
	 */
	@Column(name = "section_id")
	private Long sectionId;

	/**
	 * Número de la butaca (puede ser NULL si no aplica).
	 */
	@Column(name = "number")
	private String number;

	/**
	 * Estado de disponibilidad (true = disponible, false = reservado).
	 */
	@Column(name = "availability")
	private Boolean availability;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;
}
