package sejong.team.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sejong.team.global.DataResponse;
import sejong.team.service.SquadService;
import sejong.team.service.res.UserInSquadResponseDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team-service")
public class SquadController {
    private final SquadService squadService;
    @GetMapping("/schedules/{scheduleId}/teams/{teamId}/squad")
    public DataResponse<List<UserInSquadResponseDto>> getTeamSquad(@PathVariable(name = "scheduleId")Long scheduleId,
                                                                   @PathVariable(name = "teamId")Long teamId){
        return new DataResponse(squadService.getTeamSquad(scheduleId,teamId));
    }
}
