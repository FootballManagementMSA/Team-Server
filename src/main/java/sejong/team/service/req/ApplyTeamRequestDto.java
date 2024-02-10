package sejong.team.service.req;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApplyTeamRequestDto {
    private Long teamId;
    private Long userId;
    private String introduce;
}
