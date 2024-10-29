package com.josmejia2401.dto;

import lombok.*;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatsDTO {
	private Long id;
	private String availability;
	private Double price;
}
