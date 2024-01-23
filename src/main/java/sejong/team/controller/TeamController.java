package sejong.team.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sejong.team.dto.TeamDto;
import sejong.team.global.BaseResponse;
import sejong.team.global.DataResponse;
import sejong.team.service.TeamService;
import sejong.team.service.res.TeamBaseInfoResponseDto;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team-service")
public class TeamController {
    private final TeamService teamService;
    @GetMapping("/teams/{teamId}")
    public DataResponse<TeamBaseInfoResponseDto> findTeamBaseInfo(@PathVariable Long teamId) {
        return new DataResponse<>(teamService.findTeamInfo(teamId));
    }
    @PostMapping("/teams")
    public BaseResponse createTeam(@ModelAttribute TeamDto teamDto) throws IOException {
        teamService.createTeam(teamDto);
        return new BaseResponse();
    }


}
