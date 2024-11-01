package com.josmejia2401.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class UserReqDTO {
	private Long id;
	@NotNull(message = "El Nombre es requerido")
	@NotBlank(message = "El Nombre no puede ser vacío")
	private String name;
	@NotNull(message = "El DNI es requerido")
	@NotBlank(message = "El DNI no puede ser vacío")
	private String dni;
	@NotNull(message = "El Email es requerido")
	@Email(message = "El Email es invalido")
	@NotBlank(message = "El Email no puede ser vacío")
	private String email;
	@NotNull(message = "El Rol es requerido")
	@NotBlank(message = "El Rol no puede ser vacío")
	private String role;
	private Date createdAt;
}
