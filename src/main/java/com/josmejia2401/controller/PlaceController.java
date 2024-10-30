package com.josmejia2401.controller;

import com.josmejia2401.dto.PlaceReqDTO;
import com.josmejia2401.dto.PlaceResDTO;
import com.josmejia2401.exceptions.CustomException;
import com.josmejia2401.service.IPlaceService;
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
public class PlaceController {

	@Autowired
	private IPlaceService iPlaceService;

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

	@PutMapping("/{id}")
	public ResponseEntity<PlaceResDTO> updateById(@PathVariable("id") long id, @Valid @RequestBody PlaceReqDTO req) {
		try {
			PlaceResDTO data = this.iPlaceService.getById(id);
			if (data != null) {
				req.setId(id);
				this.iPlaceService.update(req);
				return new ResponseEntity<PlaceResDTO>(data, HttpStatus.OK);
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

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") long id) {
		try {
			PlaceResDTO data = this.iPlaceService.getById(id);
			if (data != null) {
				this.iPlaceService.deleteById(id);
				return new ResponseEntity<>(null, HttpStatus.OK);
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
