package com.josmejia2401.controller;

import com.josmejia2401.dto.ReservationReqDTO;
import com.josmejia2401.dto.ReservationResDTO;
import com.josmejia2401.dto.UserReqDTO;
import com.josmejia2401.exceptions.CustomErrorResponse;
import com.josmejia2401.exceptions.CustomException;
import com.josmejia2401.service.IReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/reservations")
@Log4j2
public class ReservationController {

	@Autowired
	private IReservationService iReservationService;

	@Operation(
			summary = "Recupera los elementos",
			description = "Método GET para recuperar los elementos por filtros de búsqueda",
			tags = { "reservations", "get" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ReservationResDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = CustomErrorResponse.class), mediaType = "application/json") }) })
	@GetMapping("")
	public ResponseEntity<List<ReservationResDTO>> getAll(@RequestParam(required = false) Long userId,
														  @RequestParam(required = false) Long showId,
														  @RequestParam(required = false) Long functionId) {
		try {
			List<ReservationResDTO> models = this.iReservationService.getAll(ReservationReqDTO
					.builder()
							.user(UserReqDTO
									.builder()
									.id(userId)
									.build())
							.showId(showId)
							.functionId(functionId)
					.build());
			if (models.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(models, HttpStatus.OK);
		} catch (CustomException e) {
			log.error(e);
			throw e;
		} catch (Exception e) {
			log.error(e);
			throw new CustomException(e);
		}
	}

	@Operation(
			summary = "Recupera un elemento por ID",
			description = "Método GET para recuperar un elemento por ID",
			tags = { "reservations", "get" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ReservationResDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = CustomErrorResponse.class), mediaType = "application/json") }) })
	@GetMapping("/{id}")
	public ResponseEntity<ReservationResDTO> getById(@PathVariable("id") long id) {
		try {
			ReservationResDTO data = this.iReservationService.getById(id);
			if (data != null) {
				return new ResponseEntity<>(data, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (CustomException e) {
			log.error(e);
			throw e;
		} catch (Exception e) {
			log.error(e);
			throw new CustomException(e);
		}
	}

	@Operation(
			summary = "Crea un elemento",
			description = "Método POST para crear un elemento",
			tags = { "reservations", "post" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ReservationResDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = CustomErrorResponse.class), mediaType = "application/json") }) })
	@PostMapping("")
	public ResponseEntity<ReservationResDTO> create(@Valid @RequestBody ReservationReqDTO req) {
		try {
			return new ResponseEntity<>(this.iReservationService.create(req), HttpStatus.CREATED);
		} catch (CustomException e) {
			log.error(e);
			throw e;
		} catch (Exception e) {
			log.error(e);
			throw new CustomException(e);
		}
	}

	@Operation(
			summary = "Actualizar un elemento por ID",
			description = "Método PUT para actualizar un elemento por ID",
			tags = { "reservations", "put" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ReservationResDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = CustomErrorResponse.class), mediaType = "application/json") }) })
	@PutMapping("/{id}")
	public ResponseEntity<ReservationResDTO> updateById(@PathVariable("id") long id, @Valid @RequestBody ReservationReqDTO req) {
		try {
			req.setId(id);
			return new ResponseEntity<ReservationResDTO>(this.iReservationService.update(req), HttpStatus.OK);
		} catch (CustomException e) {
			log.error(e);
			throw e;
		} catch (Exception e) {
			log.error(e);
			throw new CustomException(e);
		}
	}

	@Operation(
			summary = "Eliminar un elemento por ID",
			description = "Método DELETE para eliminar un elemento por ID",
			tags = { "reservations", "delete" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = CustomErrorResponse.class), mediaType = "application/json") }) })
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") long id) {
		try {
			this.iReservationService.deleteById(id);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (CustomException e) {
			log.error(e);
			throw e;
		} catch (Exception e) {
			log.error(e);
			throw new CustomException(e);
		}
	}
}
