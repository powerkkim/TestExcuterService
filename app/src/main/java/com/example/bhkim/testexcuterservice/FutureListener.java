package com.example.bhkim.testexcuterservice;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


/**
 * Created by bhkim on 16. 1. 26..
 */
public class FutureListener<V> {

    public FutureListener( final Future<V> future, final FutureCallback<V> listen )  {
        Runnable ra = new Runnable() {
            @Override
            public void run() {
                final V value;

                try {
                    // TODO(user): (Before Guava release), validate that this
                    // is the thing for IE.
                    value = getUninterruptibly(future);
                } catch (ExecutionException e) {
                    listen.onFailure(e.getCause());
                    return;
                } catch (RuntimeException e) {
                    listen.onFailure(e);
                    return;
                } catch (Error e) {
                    listen.onFailure(e);
                    return;
                }

                listen.onSuccess(value);
            }
        };

        Thread th = new Thread(ra);
        th.start();
//        Handler handle = new Handler();
//        handle.post(ra);

    }

    public <V> V getUninterruptibly(Future<V> future)
            throws ExecutionException {
        boolean interrupted = false;
        try {
            while (true) {
                try {
                    return future.get();
                } catch (InterruptedException e) {
                    interrupted = true;
                }
            }
        } finally {
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }


    public interface FutureCallback<V> {
        /**
         * Invoked with the result of the {@code Future} computation when it is
         * successful.
         */
        void onSuccess(@Nullable V result);

        /**
         * Invoked when a {@code Future} computation fails or is canceled.
         *
         * <p>If the future's {@link Future#get() get} method throws an {@link
         * ExecutionException}, then the cause is passed to this method. Any other
         * thrown object is passed unaltered.
         */
        void onFailure(Throwable t);
    }

}
