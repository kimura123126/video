package com.example.dell1.playvideotest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playMusic = (Button) findViewById(R.id.playMusic);
        Button pauseMusic = (Button) findViewById(R.id.pauseMusic);
        Button stopMusic = (Button) findViewById(R.id.stopMusic);
        Button loopMusic = (Button) findViewById(R.id.loopMusic);
        playMusic.setOnClickListener(this);
        pauseMusic.setOnClickListener(this);
        stopMusic.setOnClickListener(this);
        loopMusic.setOnClickListener(this);



        videoView = (VideoView) findViewById(R.id.video_view);
        Button play = (Button) findViewById(R.id.play);
        Button pause = (Button) findViewById(R.id.pause);
        Button replay = (Button) findViewById(R.id.replay);
        Button loop = (Button) findViewById(R.id.loop);
        Button stop = (Button) findViewById(R.id.stop);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        replay.setOnClickListener(this);
        loop.setOnClickListener(this);
        stop.setOnClickListener(this);

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission
                .WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },1);
        }else {
            initMediaPlayer();
            initVideoPath();

        }
    }

    private void initMediaPlayer(){
        try{
            File file = new File(Environment.getExternalStorageDirectory(),"music.mp3");
            //   Log.d("路径",Environment.getExternalStorageDirectory()+"");
            //   Log.d("路径",file.getPath());
            Log.i("音乐文件路径", file.getPath());
            mediaPlayer.setDataSource(file.getPath());
            mediaPlayer.prepare();
        }catch (Exception e){
            e.printStackTrace();
            //    Log.d("提示",e.getMessage());
        }
    }



    private void initVideoPath(){
        try{
            File file = new File(Environment.getExternalStorageDirectory(),"dahu12.mp4");
            //   Log.d("路径",Environment.getExternalStorageDirectory()+"");
            //   Log.d("路径",file.getPath());
            videoView.setVideoPath(file.getPath());
        }catch (Exception e){
            e.printStackTrace();
            //    Log.d("提示",e.getMessage());
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        switch (requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    initVideoPath();
                }else{
                    Toast.makeText(this,"拒绝权限将无法使用程序",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.playMusic:
                if(!mediaPlayer.isPlaying()){

                    mediaPlayer.start();

                }
                break;
            case R.id.loopMusic:
                if(!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                    mediaPlayer.setLooping(true);

                }
                break;
            case R.id.pauseMusic:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
                break;
            case R.id.stopMusic:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.reset();
                    initMediaPlayer();
                }
                break;




            case R.id.play:
                if(!videoView.isPlaying()){

                    videoView.start();

                }
                break;
            case R.id.loop:
                  //点击此按钮后循环播放

                    //监听视频播放完的代码
                    videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mPlayer) {
                     // TODO Auto-generated method stub
                            mPlayer.start();
                            mPlayer.setLooping(true);
                        }
                    });


                break;

            case R.id.pause:
                if(videoView.isPlaying()){
                    videoView.pause();
                }
                break;
            case R.id.replay:
                if(videoView.isPlaying()){

                    videoView.resume();
                }
                break;
            case R.id.stop:
                if(videoView.isPlaying()){//停止后再播放是重头播放

                    videoView.stopPlayback();
                    initVideoPath();

                }
                break;
            default:
                break;
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(videoView !=null ){

          videoView.suspend();
        }
        if(mediaPlayer !=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }


    }


}
