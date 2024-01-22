package sejong.team.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sejong.team.dto.TeamDto;
import sejong.team.global.BaseResponse;
import sejong.team.service.TeamService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team-service")
public class TeamController {
    private final TeamService teamService;
    @PostMapping("/teams")
    public BaseResponse createTeam(@ModelAttribute TeamDto teamDto) throws IOException {
        teamService.createTeam(teamDto);
        return new BaseResponse();
    }


}
