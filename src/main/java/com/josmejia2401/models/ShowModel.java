package com.josmejia2401.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;


@Entity
@Table(name = "shows")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowModel {

	/**
	 *  Identificador único del show.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	/**
	 * Nombre del show.
	 */
	@Column(name = "name")
	private String name;

	/**
	 * Descripción del show.
	 */
	@Column(name = "description")
	private String description;

	/**
	 *  Fecha y hora de inicio del show.
	 */
	@Column(name = "start_date")
	private Date startDate;

	/**
	 * Fecha y hora de finalización del show.
	 */
	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "place_id")
	private Long place_id;

	@Column(name = "createdAt")
	@CreationTimestamp
	private Date createdAt;
}
