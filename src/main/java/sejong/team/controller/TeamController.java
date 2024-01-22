package sejong.team.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sejong.team.service.TeamService;
import sejong.team.service.res.TeamBaseInfoResponseDto;

@RequiredArgsConstructor
@RequestMapping("/api/team-service")
@RestController
public class TeamController {
    private final TeamService teamService;
    @GetMapping("/teams/{teamId}")
    public ResponseEntity<TeamBaseInfoResponseDto>findTeamBaseInfo(@PathVariable Long teamId){
        TeamBaseInfoResponseDto teamInfo = teamService.findTeamInfo(teamId);
        return ResponseEntity.ok(teamInfo);
    }
}
