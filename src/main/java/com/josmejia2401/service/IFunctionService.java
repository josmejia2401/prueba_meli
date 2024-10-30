package com.josmejia2401.service;

import com.josmejia2401.dto.FunctionReqDTO;
import com.josmejia2401.dto.FunctionResDTO;

import java.util.List;

public interface IFunctionService {
	List<FunctionResDTO> getAll(FunctionReqDTO req);
	FunctionResDTO getById(Long id);
	void deleteById(Long id);
	FunctionResDTO update(FunctionReqDTO req);
	FunctionResDTO create(FunctionReqDTO req);
}
