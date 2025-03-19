package com.gidcode.destinyshop.dto;

public record ImageDto (
    long id,
    String fileName,
    String downloadUrl
){

//    public ImageDto(long id, String fileName) {
//        this(id, fileName, null);
//    }
}
