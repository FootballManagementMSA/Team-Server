package sejong.team.service.res;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SearchTeamInfoResponseDto {
    private Long teamId;
    private String teamName;
    private Integer totalMemberCnt;
    private String uniqueNum;
    private String emblem;
    public static SearchTeamInfoResponseDto of(Long teamId,String teamName,String emblem,Integer totalMemberCnt,String uniqueNum){
        return SearchTeamInfoResponseDto.builder()
                .teamId(teamId)
                .teamName(teamName)
                .uniqueNum(uniqueNum)
                .totalMemberCnt(totalMemberCnt)
                .emblem(emblem)
                .build();
    }
}
