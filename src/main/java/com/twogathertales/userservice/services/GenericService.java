package com.twogathertales.userservice.services;

public interface GenericService<T> {

    public Iterable<T> findAll();

    public T  find(String id);

    public T create(T t);

    public void update(T t);

    public void delete(String id);

}
