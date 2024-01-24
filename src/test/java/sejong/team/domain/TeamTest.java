package sejong.team.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TeamTest {
    @DisplayName("빌더 패턴 테스트")
    @Test
    void testTeamBuilder() {
        //given
        Long id = 1L;
        String name = "name_test";
        String unique_num = "unique_test";
        String emblem = "emblem_test";
        Team team = Team.builder()
                .emblem(emblem)
                .uniqueNum(unique_num)
                .id(id)
                .name(name)
                .build();
        //when
        Long test_id = team.getId();
        String test_name = team.getName();
        String test_unique_num = team.getUniqueNum();
        String test_emblem = team.getEmblem();
        //then
        Assertions.assertAll(
                () -> assertEquals(id, test_id),
                () -> assertEquals(name, test_name),
                () -> assertEquals(unique_num, test_unique_num),
                () -> assertEquals(emblem, test_emblem)
                );
    }
}