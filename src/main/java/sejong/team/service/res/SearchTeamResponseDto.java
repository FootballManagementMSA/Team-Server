package sejong.team.service.res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchTeamResponseDto {
    private String name;
    private String uniqueNum;
    private String emblem;
}
