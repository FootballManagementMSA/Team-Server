package sejong.team.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import sejong.team.common.client.dto.IncludeOwnerInTeamDto;
import sejong.team.common.client.dto.SizeUserTeamResponse;
import sejong.team.common.client.dto.UserSquadResponse;
import sejong.team.common.client.dto.UserTeamInfoDto;
import sejong.team.global.DataResponse;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    @GetMapping("/api/user-service/users/teams/{teamId}/size")
    ResponseEntity<SizeUserTeamResponse> countUsersInTeam(@PathVariable(value = "teamId") Long teamId);

    @GetMapping("/api/user-service/users/{userId}/squad")
    ResponseEntity<UserSquadResponse> getUserInfoInSquad(@PathVariable(name = "userId") Long userId);
    @PostMapping("/api/user-service/users/teams/")
    DataResponse IncludeOwnerInTeam(@RequestBody IncludeOwnerInTeamDto includeOwnerInTeamDto);

    @GetMapping("/api/user-service/users/{userId}/teams")
    ResponseEntity<List<UserTeamInfoDto>> getUserTeams(@PathVariable(name = "userId") Long userId);
}
