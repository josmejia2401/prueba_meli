package com.josmejia2401.controller;

import com.josmejia2401.dto.ShowDTO;
import com.josmejia2401.dto.ShowReqDTO;
import com.josmejia2401.exceptions.CustomException;
import com.josmejia2401.service.IShowService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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


	/**
	 *  Método que se encarga de buscar los show por filtros de búsquedas.
	 *  Ejemplo:
	 *
	 *  /shows?fechaInicio=2024-01-01&fechaFin=2024-12-31&precioMin=1000&precioMax=3000&orden=asc
	 * @param startDate Fecha inicial
	 * @param endDate Fecha final
	 * @param minPrice Costo minímo
	 * @param maxPrice Costo máximo
	 * @param order asc | desc
	 * @return Listado de shows
	 */
	@GetMapping("")
	public ResponseEntity<List<ShowDTO>> getAll(@RequestParam(required = false) Date startDate,
												@RequestParam(required = false) Date endDate,
												@RequestParam(required = false) Double minPrice,
												@RequestParam(required = false) Double maxPrice,
												@RequestParam(required = false) String order) {
		try {
			List<ShowDTO> models = this.iShowService.getAll(ShowReqDTO
					.builder()
							.startDate(startDate)
							.endDate(endDate)
							.minPrice(minPrice)
							.maxPrice(maxPrice)
							.order(order)
					.build());
			if (models.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(models, HttpStatus.OK);
		} catch (Exception e) {
			log.error("ShowController.getAll", e);
			throw new CustomException("Internal error", e);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<ShowDTO> getById(@PathVariable("id") long id) {
		try {
			ShowDTO data = this.iShowService.getById(id);
			if (data != null) {
				return new ResponseEntity<>(data, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error("TaskController.getTasklById", e);
			throw new CustomException("Internal error", e);
		}
	}

	@PostMapping("/")
	public ResponseEntity<ShowDTO> create(@Valid @RequestBody ShowDTO task) {
		try {
			return new ResponseEntity<>(this.iShowService.create(task), HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("TaskController.createTask", e);
			throw new CustomException("Internal error", e);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<ShowDTO> updateById(@PathVariable("id") long id, @Valid @RequestBody ShowDTO task) {
		try {
			ShowDTO data = this.iShowService.getById(id);
			if (data != null) {
				task.setId(id);
				this.iShowService.update(task);
				task.setId(id);
				return new ResponseEntity<ShowDTO>(task, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error("TaskController.updateTask", e);
			throw new CustomException("Internal error", e);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") long id) {
		try {
			ShowDTO data = this.iShowService.getById(id);
			if (data != null) {
				this.iShowService.deleteById(id);
				return new ResponseEntity<>(null, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error("TaskController.deleteTask", e);
			throw new CustomException("Internal error", e);
		}
	}
}
