package com.github.knextsunj.cmskycbatchprocessor.dao;

public interface GenericProcessingDAO<T> {

    T getData(Long id);
}
