package com.josmejia2401.dto;

import lombok.*;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatResDTO {
	private long id;
	private Long sectionId;
	private String number;
	private boolean availability;
	private Date createdAt;
}
