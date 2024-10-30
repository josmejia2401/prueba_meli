package com.josmejia2401.controller;

import com.josmejia2401.dto.SectionReqDTO;
import com.josmejia2401.dto.SectionResDTO;
import com.josmejia2401.exceptions.CustomException;
import com.josmejia2401.service.ISectionService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/sections")
@Log4j2
public class SectionController {

	@Autowired
	private ISectionService iSectionService;

	@GetMapping("")
	public ResponseEntity<List<SectionResDTO>> getAll(@RequestParam(required = false) String name) {
		try {
			List<SectionResDTO> models = this.iSectionService.getAll(SectionReqDTO
					.builder()
							.name(name)
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
	public ResponseEntity<SectionResDTO> getById(@PathVariable("id") long id) {
		try {
			SectionResDTO data = this.iSectionService.getById(id);
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
	public ResponseEntity<SectionResDTO> create(@Valid @RequestBody SectionReqDTO req) {
		try {
			return new ResponseEntity<>(this.iSectionService.create(req), HttpStatus.CREATED);
		} catch (CustomException e) {
			log.error(e);
			throw e;
		} catch (Exception e) {
			log.error(e);
			throw new CustomException(e);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<SectionResDTO> updateById(@PathVariable("id") long id, @Valid @RequestBody SectionReqDTO req) {
		try {
			req.setId(id);
			return new ResponseEntity<SectionResDTO>(this.iSectionService.update(req), HttpStatus.OK);
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
			this.iSectionService.deleteById(id);
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
