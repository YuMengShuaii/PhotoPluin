package com.enation.javashop.photoutils.uitl;


import android.net.Uri;
import android.os.Environment;

import com.enation.javashop.photoutils.app.TakePhoto;
import com.enation.javashop.photoutils.compress.CompressConfig;
import com.enation.javashop.photoutils.model.CropOptions;
import com.enation.javashop.photoutils.model.LubanOptions;
import com.enation.javashop.photoutils.model.TakePhotoOptions;

import java.io.File;

/**
 * Created by LDD on 17/4/11.
 */

public class RxGetPhotoUtils {
    private TakePhoto takePhoto;
    private Uri uri;
    private TakePhotoOptions.Builder builder=new TakePhotoOptions.Builder();
    private static RxGetPhotoUtils instance;

    public static RxGetPhotoUtils init(TakePhotoinf takePhoto){
        if (instance==null){
            instance = new RxGetPhotoUtils();
        }
        instance.config(takePhoto.getTakePhoto());
        return instance;
    }

    private RxGetPhotoUtils() {

    }

    private void config (TakePhoto takePhoto){
        this.takePhoto = takePhoto;
        File file=new File(Environment.getExternalStorageDirectory(), "/javaShop/"+System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists())file.getParentFile().mkdirs();
        uri = Uri.fromFile(file);
        builder.setWithOwnGallery(true);
        takePhoto.setTakePhotoOptions(builder.create());
    }

    public void getPhotos(int limit,boolean isUseCorp){
          if (isUseCorp){
              takePhoto.onPickMultipleWithCrop(limit,getCropOptions(false,false,800,800));
          }else{
              takePhoto.onPickMultiple(limit);
          }
        takePhoto=null;
    }
    public void getPhotoForCarema(boolean isUseCorp){
        if(isUseCorp){
            takePhoto.onPickFromCaptureWithCrop(uri,getCropOptions(false,false,800,800));
        }else {
            takePhoto.onPickFromCapture(uri);
        }
        takePhoto=null;
    }

    public void getPhotoFromFile(boolean isUseCorp){
        if (isUseCorp){
            takePhoto.onPickFromDocumentsWithCrop(uri,getCropOptions(false,false,800,800));
        }else{
            takePhoto.onPickFromDocuments();
        }
        takePhoto=null;
    }

    public void getPhotoFromGallery(boolean isUseCorp){
        if (isUseCorp){
            takePhoto.onPickFromGalleryWithCrop(uri,getCropOptions(false,false,800,800));
        }else{
            takePhoto.onPickFromGallery();
        }
        takePhoto=null;
    }

    public RxGetPhotoUtils useSystemGallery(){
        builder.setCorrectImage(true);
        takePhoto.setTakePhotoOptions(builder.create());
        return instance;
    }

    public RxGetPhotoUtils configCompress(boolean isUseLuban,boolean isShowPressbar,boolean isSaveBigImg,int maxSize,int width,int height){
        CompressConfig config;
        if(!isUseLuban){
            config=new CompressConfig.Builder()
                    .setMaxSize(maxSize)
                    .setMaxPixel(width>=height? width:height)
                    .enableReserveRaw(isSaveBigImg)
                    .create();
        }else {
            LubanOptions option=new LubanOptions.Builder()
                    .setMaxHeight(height)
                    .setMaxWidth(width)
                    .setMaxSize(maxSize)
                    .create();
            config= CompressConfig.ofLuban(option);
        }
        config.enableReserveRaw(isSaveBigImg);
        takePhoto.onEnableCompress(config,isShowPressbar);
        return instance;
    }

    private CropOptions getCropOptions(boolean isUseMyCrop, boolean isUseWeight, int height, int width){
        CropOptions.Builder builder=new CropOptions.Builder();
        if (isUseWeight){
            builder.setAspectX(width).setAspectY(height);
        }else{
            builder.setOutputX(width).setOutputY(height);
        }
        builder.setWithOwnCrop(isUseMyCrop);
        return builder.create();
    }
}
