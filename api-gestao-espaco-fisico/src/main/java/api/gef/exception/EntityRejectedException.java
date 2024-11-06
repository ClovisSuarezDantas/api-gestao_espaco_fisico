package api.gef.exception;

import api.gef.enums.ExceptionEnum;

public class EntityRejectedException extends GlobalException {
    public EntityRejectedException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum);
    }
}
