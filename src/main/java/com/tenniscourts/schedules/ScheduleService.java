package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.TennisCourtDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    /**
     * Adds a schedule to the database with a given tennis court id
     * @param tennisCourtId the id of the tennis court you want to add to your schedule
     * @param createScheduleRequestDTO a DTO representing a request to add a schedule. Includes the
     *                                 tennis court id and the start time.
     * @return a schedule DTO containing information about the schedule added to the database.
     */
    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
        List<ScheduleDTO> scheduleDTOS = findSchedulesByTennisCourtId(tennisCourtId);
        if (scheduleDTOS.isEmpty()) {
            throw new EntityNotFoundException("Cannot add a schedule for this tennis Id");
        }

        LocalDateTime startTime = createScheduleRequestDTO.getStartDateTime();
        TennisCourtDTO tennisCourtDTO = scheduleDTOS.get(0).getTennisCourt();
        ScheduleDTO scheduleDTO = ScheduleDTO.builder()
                .tennisCourtId(tennisCourtId).tennisCourt(tennisCourtDTO)
                .startDateTime(startTime).endDateTime(startTime.plusHours(1))
                .build();

        scheduleDTO.setId(scheduleRepository.save(scheduleMapper.map(scheduleDTO)).getId());
        return scheduleDTO;
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        //TODO: implement
        return null;
    }

    public ScheduleDTO findSchedule(Long scheduleId) {

        return scheduleRepository.findById(scheduleId).map(scheduleMapper::map).orElseThrow(() -> {
                    throw new EntityNotFoundException("Schedule not found.");
                }
        );
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}
