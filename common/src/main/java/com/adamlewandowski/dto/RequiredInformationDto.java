package com.adamlewandowski.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequiredInformationDto {
    private String cityName;
    private int page;
    private int numberOfRowsToDisplay;
    private String unit;
}
