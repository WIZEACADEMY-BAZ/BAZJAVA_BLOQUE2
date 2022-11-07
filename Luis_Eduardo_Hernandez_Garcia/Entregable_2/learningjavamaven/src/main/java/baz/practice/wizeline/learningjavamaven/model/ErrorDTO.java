package baz.practice.wizeline.learningjavamaven.model;

public class ErrorDTO {
    private String errorCode;
    private String message;

    public ErrorDTO(){ super(); }

    public ErrorDTO(ErrorDTOBuilder errorDTOBuilder){
        super();
        this.errorCode=errorDTOBuilder.errorCode;
        this.message=errorDTOBuilder.message;
    }


    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }


    public static class ErrorDTOBuilder{
        private String errorCode;
        private String message;

        public ErrorDTOBuilder(String errorCode, String message){
            this.errorCode = errorCode;
            this.message = message;
        }


        public ErrorDTOBuilder errorCode(String errorCode){
            this.errorCode = errorCode;
            return this;
        }

        public ErrorDTOBuilder message(String message){
            this.message = message;
            return this;
        }

        public ErrorDTO build() {
            return new ErrorDTO(this);
        }
    }
}
