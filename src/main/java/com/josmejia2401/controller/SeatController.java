package com.josmejia2401.controller;

import com.josmejia2401.dto.PlaceResDTO;
import com.josmejia2401.dto.SeatReqDTO;
import com.josmejia2401.dto.SeatResDTO;
import com.josmejia2401.exceptions.CustomErrorResponse;
import com.josmejia2401.exceptions.CustomException;
import com.josmejia2401.service.ISeatService;
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
@RequestMapping("/api/v1/seats")
@Log4j2
public class SeatController {

	@Autowired
	private ISeatService iSeatService;

	@Operation(
			summary = "Recupera los elementos",
			description = "Método GET para recuperar los elementos por filtros de búsqueda",
			tags = { "seats", "get" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = PlaceResDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = CustomErrorResponse.class), mediaType = "application/json") }) })
	@GetMapping("")
	public ResponseEntity<List<SeatResDTO>> getAll() {
		try {
			List<SeatResDTO> models = this.iSeatService.getAll(SeatReqDTO
					.builder()
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
			tags = { "seat", "get" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = SeatResDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = CustomErrorResponse.class), mediaType = "application/json") }) })
	@GetMapping("/{id}")
	public ResponseEntity<SeatResDTO> getById(@PathVariable("id") long id) {
		try {
			SeatResDTO data = this.iSeatService.getById(id);
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
			tags = { "seat", "post" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = SeatResDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = CustomErrorResponse.class), mediaType = "application/json") }) })
	@PostMapping("")
	public ResponseEntity<SeatResDTO> create(@Valid @RequestBody SeatReqDTO req) {
		try {
			return new ResponseEntity<>(this.iSeatService.create(req), HttpStatus.CREATED);
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
			tags = { "seat", "put" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = SeatResDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = CustomErrorResponse.class), mediaType = "application/json") }) })
	@PutMapping("/{id}")
	public ResponseEntity<SeatResDTO> updateById(@PathVariable("id") long id, @Valid @RequestBody SeatReqDTO req) {
		try {
			req.setId(id);
			return new ResponseEntity<SeatResDTO>(this.iSeatService.update(req), HttpStatus.OK);
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
			tags = { "seat", "delete" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = CustomErrorResponse.class), mediaType = "application/json") }) })
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") long id) {
		try {
			this.iSeatService.deleteById(id);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (CustomException e) {
			log.error(e);
			throw e;
		} catch (Exception e) {
			log.error(e);
			throw new CustomException(e);
		}
	}

	@Operation(
			summary = "Obtener butacas disponibles",
			description = "Método GET para obtener butacas disponibles",
			tags = { "seat", "put" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = SeatResDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = CustomErrorResponse.class), mediaType = "application/json") }) })
	@GetMapping("/available/{showId}/{functionId}")
	public ResponseEntity<List<SeatResDTO>> getById(@PathVariable("showId") long showId, @PathVariable("functionId") long functionId) {
		try {
			List<SeatResDTO> data = this.iSeatService.getAll(SeatReqDTO
					.builder()
							.showId(showId)
							.functionId(functionId)
					.build());
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
}
