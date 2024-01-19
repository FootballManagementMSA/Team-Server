package sejong.team.service.res;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class TeamBaseInfoResponseDto {
    private String teamName;
    private String unique_num;
    private String teamEmblem;
    private LocalDateTime createdAt;
    private Integer sizeOfUsers;
}
