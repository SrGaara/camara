package com.srgaara.drawer.camara;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    ImageView CDMX;
    private static Bitmap Bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CDMX=(ImageView)findViewById(R.id.imageView2);
        CDMX.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                abrircamara();
            }
        });
    }

    public void abrircamara(){
        Intent Intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(Intent,0);
    }

    protected void onActivityResul(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        InputStream Stream=null;
        if(requestCode ==0 && resultCode==RESULT_OK){
            try{
                if(Bitmap !=null){
                    Bitmap.recycle();
                }
                Stream=getContentResolver().openInputStream(data.getData())
                Bitmap= BitmapFactory.decodeStream(Stream);
                CDMX.setImageBitmap(resizeImage(this, Bitmap,700, 600));
            }catch (FileNotFoundException e){
                e.printStackTrace();

            }finally {
                if (Stream !=null)
                    try{
                    Stream.close();
                    }catch (IOException e){
                    e.printStackTrace();
                    }
            }
        }
    }

    private static Bitmap resizeImage(Context context, Bitmap bmpOriginal, float newith, float newheight ){
        Bitmap novoBmp=null;
        int w=bmpOriginal.getWidth();
        int h=bmpOriginal.getHeight();
        float densityfactor = context.getResources().getDisplayMetrics().density;
        float novow=newith * densityfactor;
        float novoh=newheight * densityfactor;

        float sacalaw = novow / w;
        float scalah= novoh/h;

        Matrix matrix= new Matrix();
        matrix.postScale(sacalaw,scalah);
        novoBmp= Bitmap .createBitmap(bmpOriginal,0,0,w,h,matrix,true);
    return novoBmp;
    }
    public void girarFoto(View v){
        CDMX.setRotation(90);
    }
    public  void voltearFoto (View v){
        CDMX.setRotation(0);
    }
public boolean onCreateoptionmenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
}
}
