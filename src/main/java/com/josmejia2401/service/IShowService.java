package com.josmejia2401.service;

import com.josmejia2401.dto.ShowFilterReqDTO;
import com.josmejia2401.dto.ShowReqDTO;
import com.josmejia2401.dto.ShowResDTO;

import java.util.List;

public interface IShowService {
	List<ShowResDTO> getAll(ShowFilterReqDTO req);
	ShowResDTO getById(Long id);
	void deleteById(Long id);
	ShowResDTO update(ShowReqDTO req);
	ShowResDTO create(ShowReqDTO req);
}
