package com.josmejia2401.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;


@Entity
@Table(name = "functions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FunctionModel {

	/**
	 * Identificador único de la función.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	/**
	 *  Identificador del show asociado.
	 */
	@Column(name = "show_id")
	private Long showId;

	/**
	 * Fecha y hora de la función.
	 */
	@Column(name = "datetime")
	private Date datetime;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;

}
