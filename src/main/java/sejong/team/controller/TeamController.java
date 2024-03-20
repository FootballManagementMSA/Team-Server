package sejong.team.controller;

import jakarta.servlet.http.HttpServletRequest;
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
import sejong.team.service.res.*;

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
    public DataResponse<CreateTeamResponseVO> createTeam(@ModelAttribute TeamDto teamDto,
                                                         HttpServletRequest request) throws IOException {
        String token = request.getHeader("Authorization").substring(7);
        return new DataResponse<>(teamService.createTeam(teamDto,token));
    }

    @GetMapping("/teams")
    public DataResponse<List<SearchTeamResponseDto>> findAllTeams() {
        return new DataResponse<>(teamService.findAllTeams());
    }
    /**
     * 팀 검색api
     */
    @GetMapping("/team/search")
    public DataResponse<List<SearchTeamInfoResponseDto>> searchTeamInfo(@RequestParam(name = "search") String search) {
        List<SearchTeamInfoResponseDto> responseDtos = teamService.searchTeamInfo(SearchTeamInfoRequestDto.builder()
                .search(search)
                .build());
        return new DataResponse<>(responseDtos);
    }

    /**
     * 팀 가입 신청api
     */
    @PostMapping("/team/{teamId}/apply")
    public DataResponse applyTeam(@RequestBody ApplyTeamRequest request, @PathVariable Long teamId) {
        teamService.applyTeam(ApplyTeamRequestDto.builder()
                .userId(request.getUserId())
                .teamId(teamId)
                .introduce(request.getIntroduce())
                .build());
        return new DataResponse();
    }

    /**
     * 팀 가입신청 승인 또는 거절 api
     */
    @PostMapping("/team/{teamId}/apply/confirm")
    public DataResponse confirmApplicant(@PathVariable Long teamId,@RequestBody ConfirmApplicantRequest request){
        teamService.confirmApplicant(ConfirmApplicationRequestDto.builder()
                .approve(request.isApprove())
                .userId(request.getUserId())
                .teamId(teamId)
                .build());
        return new DataResponse();
    }
    @GetMapping("/users/{userId}/teams")
    public DataResponse<List<UserTeamsInfo>> findUserTeams(@PathVariable Long userId){
        return new DataResponse(teamService.findUserTeams(userId));
    }

}
