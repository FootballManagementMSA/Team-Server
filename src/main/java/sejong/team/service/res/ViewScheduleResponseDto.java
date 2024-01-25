package sejong.team.service.res;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ViewScheduleResponseDto {
    private String title;
    private String memo;
    private String homeTeamName;
    private String homeTeamEmblem;
    private String homeTeamUniqueNumber;
    private String awayTeamName;
    private String awayTeamEmblem;
    private String awayTeamUniqueNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String place;
    private Double longitude;
    private Double latitude;
}
