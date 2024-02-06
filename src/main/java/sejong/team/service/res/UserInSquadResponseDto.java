package sejong.team.service.res;

import lombok.Builder;
import lombok.Getter;
@Getter
@Builder
public class UserInSquadResponseDto {
    private String name;
    private String image;
    private Double xCoordinate;
    private Double yCoordinate;
}
