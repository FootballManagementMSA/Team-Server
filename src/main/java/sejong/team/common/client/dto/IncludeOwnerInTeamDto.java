package sejong.team.common.client.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IncludeOwnerInTeamDto {
    private Long teamId;
    private String token;
}
