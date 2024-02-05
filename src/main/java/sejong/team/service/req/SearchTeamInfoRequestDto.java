package sejong.team.service.req;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchTeamInfoRequestDto {
    private String search;
    public static SearchTeamInfoRequestDto of(String searchParam){
        return SearchTeamInfoRequestDto.builder()
                .search(searchParam)
                .build();
    }
}
