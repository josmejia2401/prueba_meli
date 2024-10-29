package com.josmejia2401.dto;

import lombok.*;

import java.util.List;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationDTO {
	private Long dni;
	private String name;
	private Long showId;
	private Long functionId;
	private List<SeatsDTO> seats;
}
