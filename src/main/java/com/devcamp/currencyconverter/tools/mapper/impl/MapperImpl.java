package com.devcamp.currencyconverter.tools.mapper.impl;

import com.devcamp.currencyconverter.constants.Qualifiers;
import com.devcamp.currencyconverter.tools.mapper.api.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component(value = Qualifiers.MODEL_MAPPER)
public class MapperImpl implements Mapper {

    private ModelMapper modelMapper;

    public MapperImpl() {
        this.modelMapper = new ModelMapper();
    }

    @Override
    public <S, D> D convert(S source, Class<D> destinationClass) {
        return this.modelMapper.map(source, destinationClass);
    }
}