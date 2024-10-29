package com.josmejia2401.dto;

import lombok.*;

import java.util.Date;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowDTO {
	private long id;
	private String name;
	private String description;
	private Date startDate;
	private Date endDate;
	private Double basePrice;
	private Date createdAt;
}
