package api.gef.exception;

import api.gef.enums.ExceptionEnum;

public class EntityNotFoundException extends GlobalException {
    public EntityNotFoundException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum);
    }
}
