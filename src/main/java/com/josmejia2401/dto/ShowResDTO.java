package com.josmejia2401.dto;

import lombok.*;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowResDTO {
	private long id;
	private String name;
	private String description;
	private Date startDate;
	private Date endDate;
	private Long place_id;
	private Date createdAt;

	private List<FunctionResDTO> functions;
}
