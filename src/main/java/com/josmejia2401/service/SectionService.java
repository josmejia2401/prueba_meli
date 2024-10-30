package com.josmejia2401.service;

import com.josmejia2401.dto.SeatReqDTO;
import com.josmejia2401.dto.SectionReqDTO;
import com.josmejia2401.dto.SectionResDTO;
import com.josmejia2401.exceptions.CustomException;
import com.josmejia2401.models.SectionModel;
import com.josmejia2401.repository.SectionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class SectionService implements ISectionService {

	@Autowired
	private EntityManager em;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private SectionRepository sectionRepository;
	@Autowired
	private SeatService seatService;

	@Override
	public List<SectionResDTO> getAll(SectionReqDTO req) {
		StringBuilder sql = new StringBuilder("SELECT * FROM sections");
		List<String> parameters = new ArrayList<>();
		if (req.getName() != null) {
			parameters.add(String.format("name = %s", req.getName()));
		}
		if (req.getPlaceId() != null) {
			parameters.add(String.format("place_id = %s", req.getPlaceId()));
		}
		if (!parameters.isEmpty()) {
			sql.append(" WHERE ");
			sql.append(String.join(" AND ", parameters));
		}
		Query query = em.createNativeQuery(sql.toString(), SectionModel.class);
		List<?> results = query.getResultList();
		Type listType = new TypeToken<List<SectionResDTO>>(){}.getType();
		return modelMapper.map(results, listType);
	}

	@Override
	public SectionResDTO getById(Long id) {
		SectionModel model = this.sectionRepository.findById(id).orElseThrow(() -> new CustomException(404, "Elemento no existe."));
		SectionResDTO response = modelMapper.map(model, SectionResDTO.class);
		response.setSeats(this.seatService.getAll(SeatReqDTO
				.builder()
						.sectionId(model.getId())
				.build()));
		return response;
	}

	@Override
	public void deleteById(Long id) {
		SectionResDTO model = this.getById(id);
		if (model.getSeats() != null && !model.getSeats().isEmpty()) {
			model.getSeats().forEach(p -> seatService.deleteById(p.getId()));
		}
		this.sectionRepository.deleteById(model.getId());
	}

	@Override
	public SectionResDTO update(SectionReqDTO req) {
		SectionResDTO data = this.getById(req.getId());
		SectionModel model = modelMapper.map(req, SectionModel.class);
		model.setCreatedAt(data.getCreatedAt());
		this.sectionRepository.saveAndFlush(model);
		return modelMapper.map(model, SectionResDTO.class);
	}

	@Override
	public SectionResDTO create(SectionReqDTO req) {
		SectionModel model = modelMapper.map(req, SectionModel.class);
		this.sectionRepository.saveAndFlush(model);
		SectionResDTO response = modelMapper.map(model, SectionResDTO.class);
		if (req.getSeats() != null && !req.getSeats().isEmpty()) {
			req.getSeats().forEach(p -> p.setSectionId(response.getId()));
			response.setSeats(req.getSeats().stream().map(p -> this.seatService.create(p)).toList());
		}
		return response;
	}
}
