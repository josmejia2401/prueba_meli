package com.josmejia2401.controller;

import com.josmejia2401.dto.PlaceReqDTO;
import com.josmejia2401.dto.PlaceResDTO;
import com.josmejia2401.exceptions.CustomErrorResponse;
import com.josmejia2401.exceptions.CustomException;
import com.josmejia2401.service.IPlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/places")
@Log4j2
@Tag(name = "Sitios", description = "APIs para sitios donde se realizan los shows")
public class PlaceController {

	@Autowired
	private IPlaceService iPlaceService;

	@Operation(
			summary = "Recupera los elementos",
			description = "Método GET para recuperar los elementos por filtros de búsqueda",
			tags = { "sitios", "get" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = PlaceResDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = CustomErrorResponse.class), mediaType = "application/json") }) })
	@GetMapping("")
	public ResponseEntity<List<PlaceResDTO>> getAll(@RequestParam(required = false) String name,
													@RequestParam(required = false) String address,
													@RequestParam(required = false) Long capacity) {
		try {
			List<PlaceResDTO> models = this.iPlaceService.getAll(PlaceReqDTO
					.builder()
							.name(name)
							.address(address)
							.capacity(capacity)
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
			tags = { "sitios", "get" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = PlaceResDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = CustomErrorResponse.class), mediaType = "application/json") }) })
	@GetMapping("/{id}")
	public ResponseEntity<PlaceResDTO> getById(@PathVariable("id") long id) {
		try {
			PlaceResDTO data = this.iPlaceService.getById(id);
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
			tags = { "sitios", "post" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = PlaceResDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = CustomErrorResponse.class), mediaType = "application/json") }) })
	@PostMapping("")
	public ResponseEntity<PlaceResDTO> create(@Valid @RequestBody PlaceReqDTO req) {
		try {
			return new ResponseEntity<>(this.iPlaceService.create(req), HttpStatus.CREATED);
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
			tags = { "sitios", "put" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = PlaceResDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = CustomErrorResponse.class), mediaType = "application/json") }) })
	@PutMapping("/{id}")
	public ResponseEntity<PlaceResDTO> updateById(@PathVariable("id") long id, @Valid @RequestBody PlaceReqDTO req) {
		try {
			req.setId(id);
			return new ResponseEntity<PlaceResDTO>(this.iPlaceService.update(req), HttpStatus.OK);
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
			tags = { "sitios", "delete" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = PlaceResDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = CustomErrorResponse.class), mediaType = "application/json") }) })
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") long id) {
		try {
			this.iPlaceService.deleteById(id);
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
