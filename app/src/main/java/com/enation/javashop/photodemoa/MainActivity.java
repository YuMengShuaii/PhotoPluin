package com.enation.javashop.photodemoa;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.enation.javashop.photoutils.app.TakePhoto;
import com.enation.javashop.photoutils.app.TakePhotoActivity;
import com.enation.javashop.photoutils.model.TResult;
import com.enation.javashop.photoutils.uitl.RxGetPhotoUtils;
import com.enation.javashop.photoutils.uitl.TakePhotoinf;

public class MainActivity extends TakePhotoActivity {
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxGetPhotoUtils.init(MainActivity.this)
                        .configCompress(true,true,false,102400,800,800)
                        .getPhotoFromGallery(true);
            }
        });
    }

    @Override
    public void takeSuccess(TResult result) {
        imageView.setImageBitmap(BitmapFactory.decodeFile(result.getImage().getCompressPath()));
    }
}
