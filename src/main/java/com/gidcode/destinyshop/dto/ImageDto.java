package com.gidcode.destinyshop.dto;

import lombok.Data;

//@Data
public record ImageDto (
    long imageId,
    String imageName,
    String downloadUrl
){

}
