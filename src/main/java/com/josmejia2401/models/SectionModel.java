package com.josmejia2401.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;


@Entity
@Table(name = "sections")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SectionModel {

	/**
	 *  Identificador único de la sección.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	/**
	 * Identificador del lugar donde se encuentra la sección.
	 */
	@Column(name = "place_id")
	private Long placeId;

	/**
	 *  Nombre de la sección (e.g., "Platea", "General").
	 */
	@Column(name = "name")
	private String name;

	/**
	 * Precio de las entradas para esta sección.
	 */
	@Column(name = "price")
	private Double price;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;
}
