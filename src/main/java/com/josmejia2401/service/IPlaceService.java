package com.josmejia2401.service;

import com.josmejia2401.dto.PlaceReqDTO;
import com.josmejia2401.dto.PlaceResDTO;

import java.util.List;

public interface IPlaceService {
	List<PlaceResDTO> getAll(PlaceReqDTO req);
	PlaceResDTO getById(Long id);
	void deleteById(Long id);
	PlaceResDTO update(PlaceReqDTO req);
	PlaceResDTO create(PlaceReqDTO req);
}
