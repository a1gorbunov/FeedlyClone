package com.feedlyclone.mapper;

/**
 * Custom bean mapper, order not important
 * @param <A> one class
 * @param <B> other class
 */
public interface FeedCustomMapper<A,B> {

    void mapAtoB(A a, B b);

    void mapBtoA(B b, A a);

    B map(A a);

    A mapReverse(B b);
}
