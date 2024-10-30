package com.josmejia2401.service;

import com.josmejia2401.dto.ShowFilterReqDTO;
import com.josmejia2401.dto.ShowReqDTO;
import com.josmejia2401.dto.ShowResDTO;
import com.josmejia2401.exceptions.CustomException;
import com.josmejia2401.models.ShowModel;
import com.josmejia2401.repository.ShowRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Description("Servicio para gestionar los sitios")
@Service
@Log4j2
public class ShowService implements IShowService {

	@Autowired
	private EntityManager em;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private ShowRepository showRepository;
	@Autowired
	private IFunctionService sectionService;

	@Override
	public List<ShowResDTO> getAll(ShowFilterReqDTO req) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		StringBuilder sql = new StringBuilder("SELECT * FROM shows");
		List<String> parameters = new ArrayList<>();
		if (req.getStartDate() != null) {
			parameters.add(String.format("start_date >= date '%s'", simpleDateFormat.format(req.getStartDate())));
		}
		if (req.getEndDate() != null) {
			parameters.add(String.format("start_date <= date '%s'", simpleDateFormat.format(req.getEndDate())));
		}
		if (req.getMaxPrice() != null && req.getMinPrice() != null) {
			parameters.add(String.format("place_id IN (SELECT place_id FROM sections WHERE price <= %s AND price >= %s)", req.getMaxPrice(), req.getMinPrice()));
		}
		if (!parameters.isEmpty()) {
			sql.append(" WHERE ");
			sql.append(String.join(" AND ", parameters));
			String[] orders = new String[]{"asc", "desc"};
			if (req.getOrder() != null && Arrays.stream(orders).toList().contains(req.getOrder().toLowerCase())) {
				sql.append(String.format(" ORDER BY 1 %s", req.getOrder()));
			}
		}
		Query query = em.createNativeQuery(sql.toString(), ShowModel.class);
		List<?> results = query.getResultList();
		Type listType = new TypeToken<List<ShowResDTO>>(){}.getType();
		return modelMapper.map(results, listType);
	}

	@Override
	public ShowResDTO getById(Long id) {
		ShowModel model = this.showRepository.findById(id).orElseThrow(() -> new CustomException(404, "Elemento no existe."));
		return modelMapper.map(model, ShowResDTO.class);
	}

	@Override
	public void deleteById(Long id) {
		ShowResDTO model = this.getById(id);
		this.showRepository.deleteById(model.getId());
	}

	@Override
	public ShowResDTO update(ShowReqDTO req) {
		ShowResDTO data = this.getById(req.getId());
		ShowModel model = modelMapper.map(req, ShowModel.class);
		model.setCreatedAt(data.getCreatedAt());
		this.showRepository.saveAndFlush(model);
		return modelMapper.map(model, ShowResDTO.class);
	}

	@Override
	public ShowResDTO create(ShowReqDTO req) {
		ShowModel model = modelMapper.map(req, ShowModel.class);
		model.setPlace_id(req.getPlaceId());
		this.showRepository.saveAndFlush(model);
		ShowResDTO response = modelMapper.map(model, ShowResDTO.class);
		if (req.getFunctions() != null && !req.getFunctions().isEmpty()) {
			req.getFunctions().forEach(p -> p.setShowId(response.getId()));
			response.setFunctions(req.getFunctions().stream().map(p -> this.sectionService.create(p)).toList());
		}
		return response;
	}
}
