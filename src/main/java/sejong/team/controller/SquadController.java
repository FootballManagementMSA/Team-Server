package sejong.team.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sejong.team.global.BaseResponse;
import sejong.team.global.DataResponse;
import sejong.team.service.SquadService;
import sejong.team.service.req.UserInSquadSaveRequestDto;
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
    @PostMapping("/schedules/{scheduleId}/teams/{teamId}/squad")
    public DataResponse saveTeamSquad(@PathVariable(name = "scheduleId")Long scheduleId,
                                      @PathVariable(name = "teamId")Long teamId,
                                      @RequestBody UserInSquadSaveRequestDto users) {
        squadService.saveTeamSquad(scheduleId,teamId,users);
        return new DataResponse();
    }

    @DeleteMapping("/{userId}/squad")
    public ResponseEntity<Void> deleteUserSquad(@PathVariable Long userId) {
        squadService.deleteUserSqaud(userId);

        return ResponseEntity.ok().build();
    }
}
