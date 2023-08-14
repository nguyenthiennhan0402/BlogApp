package com.springbootBlog.BlogApp.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class ErrorDetail {
    private Date timestamp;
    private String msg;
    private String details;

    public ErrorDetail(Date timestamp, String msg, String details) {
        this.timestamp = timestamp;
        this.msg = msg;
        this.details = details;
    }

}
