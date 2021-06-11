package com.fmariani.examples.crudPerson.dtos;


import java.time.LocalDate;

/**
 * Created by florencia on 08/06/21.
 */
public class ResponseDto {

    private String status;
    private String message;

    public ResponseDto(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
