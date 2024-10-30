package com.josmejia2401.dto;

import lombok.*;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowFilterReqDTO {
	private Date startDate;
	private Date endDate;
	private Double maxPrice;
	private Double minPrice;
	private String order;
}
