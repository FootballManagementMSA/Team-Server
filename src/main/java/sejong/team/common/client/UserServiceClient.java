package sejong.team.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sejong.team.common.client.dto.SizeOfUsersInTeamResponse;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    @GetMapping("/user-service/users/teams/{teamId}")
    SizeOfUsersInTeamResponse getSizeOfUsersInTeam(@PathVariable(value = "teamId") Long teamId);
}
