package com.josmejia2401.service;

import com.josmejia2401.dto.SectionReqDTO;
import com.josmejia2401.dto.SectionResDTO;

import java.util.List;

public interface ISectionService {
	List<SectionResDTO> getAll(SectionReqDTO req);
	SectionResDTO getById(Long id);
	void deleteById(Long id);
	void update(SectionReqDTO req);
	SectionResDTO create(SectionReqDTO req);
}
