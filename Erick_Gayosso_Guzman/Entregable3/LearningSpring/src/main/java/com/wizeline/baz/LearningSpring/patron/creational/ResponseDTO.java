package com.wizeline.baz.LearningSpring.patron.creational;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.wizeline.baz.LearningSpring.model.ErrorDTO;

public class ResponseDTO {

    //BUILDER PATRON DISEÑO
    private final String status;

    private final String code;

    private final ErrorDTO errors;

    private final Object results;

    private ResponseDTO(ResponseDTOBuilder builder){
        this.status = builder.status;
        this.code = builder.code;
        this.errors = builder.errors;
        this.results = builder.results;
    }

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "status='" + status + '\'' +
                ", code='" + code + '\'' +
                ", errors=" + errors +
                ", results=" + results +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public ErrorDTO getErrors() {
        return errors;
    }

    public Object getResults() {
        return results;
    }

    public static class ResponseDTOBuilder{
        private  String status;

        private  String code;

        private  ErrorDTO errors;

        private  Object results;

        public ResponseDTOBuilder(String status, String code) {
            this.status = status;
            this.code = code;
        }
        public ResponseDTOBuilder errors(ErrorDTO errorDTO){
            this.errors = new ErrorDTO("FAIL0000","Ocurrio algun error");
            return this;
        }
        public ResponseDTOBuilder results(Object results){
            this.results = results;
            return this;
        }
        public ResponseDTO build(){
            return new ResponseDTO(this);
        }
    }

}
