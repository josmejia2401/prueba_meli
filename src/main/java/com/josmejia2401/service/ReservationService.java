package com.josmejia2401.service;

import com.josmejia2401.dto.ReservationReqDTO;
import com.josmejia2401.dto.ReservationResDTO;
import com.josmejia2401.exceptions.CustomException;
import com.josmejia2401.models.ReservationModel;
import com.josmejia2401.models.ReserveSeatModel;
import com.josmejia2401.models.UserModel;
import com.josmejia2401.repository.ReservationRepository;
import com.josmejia2401.repository.ReserveSeatRepository;
import com.josmejia2401.repository.UserRepository;
import com.josmejia2401.utils.Status;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Description("Servicio para gestionar los sitios")
@Service
@Log4j2
public class ReservationService implements IReservationService {

	@Autowired
	private EntityManager em;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ReserveSeatRepository reserveSeatRepository;

	@Override
	public List<ReservationResDTO> getAll(ReservationReqDTO req) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		/**
		 * SELECT * FROM reservations
		 * WHERE
		 * showId = ? AND
		 * functionId = ? AND
		 * reservationDate = ? AND
		 * status = ? AND
		 * id IN (SELECT reservation_id FROM reserve_seat WHERE seat_id = ?)
		 */
		StringBuilder sql = new StringBuilder("SELECT * FROM reservations");
		List<String> parameters = new ArrayList<>();
		if (req.getFunctionId() != null) {
			parameters.add(String.format("function_id = %s", req.getFunctionId()));
		}
		if (req.getShowId() != null) {
			parameters.add(String.format("show_id = %s", req.getShowId()));
		}
		if (req.getUser() != null && req.getUser().getId() != null) {
			parameters.add(String.format("user_id = %s", req.getUser().getId()));
		}
		if (req.getStatus() != null) {
			parameters.add(String.format("status = '%s'", req.getStatus()));
		}
		if (req.getReservationDate() != null) {
			parameters.add(String.format("TO_CHAR(reservation_date, 'yyyy-mm-dd') = '%s'", simpleDateFormat.format(req.getReservationDate())));
		}
		if (req.getSeatId() != null) {
			parameters.add(String.format("id IN (SELECT reservation_id FROM reserve_seat WHERE seat_id = %s)", req.getSeatId()));
		}
		if (!parameters.isEmpty()) {
			sql.append(" WHERE ");
			sql.append(String.join(" AND ", parameters));
		}
		Query query = em.createNativeQuery(sql.toString(), ReservationModel.class);
		List<?> results = query.getResultList();
		Type listType = new TypeToken<List<ReservationResDTO>>(){}.getType();
		return modelMapper.map(results, listType);
	}

	@Override
	public ReservationResDTO getById(Long id) {
		ReservationModel model = this.reservationRepository.findById(id).orElseThrow(() -> new CustomException(404, "Elemento no existe."));
		return modelMapper.map(model, ReservationResDTO.class);
	}

	@Override
	public void deleteById(Long id) {
		ReservationResDTO model = this.getById(id);
		this.reservationRepository.deleteById(model.getId());
	}

	@Override
	public ReservationResDTO update(ReservationReqDTO req) {
		List<ReservationResDTO> results = this.getAll(ReservationReqDTO
				.builder()
				.functionId(req.getFunctionId())
				.showId(req.getShowId())
				.status(Status.CONFIRMADA.getName())
				.reservationDate(req.getReservationDate())
				.seatId(req.getSeatId())
				.user(req.getUser())
				.build());
		if (results.isEmpty()) {
			throw new CustomException(404, "No existe una reserva");
		}
		ReservationModel model = modelMapper.map(req, ReservationModel.class);
		this.reservationRepository.saveAndFlush(model);
		return modelMapper.map(model, ReservationResDTO.class);
	}

	@Override
	public ReservationResDTO create(ReservationReqDTO req) {
		List<ReservationResDTO> results = this.getAll(ReservationReqDTO
				.builder()
						.functionId(req.getFunctionId())
						.showId(req.getShowId())
						.status(Status.CONFIRMADA.getName())
						.reservationDate(req.getReservationDate())
						.seatId(req.getSeatId())
				.build());
		if (!results.isEmpty()) {
			throw new CustomException(409, "Ya existe una reserva");
		}
		UserModel userModel = modelMapper.map(req.getUser(), UserModel.class);
		if (userModel.getId() == 0L) {
			userRepository.save(userModel);
		}
		ReservationModel model = modelMapper.map(req, ReservationModel.class);
		model.setUserId(userModel.getId());
		model.setStatus(req.getStatus());
		if (model.getStatus() == null || model.getStatus().isEmpty()) {
			model.setStatus(Status.CONFIRMADA.getName());
		}
		this.reservationRepository.saveAndFlush(model);
		ReservationResDTO dto = modelMapper.map(model, ReservationResDTO.class);

		ReserveSeatModel reserveSeatModel = new ReserveSeatModel();
		reserveSeatModel.setReservationId(model.getId());
		reserveSeatModel.setSeatId(req.getSeatId());
		reserveSeatRepository.save(reserveSeatModel);
		return dto;
	}
}
