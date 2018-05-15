package com.hefeibus.www.hefeibus.entity;

public class ResponseEntity<T> {

    private T data;
    private ProgramStatus status;

    public ResponseEntity() {
    }


    public T getData() {
        return data;
    }

    public ResponseEntity<T> setData(T data) {
        this.data = data;
        return this;
    }

    public ProgramStatus getStatus() {
        return status;
    }

    public ResponseEntity<T> setStatus(ProgramStatus status) {
        this.status = status;
        return this;
    }
}
