package sejong.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sejong.team.domain.UserSquad;

import java.util.List;


public interface UserSquadRepository extends JpaRepository<UserSquad,Long> {
    @Query("SELECT us FROM user_squad_tb us WHERE us.squadId = :squadId")
    List<UserSquad> findBySquadId(@Param("squadId") Long squadId);

    @Modifying
    @Query("UPDATE user_squad_tb us SET us.xCoordinate = :xCoordinate, us.yCoordinate = :yCoordinate " +
            "WHERE us.userId = :userId AND us.squadId = :squadId")
    int updateUserSquadCoordinates(@Param("userId") Long userId,
                                   @Param("squadId") Long squadId,
                                   @Param("xCoordinate") Double xCoordinate,
                                   @Param("yCoordinate") Double yCoordinate);

    /**
     * 벌크연산 - squad_id가 squadIds 리스트에 있는 ID 중 하나라도 일치하는 모든 UserSquad 행을 삭제
     * @param squadIds
     */
    void deleteBySquadIdIn(List<Long> squadIds);
}
