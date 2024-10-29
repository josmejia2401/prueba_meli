package com.josmejia2401.dto;

import lombok.*;

import java.util.Date;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowReqDTO {
	private Date startDate;
	private Date endDate;
	private Double minPrice;
	private Double maxPrice;
	private String order;
}
