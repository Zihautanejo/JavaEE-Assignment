package framework;

public class BeanException extends Exception {
    public enum ErrorType {REFBEAN_NOTFOUND,CLASS_NOTFOUND,BEANROOT_ERROR,CREATE_OBJECT_ERROR,
        BEAN_ADD_ERROR,DOCUMENT_OPEN_ERROR,DOCUMENT_READ_ERROR,ELEMENT_ERROR,MAP_PUT_ERROR,
        METHOD_NOTFOUND,INJECT_ERROR};

    private ErrorType errorType;

    public BeanException (ErrorType errorType, String message){
        super(message);
        this.errorType =errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
