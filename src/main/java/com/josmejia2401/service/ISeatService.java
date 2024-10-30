package com.josmejia2401.service;

import com.josmejia2401.dto.SeatReqDTO;
import com.josmejia2401.dto.SeatResDTO;

import java.util.List;

public interface ISeatService {
	List<SeatResDTO> getAll(SeatReqDTO req);
	SeatResDTO getById(Long id);
	void deleteById(Long id);
	SeatResDTO update(SeatReqDTO req);
	SeatResDTO create(SeatReqDTO req);
}
