package sejong.team.service.req;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("title")
    private String title;
    @JsonProperty("memo")
    private String memo;
    @JsonProperty("startTime")
    private LocalDateTime startTime;
    @JsonProperty("endTime")
    private LocalDateTime endTime;
    @JsonProperty("place")
    private String place;
    @JsonProperty("awayTeamId")
    private Long awayTeamId;
    @JsonProperty("longitude")
    private Double longitude;
    @JsonProperty("latitude")
    private Double latitude;

}
