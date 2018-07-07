package com.devcamp.currencyconverter.tools.mapper.api;

public interface Mapper {
    <S, D> D convert(S source, Class<D> destinationClass);
}
