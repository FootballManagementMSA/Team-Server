package sejong.team.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
@Builder
public class TeamDto {
    private String name;
    private MultipartFile emblem;
}
