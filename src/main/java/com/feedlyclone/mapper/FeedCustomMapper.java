package com.feedlyclone.mapper;

public interface FeedCustomMapper<A,B> {

    void mapAtoB(A a, B b);

    void mapBtoA(B b, A a);

    B map(A a);

    A mapReverse(B b);
}
