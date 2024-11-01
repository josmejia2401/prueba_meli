package com.josmejia2401.service;

import com.josmejia2401.dto.ReservationReqDTO;
import com.josmejia2401.dto.ReservationResDTO;

import java.util.List;

public interface IReservationService {
	List<ReservationResDTO> getAll(ReservationReqDTO req);
	ReservationResDTO getById(Long id);
	void deleteById(Long id);
	ReservationResDTO update(ReservationReqDTO req);
	ReservationResDTO create(ReservationReqDTO req);
}
