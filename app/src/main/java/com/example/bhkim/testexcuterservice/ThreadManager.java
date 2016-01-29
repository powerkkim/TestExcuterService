package com.example.bhkim.testexcuterservice;


import android.util.Log;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class ThreadManager {
    ExecutorService es;
    
    public ThreadManager(){
        //es = Executors.newFixedThreadPool(5);
        es = Executors.newCachedThreadPool();
    }
    
    public Future<Object> addFuture(FutureTask<Object> futuretask){
        return (Future<Object>)es.submit(futuretask);
    }

    public Future<Object> addCallable(Callable<Object> futuretask){
        return (Future<Object>)es.submit(futuretask);
    }


    public void shutdown()
    {
        es.shutdown();
        try {
            if (!es.awaitTermination(1, TimeUnit.SECONDS)) {

                Log.d("ThreadTest","shutdown 1");
                List<Runnable> ll =  es.shutdownNow();
                for (Runnable aa : ll) {
                    
                }
                
                if (!es.awaitTermination(1, TimeUnit.SECONDS)) {
                    Log.d("ThreadTest","shutdown awaitTermination");
                }
                Log.d("ThreadTest","shutdown isShutdown:"+es.isShutdown());
                Log.d("ThreadTest","shutdown isTerminated:" + es.isTerminated());
            }
        } catch (InterruptedException e1) {
            e1.printStackTrace();
            es.shutdownNow();
            Log.d("ThreadTest","InterruptedException");
            Thread.currentThread().interrupt();
        }
        Log.d("ThreadTest","shutdown End");
    }
    
    /*
	protected void testThreadStart1() {
		RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();
		ThreadFactory threadFactory = Executors.defaultThreadFactory();

		ThreadPoolExecutor threadPool = new ThreadPoolExecutor( 3, 5, 2, TimeUnit.SECONDS,
				//new ArrayBlockingQueue<Runnable>(2),
				new LinkedBlockingQueue<Runnable>(),
				threadFactory,
				rejectionHandler);
		Log.i("ThreadTest", "testThreadStart1 start");
		
        for( int i = 0; i < 10; i++ )
        {
            threadPool.execute( new Runnable()
            {
                public void run()
                {
                	Log.i("ThreadTest", "Thread " + Thread.currentThread().getName() + " Start \n" );
                    
                    try{ Thread.sleep( 10000 ); } catch( Exception e ) {}
                    Log.i("ThreadTest", "Thread " + Thread.currentThread().getName() + " End \n");
                }
            } );
        	
        	threadPool.execute(new WorkerThread("LinkedBlockingQueue cmd"+i));
        }
 
        try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //shut down the pool
        threadPool.shutdown();
        Log.i("ThreadTest", "testThreadStart1 end");
	}
     */
}

