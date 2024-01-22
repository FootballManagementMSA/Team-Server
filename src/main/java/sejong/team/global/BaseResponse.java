package sejong.team.global;

import lombok.Getter;

@Getter
public class BaseResponse {
    private Integer status;
    private String code;
    private String message;

    public BaseResponse(){
        this.message  = MessageUtils.SUCCESS;
        this.code="";
        this.status = 200;
    }
    public BaseResponse(Integer status, String code, String reason){
        this.status = status;
        this.code = code;
        this.message = reason;
    }
}
