package api.gef.exception;

import api.gef.enums.ExceptionEnum;

public class EntityAlreadyExistsException extends GlobalException {
    public EntityAlreadyExistsException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum);
    }
}
