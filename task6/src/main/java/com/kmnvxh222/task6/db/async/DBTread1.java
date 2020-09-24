package com.kmnvxh222.task6.db.async;

import com.kmnvxh222.task6.db.DBHelper;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 1.ThreadPoolExecutor + Handler
 */
class DBTread1 {
//    public DBTread1(DBHelper dbHelper){
//        super(new DBHelper(context, null));
//    }
    ThreadPoolExecutor tExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    
}
