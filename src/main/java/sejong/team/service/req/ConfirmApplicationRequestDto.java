package sejong.team.service.req;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ConfirmApplicationRequestDto {
    private boolean approve;
    private Long teamId;
    private Long userId;
}
