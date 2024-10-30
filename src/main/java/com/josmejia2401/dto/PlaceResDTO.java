package com.josmejia2401.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceResDTO {
	private long id;
	private String name;
	private String address;
	private Long capacity;
	private Date createdAt;

	private List<SectionResDTO> sections;
}
