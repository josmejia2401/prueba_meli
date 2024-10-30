package com.josmejia2401.service;

import com.josmejia2401.dto.FunctionReqDTO;
import com.josmejia2401.dto.FunctionResDTO;
import com.josmejia2401.exceptions.CustomException;
import com.josmejia2401.models.FunctionModel;
import com.josmejia2401.repository.FunctionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Description("Servicio para gestionar los sitios")
@Service
@Log4j2
public class FunctionService implements IFunctionService {

	@Autowired
	private EntityManager em;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private FunctionRepository functionRepository;

	@Override
	public List<FunctionResDTO> getAll(FunctionReqDTO req) {
		StringBuilder sql = new StringBuilder("SELECT * FROM functions");
		Query query = em.createNativeQuery(sql.toString(), FunctionModel.class);
		List<?> results = query.getResultList();
		Type listType = new TypeToken<List<FunctionResDTO>>(){}.getType();
		return modelMapper.map(results, listType);
	}

	@Override
	public FunctionResDTO getById(Long id) {
		FunctionModel model = this.functionRepository.findById(id).orElseThrow(() -> new CustomException(404, "Elemento no existe."));
		return modelMapper.map(model, FunctionResDTO.class);
	}

	@Override
	public void deleteById(Long id) {
		FunctionResDTO model = this.getById(id);
		this.functionRepository.deleteById(model.getId());
	}

	@Override
	public FunctionResDTO update(FunctionReqDTO req) {
		FunctionResDTO data = this.getById(req.getId());
		FunctionModel model = modelMapper.map(req, FunctionModel.class);
		model.setCreatedAt(data.getCreatedAt());
		this.functionRepository.saveAndFlush(model);
		return modelMapper.map(model, FunctionResDTO.class);
	}

	@Override
	public FunctionResDTO create(FunctionReqDTO req) {
		FunctionModel model = modelMapper.map(req, FunctionModel.class);
		this.functionRepository.saveAndFlush(model);
		return modelMapper.map(model, FunctionResDTO.class);
	}
}
