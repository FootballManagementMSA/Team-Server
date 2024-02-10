package sejong.team.service.team;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import sejong.team.service.req.ApplyTeamRequestDto;

@Component
@RequiredArgsConstructor
public class TeamKafkaProducer {
    private final KafkaTemplate<String, ApplyTeamRequestDto> kafkaTemplate;
    public void applyTeam(ApplyTeamRequestDto applyTeamRequestDto){
        kafkaTemplate.send("team", applyTeamRequestDto);
    }
}
