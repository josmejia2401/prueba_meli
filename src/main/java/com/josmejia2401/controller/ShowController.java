package com.josmejia2401.controller;

import com.josmejia2401.dto.*;
import com.josmejia2401.exceptions.CustomErrorResponse;
import com.josmejia2401.exceptions.CustomException;
import com.josmejia2401.service.IShowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/shows")
@Log4j2
public class ShowController {

	@Autowired
	private IShowService iShowService;

	@Operation(
			summary = "Recupera los elementos",
			description = "Método GET para recuperar los elementos por filtros de búsqueda",
			tags = { "shows", "get" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = PlaceResDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = CustomErrorResponse.class), mediaType = "application/json") }) })
	@GetMapping("")
	public ResponseEntity<List<ShowResDTO>> getAll(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  Date startDate,
													@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date endDate,
													@RequestParam(required = false) Double maxPrice,
													@RequestParam(required = false) Double minPrice,
													@RequestParam(required = false) String order) {
		try {
			List<ShowResDTO> models = this.iShowService.getAll(ShowFilterReqDTO
					.builder()
							.startDate(startDate)
							.endDate(endDate)
							.maxPrice(maxPrice)
							.minPrice(minPrice)
							.order(order)
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
			tags = { "show", "get" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ShowResDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = CustomErrorResponse.class), mediaType = "application/json") }) })
	@GetMapping("/{id}")
	public ResponseEntity<ShowResDTO> getById(@PathVariable("id") long id) {
		try {
			ShowResDTO data = this.iShowService.getById(id);
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
			tags = { "show", "post" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ShowResDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = CustomErrorResponse.class), mediaType = "application/json") }) })
	@PostMapping("")
	public ResponseEntity<ShowResDTO> create(@Valid @RequestBody ShowReqDTO req) {
		try {
			return new ResponseEntity<>(this.iShowService.create(req), HttpStatus.CREATED);
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
			tags = { "show", "put" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ShowResDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = CustomErrorResponse.class), mediaType = "application/json") }) })
	@PutMapping("/{id}")
	public ResponseEntity<ShowResDTO> updateById(@PathVariable("id") long id, @Valid @RequestBody ShowReqDTO req) {
		try {
			req.setId(id);
			return new ResponseEntity<ShowResDTO>(this.iShowService.update(req), HttpStatus.OK);
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
			tags = { "show", "delete" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = CustomErrorResponse.class), mediaType = "application/json") }) })
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") long id) {
		try {
			this.iShowService.deleteById(id);
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
