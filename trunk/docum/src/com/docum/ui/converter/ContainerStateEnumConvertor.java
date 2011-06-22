package com.docum.ui.converter;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import com.docum.domain.ContainerStateEnum;

@FacesConverter(value = "containerStateConvertor")

public class ContainerStateEnumConvertor extends EnumConverter {
    public ContainerStateEnumConvertor() {
        super(ContainerStateEnum.class);
    }
}
