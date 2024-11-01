package com.josmejia2401.dto;

import lombok.*;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationResDTO {
	private long id;
	private Long userId;
	private Long showId;
	private Long functionId;
	private Date reservationDate;
	private String status;
	private Date createdAt;
}
