package com.example.bhkim.testexcuterservice;

import android.util.Log;

import java.util.concurrent.Callable;

/**
 * Created by bhkim on 16. 1. 26..
 */
public class MyCallable implements Callable<Object>
{
    private String mTag ;
    public MyCallable( String tag)
    {
        mTag = tag;
    }

    @Override
    public String call() throws Exception
    {
        if(Thread.currentThread().isInterrupted())
        {
            Log.d("MyCallable", "call 1 interrupted :"+mTag);

            return "Interrupted 1";
        }

        Log.d("MyCallable", "call 1 request :"+mTag);

        Thread.sleep(5000);

        if(Thread.currentThread().isInterrupted())
        {
            Log.d("MyCallable", "call 2 interrupted :"+mTag);

            return "Interrupted 2";
        }

        Log.d("MyCallable", "call 2 Success :"+mTag);
        return "Success";
    }
}