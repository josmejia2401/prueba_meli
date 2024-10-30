package com.josmejia2401.service;

import com.josmejia2401.dto.PlaceReqDTO;
import com.josmejia2401.dto.PlaceResDTO;
import com.josmejia2401.exceptions.CustomException;
import com.josmejia2401.models.PlaceModel;
import com.josmejia2401.repository.PlaceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Description("Servicio para gestionar los sitios")
@Service
@Log4j2
public class PlaceService implements IPlaceService {

	@Autowired
	private EntityManager em;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PlaceRepository placeRepository;
	@Autowired
	private SectionService sectionService;

	@Override
	public List<PlaceResDTO> getAll(PlaceReqDTO req) {
		StringBuilder sql = new StringBuilder("SELECT * FROM places");
		List<String> parameters = new ArrayList<>();
		if (req.getName() != null) {
			parameters.add(String.format("name = %s", req.getName()));
		}
		if (!parameters.isEmpty()) {
			sql.append(" WHERE ");
			sql.append(String.join(" AND ", parameters));
		}
		Query query = em.createNativeQuery(sql.toString(), PlaceModel.class);
		List<?> results = query.getResultList();
		Type listType = new TypeToken<List<PlaceResDTO>>(){}.getType();
		return modelMapper.map(results, listType);
	}

	@Override
	public PlaceResDTO getById(Long id) {
		PlaceModel model = this.placeRepository.findById(id).orElseThrow(() -> new CustomException(404, "Elemento no existe."));
		return modelMapper.map(model, PlaceResDTO.class);
	}

	@Override
	public void deleteById(Long id) {
		PlaceResDTO model = this.getById(id);
		this.placeRepository.deleteById(model.getId());
	}

	@Override
	public PlaceResDTO update(PlaceReqDTO req) {
		PlaceResDTO data = this.getById(req.getId());
		PlaceModel model = modelMapper.map(req, PlaceModel.class);
		model.setCreatedAt(data.getCreatedAt());
		this.placeRepository.saveAndFlush(model);
		return modelMapper.map(model, PlaceResDTO.class);
	}

	@Override
	public PlaceResDTO create(PlaceReqDTO req) {
		PlaceModel model = modelMapper.map(req, PlaceModel.class);
		this.placeRepository.saveAndFlush(model);
		PlaceResDTO response = modelMapper.map(model, PlaceResDTO.class);
		if (req.getSections() != null && !req.getSections().isEmpty()) {
			req.getSections().forEach(p -> p.setPlaceId(response.getId()));
			response.setSections(req.getSections().stream().map(p -> this.sectionService.create(p)).toList());
		}
		return response;
	}
}
