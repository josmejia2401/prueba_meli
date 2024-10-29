package com.josmejia2401.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;


@Entity
@Table(name = "places")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceModel {

	/**
	 *  Identificador único del lugar.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	/**
	 * Nombre del lugar (teatro, estadio, campo, etc.).
	 */
	@Column(name = "name")
	private String name;

	/**
	 * Dirección del lugar.
	 */
	@Column(name = "address")
	private String address;

	/**
	 * Capacidad máxima del lugar.
	 */
	@Column(name = "capacity")
	private Long capacity;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;
}
