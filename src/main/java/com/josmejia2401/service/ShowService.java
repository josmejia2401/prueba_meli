package com.josmejia2401.service;

import com.josmejia2401.dto.ShowDTO;
import com.josmejia2401.dto.ShowReqDTO;
import com.josmejia2401.models.ShowModel;
import com.josmejia2401.repository.ShowRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class ShowService implements IShowService {

	@Autowired
	private EntityManager em;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ShowRepository showRepository;


	@Override
	public List<ShowDTO> getAll(ShowReqDTO req) {
		StringBuilder sql = new StringBuilder("SELECT * FROM shows");
		List<String> parameters = new ArrayList<>();
		if (req.getMinPrice() != null) {
			parameters.add(String.format("base_price >= %s", req.getMinPrice()));
		}
		if (req.getMaxPrice() != null) {
			parameters.add(String.format("base_price <= %s", req.getMaxPrice()));
		}
		if (req.getStartDate() != null && req.getEndDate() != null) {
			String pattern = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			parameters.add(String.format("start_date BETWEEN PARSEDATETIME(FORMATDATETIME(%s, 'yyyy-MM-dd'), 'yyyy-MM-dd') AND %s", req.getStartDate(), req.getEndDate()));
		}
		if (!parameters.isEmpty()) {
			sql.append(" WHERE ");
			sql.append(String.join(" AND ", parameters));
			if (req.getOrder() != null) {
				sql.append(String.format(" ORDER BY 1 %s", req.getMaxPrice()));
			}
		}
		Query query = em.createNativeQuery(sql.toString(), ShowModel.class);
		List<ShowModel> results = query.getResultList();
		Type listType = new TypeToken<List<ShowDTO>>(){}.getType();
		return modelMapper.map(results, listType);
	}

	@Override
	public ShowDTO getById(Long id) {
		return null;
	}

	@Override
	public void deleteById(Long id) {

	}

	@Override
	public void update(ShowDTO task) {

	}

	@Override
	public ShowDTO create(ShowDTO task) {
		return null;
	}
}
