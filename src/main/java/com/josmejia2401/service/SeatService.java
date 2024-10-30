package com.josmejia2401.service;

import com.josmejia2401.dto.SeatReqDTO;
import com.josmejia2401.dto.SeatResDTO;
import com.josmejia2401.exceptions.CustomException;
import com.josmejia2401.models.SeatModel;
import com.josmejia2401.repository.SeatRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		return seatRepository.findAll().stream().map(p -> modelMapper.map(p, SeatResDTO.class)).toList();
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
	public void update(SeatReqDTO req) {
		SeatResDTO data = this.getById(req.getId());
		SeatModel model = modelMapper.map(req, SeatModel.class);
		model.setCreatedAt(data.getCreatedAt());
		this.seatRepository.saveAndFlush(model);
	}

	@Override
	public SeatResDTO create(SeatReqDTO req) {
		SeatModel model = modelMapper.map(req, SeatModel.class);
		this.seatRepository.saveAndFlush(model);
		return modelMapper.map(model, SeatResDTO.class);
	}
}
