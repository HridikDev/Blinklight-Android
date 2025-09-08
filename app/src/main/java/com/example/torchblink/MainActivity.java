package com.example.torchblink;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraAccessException;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    Button btn;

    EditText editText;
    private  CameraManager cameraManager;
    private String CameraId;

    private Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btn=findViewById(R.id.button);
        editText=findViewById(R.id.editTextText);

        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            if(cameraManager!=null){
                CameraId = cameraManager.getCameraIdList()[0];
            }
        }catch (CameraAccessException e){
            Toast toast = null;
            toast.makeText((Context) MainActivity.this, (CharSequence) e,Toast.LENGTH_SHORT).show();
        }
        btn.setOnClickListener(v->{blinkTorch(Integer.parseInt(editText.getText().toString()));});
    }

    private void blinkTorch(int times) {
        final int delay = 500;
        for(int i=0;i<times;i++){
            int finalI = i;
            handler.postDelayed(()->{
                try {
                    Boolean state= (finalI%2==0);
                    cameraManager.setTorchMode(CameraId,state);
                }catch (CameraAccessException e){
                    Toast toast = null;
                    toast.makeText((Context) MainActivity.this, (CharSequence) e,Toast.LENGTH_SHORT).show();
                }
            },i*delay);
        }
    }


}