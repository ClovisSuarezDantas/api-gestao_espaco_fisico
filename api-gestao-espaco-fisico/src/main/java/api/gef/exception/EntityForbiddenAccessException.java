package api.gef.exception;

import api.gef.enums.ExceptionEnum;

public class EntityForbiddenAccessException extends GlobalException {
    public EntityForbiddenAccessException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum);
    }
}
