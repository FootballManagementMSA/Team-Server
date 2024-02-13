package sejong.team.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse {
    private Integer status;
    private String code;
    private String message;

    public BaseResponse(){
        this.message  = MessageUtils.SUCCESS;
        this.status = 200;
    }
    public BaseResponse(Integer status, String code, String message){
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
