package sejong.team.service.team;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import sejong.team.service.req.ApplyTeamRequestDto;
import sejong.team.service.req.ConfirmApplicationRequestDto;

@Component
@RequiredArgsConstructor
public class TeamKafkaProducer {
    private final KafkaTemplate<String, ApplyTeamRequestDto> applyTeamRequestDtoKafkaTemplate;
    private final KafkaTemplate<String, ConfirmApplicationRequestDto> confirmApplicationRequestDtoKafkaTemplate;
    public void applyTeam(ApplyTeamRequestDto applyTeamRequestDto){
        applyTeamRequestDtoKafkaTemplate.send("team", applyTeamRequestDto);
    }

    public void confirmApplicant(ConfirmApplicationRequestDto requestDto) {
        confirmApplicationRequestDtoKafkaTemplate.send("team_confirm", requestDto);
    }
}
