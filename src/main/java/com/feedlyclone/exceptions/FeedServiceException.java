package com.feedlyclone.exceptions;

public class FeedServiceException extends Exception {

    private static final long serialVersionUID = -6352707893785194387L;

    public FeedServiceException() {
        super();
    }

    public FeedServiceException(String s) {
        super(s);
    }
}
