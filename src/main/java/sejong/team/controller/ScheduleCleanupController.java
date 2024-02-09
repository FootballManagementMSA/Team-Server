package sejong.team.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sejong.team.global.DataResponse;
import sejong.team.scheduler.ScheduleCleanupTask;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team-service")
public class ScheduleCleanupController {
    private final ScheduleCleanupTask scheduleCleanupTask;
    @GetMapping("/cleanup")
    public DataResponse triggerCleanup() {
        scheduleCleanupTask.cleanUpExpiredSchedules();
        return new DataResponse();
    }
}
