package com.josmejia2401.service;

import com.josmejia2401.dto.ShowDTO;
import com.josmejia2401.dto.ShowReqDTO;

import java.util.List;

public interface IShowService {
	List<ShowDTO> getAll(ShowReqDTO req);
	ShowDTO getById(Long id);
	void deleteById(Long id);
	void update(ShowDTO task);
	ShowDTO create(ShowDTO task);
}
