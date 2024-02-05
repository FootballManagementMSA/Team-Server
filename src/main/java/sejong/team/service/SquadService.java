package sejong.team.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sejong.team.common.client.UserServiceClient;
import sejong.team.common.client.dto.UserSquadResponse;
import sejong.team.domain.Squad;
import sejong.team.domain.UserSquad;
import sejong.team.global.MessageUtils;
import sejong.team.global.SquadNotFoundException;
import sejong.team.repository.SquadRepository;
import sejong.team.repository.UserSquadRepository;
import sejong.team.service.res.UserInSquadResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SquadService {
    private final SquadRepository squadRepository;
    private final UserSquadRepository userSquadRepository;
    private final UserServiceClient userServiceClient;

    /**
     * @param scheduleId
     * @param teamId
     * @Return
     * 1. Feign 클라이언트를 사용하여 외부 서비스로부터 UserSquadResponse를 가져온다
     * 2. UserSquadResponse로부터 name과 image를 가져와 DTO를 생성 후 반환
     */
    public List<UserInSquadResponseDto> getTeamSquad(Long scheduleId, Long teamId){
        Squad squad = squadRepository.findSquadByScheduleIdAndTeamId(scheduleId, teamId)
                .orElseThrow(() -> new SquadNotFoundException(MessageUtils.NOT_EXIST_SQUAD));

        List<UserSquad> userSquadList = userSquadRepository.findBySquadId(squad.getId());

        return userSquadList.stream().map(userSquad -> {
            UserSquadResponse userSquadResponse = userServiceClient.getUserInfoInSquad(userSquad.getUserId())
                    .getBody();

            return UserInSquadResponseDto.builder()
                    .name(userSquadResponse.getName())
                    .image(userSquadResponse.getImage())
                    .xCoordinate(userSquad.getXCoordinate())
                    .yCoordinate(userSquad.getYCoordinate())
                    .build();
        }).collect(Collectors.toList());

    }
}
