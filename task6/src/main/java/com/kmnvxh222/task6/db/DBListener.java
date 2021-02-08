package com.kmnvxh222.task6.db;

public interface DBListener<T> {
    void onDataReceived(T data);
}
