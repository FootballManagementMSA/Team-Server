package sejong.team.service.req;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchTeamInfoRequestDto {
    private String searchCond;
    public static SearchTeamInfoRequestDto of(String searchParam){
        return SearchTeamInfoRequestDto.builder()
                .searchCond(searchParam)
                .build();
    }
}
