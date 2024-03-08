package sejong.team.service;

import com.google.api.gax.rpc.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import sejong.team.common.client.UserServiceClient;
import sejong.team.common.client.dto.UserSquadResponse;
import sejong.team.domain.Squad;
import sejong.team.domain.UserSquad;
import sejong.team.global.MessageUtils;
import sejong.team.global.SquadNotFoundException;
import sejong.team.repository.SquadRepository;
import sejong.team.repository.UserSquadRepository;
import sejong.team.service.req.UserInSquadSaveRequestDto;
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

    /**
     *
     * @param scheduleId
     * @param teamId
     * @param userInSquadSaveRequestDto
     * 스쿼드 조회 후 User-Squad 테이블을 스쿼드 ID를 통해 조회합니다.
     * 이후 요청으로 들어온 각 선수들에 대해 updateCount 변수를 통해 판단합니다.
     * 이미 등록된 선수(updateCount != 0) -> 업데이트
     * 등록되지 않은 선수(updateCount == 0) -> 인서트
     */
    @Transactional
    public void saveTeamSquad(Long scheduleId, Long teamId,
                              UserInSquadSaveRequestDto userInSquadSaveRequestDto){
        Squad squad = squadRepository.findSquadByScheduleIdAndTeamId(scheduleId, teamId)
                .orElseThrow(() -> new SquadNotFoundException(MessageUtils.NOT_EXIST_SQUAD));

        userInSquadSaveRequestDto.getUsers().forEach(dto -> {
            int updatedCount = userSquadRepository.updateUserSquadCoordinates(
                    dto.getUserId(), squad.getId(), dto.getX(), dto.getY());

            if (updatedCount == 0) {
                UserSquad newUserSquad = UserSquad.builder()
                        .userId(dto.getUserId())
                        .squadId(squad.getId())
                        .xCoordinate(dto.getX())
                        .yCoordinate(dto.getY())
                        .build();
                userSquadRepository.save(newUserSquad);
            }
        });

    }

    @Transactional
    @KafkaListener(topics = "user", groupId = "group_2")
    public void deleteUserSqaud(Long userId) {
        userSquadRepository.deleteAllByUserIds(userId);
    }
}
