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
public class SectionResDTO {
	private long id;
	private Long placeId;
	private String name;
	private Double price;
	private Date createdAt;

	private List<SeatResDTO> seats;

}
