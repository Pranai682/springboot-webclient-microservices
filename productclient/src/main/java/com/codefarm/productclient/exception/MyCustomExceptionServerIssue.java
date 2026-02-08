package com.codefarm.productclient.exception;

public class MyCustomExceptionServerIssue  extends RuntimeException{
    public MyCustomExceptionServerIssue(String s) {
        super(s);
    }

    public static class ProductNotFoundException extends RuntimeException {
        public ProductNotFoundException(String s){
            super(s);
        }
    }
}
