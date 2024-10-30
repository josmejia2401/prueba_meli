package com.josmejia2401.controller;

import com.josmejia2401.dto.ShowFilterReqDTO;
import com.josmejia2401.dto.ShowReqDTO;
import com.josmejia2401.dto.ShowResDTO;
import com.josmejia2401.exceptions.CustomException;
import com.josmejia2401.service.IShowService;
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
