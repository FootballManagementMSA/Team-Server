package sejong.team.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sejong.team.common.client.dto.IncludeOwnerInTeamDto;
import sejong.team.common.client.dto.SizeUserTeamResponse;
import sejong.team.common.client.dto.UserSquadResponse;
import sejong.team.global.DataResponse;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    @GetMapping("/api/user-service/users/teams/{teamId}/size")
    ResponseEntity<SizeUserTeamResponse> countUsersInTeam(@PathVariable(value = "teamId") Long teamId);

    @GetMapping("/api/user-service/users/{userId}/squad")
    ResponseEntity<UserSquadResponse> getUserInfoInSquad(@PathVariable(name = "userId") Long userId);
    @PostMapping("/api/user-service/users/teams/")
    DataResponse IncludeOwnerInTeam(@RequestBody IncludeOwnerInTeamDto includeOwnerInTeamDto);

    @GetMapping("/api/user-service/fcm/")
    String getFcmToken(@RequestParam Long teamId);
}
