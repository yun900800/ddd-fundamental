package org.ddd.fundamental.core.repository;

import java.util.List;

public interface BaseHibernateRepository<T> {
    @Deprecated
    List<T> findAll();

    /** @deprecated */
    @Deprecated
    <S extends T> S save(S var1);

    /** @deprecated */
    @Deprecated
    <S extends T> List<S> saveAll(Iterable<S> var1);

    /** @deprecated */
    @Deprecated
    <S extends T> S saveAndFlush(S var1);

    /** @deprecated */
    @Deprecated
    <S extends T> List<S> saveAllAndFlush(Iterable<S> var1);

    <S extends T> S persist(S var1);

    <S extends T> S persistAndFlush(S var1);

    <S extends T> List<S> persistAll(Iterable<S> var1);

    <S extends T> List<S> persistAllAndFlush(Iterable<S> var1);

    <S extends T> S merge(S var1);

    <S extends T> S mergeAndFlush(S var1);

    <S extends T> List<S> mergeAll(Iterable<S> var1);

    <S extends T> List<S> mergeAllAndFlush(Iterable<S> var1);

    <S extends T> S update(S var1);

    <S extends T> S updateAndFlush(S var1);

    <S extends T> List<S> updateAll(Iterable<S> var1);

    <S extends T> List<S> updateAllAndFlush(Iterable<S> var1);
}
