package sejong.team.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sejong.team.controller.req.ApplyTeamRequest;
import sejong.team.controller.req.ConfirmApplicantRequest;
import sejong.team.dto.TeamDto;
import sejong.team.global.DataResponse;
import sejong.team.service.TeamService;
import sejong.team.service.req.ApplyTeamRequestDto;
import sejong.team.service.req.ConfirmApplicationRequestDto;
import sejong.team.service.req.SearchTeamInfoRequestDto;
import sejong.team.service.res.CreateTeamResponseVO;
import sejong.team.service.res.SearchTeamInfoResponseDto;
import sejong.team.service.res.SearchTeamResponseDto;
import sejong.team.service.res.TeamBaseInfoResponseDto;

import java.io.IOException;
import java.util.List;

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
    public DataResponse<CreateTeamResponseVO> createTeam(@ModelAttribute TeamDto teamDto) throws IOException {
        return new DataResponse<>(teamService.createTeam(teamDto));
    }

    @GetMapping("/teams")
    public DataResponse<List<SearchTeamResponseDto>> findAllTeams() {
        return new DataResponse<>(teamService.findAllTeams());
    }

    @GetMapping("/team/search")
    public DataResponse<List<SearchTeamInfoResponseDto>> searchTeamInfo(@RequestParam(name = "search") String search) {
        List<SearchTeamInfoResponseDto> responseDtos = teamService.searchTeamInfo(SearchTeamInfoRequestDto.builder()
                .search(search)
                .build());
        return new DataResponse<>(responseDtos);
    }

    @PostMapping("/team/{teamId}/apply")
    public void applyTeam(@RequestBody ApplyTeamRequest request, @PathVariable Long teamId) {
        teamService.applyTeam(ApplyTeamRequestDto.builder()
                .userId(request.getUserId())
                .teamId(teamId)
                .introduce(request.getIntroduce())
                .build());
    }

    @PostMapping("/team/{teamId}/apply/confirm")
    public void confirmApplicant(@PathVariable Long teamId,@RequestBody ConfirmApplicantRequest request){
        teamService.confirmApplicant(ConfirmApplicationRequestDto.builder()
                .approve(request.isApprove())
                .userId(request.getUserId())
                .teamId(teamId)
                .build());
    }


}
