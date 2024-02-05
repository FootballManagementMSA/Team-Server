package sejong.team.service.req;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @JsonDeserialize
 * Jackson이 사용할 Builder 클래스를 명시. Jackson이 Builder 클래스를 사용하여 객체를 생성하도록 한다.
 */
@Getter
@Builder
@JsonDeserialize(builder = CreateScheduleRequestDto.CreateScheduleRequestDtoBuilder.class)
public class CreateScheduleRequestDto {
    private String title;
    private String memo;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String place;
    private Long awayTeamId;
    private Double longitude;
    private Double latitude;
    private String fcmToken;



}
