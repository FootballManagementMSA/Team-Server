package sejong.team.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sejong.team.global.DataResponse;
import sejong.team.service.ScheduleService;
import sejong.team.service.req.CreateScheduleRequestDto;
import sejong.team.service.res.ViewScheduleResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team-service")
public class ScheduleController {
    private final ScheduleService scheduleService;
    @PostMapping("/{teamId}/schedules")
    public DataResponse createSchedule(@PathVariable(name = "teamId") Long teamId,
                                       @RequestBody CreateScheduleRequestDto createScheduleRequestDto){
        scheduleService.createSchedule(teamId, createScheduleRequestDto);
        return new DataResponse();
    }
    @GetMapping("/schedules/{scheduleId}")
    public DataResponse<ViewScheduleResponseDto> viewSchedule(@PathVariable(name = "scheduleId") Long scheduleId){
        return new DataResponse<>(scheduleService.viewSchedule(scheduleId));
    }
    @PutMapping("/schedules/{scheduleId}")
    public DataResponse acceptSchedule(@PathVariable(name = "scheduleId") Long scheduleId){
        scheduleService.acceptSchedule(scheduleId);
        return new DataResponse();
    }

    @DeleteMapping("/schedules/{scheduleId}")
    public DataResponse rejectSchedule(@PathVariable(name = "scheduleId") Long scheduleId){
        scheduleService.deleteSchedule(scheduleId);
        return new DataResponse();
    }
}
