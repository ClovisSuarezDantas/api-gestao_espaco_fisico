package api.gef.exception;

import api.gef.enums.ExceptionEnum;
import api.gef.vo.ErrorVO;
import lombok.Getter;

@Getter
public abstract class GlobalException extends RuntimeException {
    private final ErrorVO errorVO;
    public GlobalException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.errorVO = new ErrorVO(exceptionEnum.getCode(), exceptionEnum.getMessage());
    }
}
