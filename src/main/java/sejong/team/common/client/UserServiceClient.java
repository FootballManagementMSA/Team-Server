package sejong.team.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sejong.team.common.client.dto.SizeUserTeamResponse;
import sejong.team.common.client.dto.UserSquadResponse;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    @GetMapping("/api/user-service/users/teams/{teamId}/size")
    ResponseEntity<SizeUserTeamResponse> countUsersInTeam(@PathVariable(value = "teamId") Long teamId);

    @GetMapping("/api/user-service/users/{userId}/squad")
    ResponseEntity<UserSquadResponse> getUserInfoInSquad(@PathVariable(name = "userId") Long userId);
}
