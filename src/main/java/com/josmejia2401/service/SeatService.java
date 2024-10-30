package com.josmejia2401.service;

import com.josmejia2401.dto.SeatReqDTO;
import com.josmejia2401.dto.SeatResDTO;
import com.josmejia2401.exceptions.CustomException;
import com.josmejia2401.models.SeatModel;
import com.josmejia2401.repository.SeatRepository;
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
public class SeatService implements ISeatService {

	@Autowired
	private EntityManager em;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private SeatRepository seatRepository;


	@Override
	public List<SeatResDTO> getAll(SeatReqDTO req) {
		StringBuilder sql = new StringBuilder("SELECT * FROM seats");
		List<String> parameters = new ArrayList<>();
		if (req.getShowId() != null) {
			parameters.add(String.format("section_id IN (SELECT id FROM sections WHERE place_id = (SELECT place_id FROM shows WHERE id = %s))", req.getShowId()));
		}
		if (req.getFunctionId() != null) {
			parameters.add(String.format("section_id IN (SELECT id FROM sections WHERE place_id = (SELECT place_id FROM shows WHERE id in (SELECT show_id FROM functions where id = %s)))", req.getFunctionId()));
		}
		if (req.getSectionId() != null) {
			parameters.add(String.format("section_id = %s", req.getSectionId()));
		}
		if (!parameters.isEmpty()) {
			sql.append(" WHERE ");
			sql.append(String.join(" AND ", parameters));
		}
		Query query = em.createNativeQuery(sql.toString(), SeatModel.class);
		List<?> results = query.getResultList();
		Type listType = new TypeToken<List<SeatResDTO>>(){}.getType();
		return modelMapper.map(results, listType);
	}

	@Override
	public SeatResDTO getById(Long id) {
		SeatModel model = this.seatRepository.findById(id).orElseThrow(() -> new CustomException(404, "Elemento no existe."));
		return modelMapper.map(model, SeatResDTO.class);
	}

	@Override
	public void deleteById(Long id) {
		SeatResDTO model = this.getById(id);
		this.seatRepository.deleteById(model.getId());
	}

	@Override
	public SeatResDTO update(SeatReqDTO req) {
		SeatResDTO data = this.getById(req.getId());
		SeatModel model = modelMapper.map(req, SeatModel.class);
		model.setCreatedAt(data.getCreatedAt());
		this.seatRepository.saveAndFlush(model);
		return modelMapper.map(model, SeatResDTO.class);
	}

	@Override
	public SeatResDTO create(SeatReqDTO req) {
		SeatModel model = modelMapper.map(req, SeatModel.class);
		this.seatRepository.saveAndFlush(model);
		return modelMapper.map(model, SeatResDTO.class);
	}
}
