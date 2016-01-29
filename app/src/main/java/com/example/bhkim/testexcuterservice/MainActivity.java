package com.example.bhkim.testexcuterservice;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.Future;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvPrint ;
    private Future mfutue;

    private int nCount = 0;
    private ThreadManager tmanager = new ThreadManager();
//
//    private ListeningExecutorService executor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
    }

    private void initView() {
        tvPrint = (TextView)findViewById(R.id.textView);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                ExcuteServiceSample1();
                break;
            case R.id.button2:
                ExcuteServiceSample2();
                break;
//            case R.id.button3:
//                test_guava_future();
//                break;
//            case R.id.button4:
//                test_guava_future_stop();
//                break;
        }
    }

    private void ExcuteServiceSample1() {

        MyCallable mycall = new MyCallable(String.valueOf(nCount++));
//        FutureTask<Object> task1 = new FutureTask<Object>(mycall);

        mfutue = tmanager.addCallable(mycall);

        FutureListener<String> fl = new FutureListener(mfutue, new FutureListener.FutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("bhkim", "future onSuccess:" + result);

                if(Looper.myLooper() == Looper.getMainLooper()) {
                    Log.d("bhkim", "[success] : MainTHread");
                }
                else{
                    Log.d("bhkim", "[success] : BackTHread");
                }
            }

            @Override
            public void onFailure(Throwable thrown) {
                Log.d("bhkim", "future onFailure:" + thrown.getMessage());
            }
        });
    }

    private void ExcuteServiceSample2(){

        tmanager.shutdown();
//        try {
//            mfutue.get(1000, TimeUnit.MILLISECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (TimeoutException e) {
//            e.printStackTrace();
//            Log.d("bhkim", "future idDone:" + mfutue.isDone());
//            Log.d("bhkim", "future isCancelled:" + mfutue.isCancelled());
//            mfutue.cancel(true);
//            Log.d("bhkim", "future idDone:" + mfutue.isDone());
//            Log.d("bhkim", "future isCancelled 2:" + mfutue.isCancelled());
//        }


    }

//    public void test_guava_future() {
//        Log.d("bhkim", "===guava future===");
//
//         executor = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
//
////        Callable<String> asyncTask = new Callable<String>() {
////            @Override
////            public String call() throws Exception {
////                Random random = new Random();
////                int number = random.nextInt(5);
////
////                Thread.sleep(3000);
////                return "async : " + number;
////            }
////        };
//
//        MyCallable mycall = new MyCallable(String.valueOf(nCount++));
//
//        ListenableFuture<Object> listenableFuture = executor.submit(mycall);
//
//        Futures.addCallback(listenableFuture, new FutureCallback<Object>() {
//
//            @Override
//            public void onSuccess(Object result) {
//                Log.d("bhkim", "[success] : " + (String)result);
//                if(Looper.myLooper() == Looper.getMainLooper()) {
//                    Log.d("bhkim", "[success] : MainTHread");
//                }
//                else{
//                    Log.d("bhkim", "[success] : BackTHread");
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable thrown) {
//
//                Log.d("bhkim", "[onFailure] : " + thrown.getMessage());
//            }
//        });
//
////        try {
////            String result = (String)listenableFuture.get();
////            Log.d("bhkim", "result: "+result);
////        } catch (ExecutionException e) {
////            e.printStackTrace();
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
//
////        executor.shutdown();
//    }
//
//    public void test_guava_future_stop() {
//        Log.d("bhkim", "===test_guava_future_stop===");
//        executor.shutdown();
//
//        try {
//            if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
//
//                Log.d("ThreadTest","shutdown 1");
//                List<Runnable> ll =  executor.shutdownNow();
//                for (Runnable aa : ll) {
//
//                }
//
//                if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
//                    Log.d("ThreadTest","shutdown awaitTermination");
//                }
//                Log.d("ThreadTest","shutdown isShutdown:"+executor.isShutdown());
//                Log.d("ThreadTest","shutdown isTerminated:" + executor.isTerminated());
//            }
//        } catch (InterruptedException e1) {
//            e1.printStackTrace();
//            executor.shutdownNow();
//            Log.d("ThreadTest","InterruptedException");
//            Thread.currentThread().interrupt();
//        }
//        Log.d("ThreadTest", "shutdown End");
//    }
}
