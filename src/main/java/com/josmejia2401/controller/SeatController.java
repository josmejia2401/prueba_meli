package com.josmejia2401.controller;

import com.josmejia2401.dto.SeatReqDTO;
import com.josmejia2401.dto.SeatResDTO;
import com.josmejia2401.exceptions.CustomException;
import com.josmejia2401.service.ISeatService;
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
