package sejong.team.global;

import lombok.Getter;

@Getter
public class DataResponse<T> extends BaseResponse{
    private T data;

    public DataResponse(T data) {
        super();
        this.data = data;
    }
    public DataResponse(String reason, String code, Integer status, T data) {
        super(status,code,reason);
        this.data = data;
    }
}
