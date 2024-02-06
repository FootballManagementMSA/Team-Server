package sejong.team.service.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserInSquadSaveRequestDto {
    private List<UserSquadRequest> users;
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserSquadRequest {
        private Long userId;
        private Double x;
        private Double y;
    }
}
