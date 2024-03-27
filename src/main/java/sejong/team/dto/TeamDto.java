package sejong.team.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Builder
public class TeamDto {
    private String name;
    private String details;
    private MultipartFile emblem;
}
