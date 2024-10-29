package com.josmejia2401.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;


@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserModel {

	/**
	 *  Identificador único del usuario.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	/**
	 * Nombre del usuario.
	 */
	@Column(name = "name")
	private String name;

	/**
	 * DNI del usuario
	 */
	@Column(name = "dni")
	private String dni;

	/**
	 * Correo electrónico del usuario.
	 */
	@Column(name = "email")
	private String email;

	/**
	 *  (ENUM: 'admin', 'cliente'): Rol del usuario (quien carga información o realiza reservas).
	 */
	@Column(name = "role")
	private String role;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;
}
