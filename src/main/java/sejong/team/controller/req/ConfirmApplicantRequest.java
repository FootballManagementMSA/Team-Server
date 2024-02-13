package sejong.team.controller.req;

import lombok.Getter;

@Getter
public class ConfirmApplicantRequest {
    private Long userId;
    private boolean approve;
}
