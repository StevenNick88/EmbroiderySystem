package com.gxu.embroidery.utils;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Administrator on 2015/2/16.
 */
public class ImgUtils {


    // 实用工具方法getThumbnail接收四个实参：MediaType、表示图像位置的Uri、用于与设备文件系统交互的ContentResolver
    // 和指定Bitmap配置的BitmapFactory.Options对象。
    public static Bitmap getThumbnail(Uri uri,ContentResolver cr,  int kind,
                                      BitmapFactory.Options options) {
        Bitmap bitmap = null;
        //从Uri中抽取将加载缩略图的图像id。
        int id = Integer.parseInt(uri.getLastPathSegment());
        //根据MediaItem.MediaType，使用Android MediaStore来获得对应的缩略图。

        //MediaStore.Images.Thumbnails类为此提供了自己的实用工具方法getThumbnail。在提供的三个实参中，
        //ContentResolver用于与设备的文件系统交互，图像的id为希望加载的缩略图类型，而BitmapFactory.Options
        //指定了Bitmap的配置。       Thumbnails.MICRO_KIND表示缩略图的分辨率为96X96
//        bitmap = MediaStore.Images.Thumbnails.getThumbnail(cr, id,
//                MediaStore.Images.Thumbnails.MICRO_KIND, options);
        bitmap = MediaStore.Images.Thumbnails.getThumbnail(cr, id,
                kind, options);
        //然后，返回这个bitmap
        return bitmap;
    }

    public static void loadImage(final ImageCallBack callBack, final  String img_path){
        final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                callBack.getBitmap((Bitmap) msg.obj);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bitmap bitmap=BitmapFactory.decodeStream(new URL(img_path).openStream());
//                    Bitmap bitmap=decodeSampledBitmapFromInputStream(new URL(img_path).openStream(),100,100);
//                    Drawable drawable=Drawable.createFromStream(new URL(img_path).openStream(),"");

                    Message message=Message.obtain();
                    message.obj=bitmap;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public interface ImageCallBack{
//        public  void getDrawable(Drawable drawable);
        public  void getBitmap(Bitmap bitmap);
    }


    public static Bitmap decodeSampledBitmapFromInputStream(
            InputStream inputStream,  int width, int height) {
        // 给定的BitmapFactory设置解码的参数
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 从解码器中获得原始图片的宽高，而避免申请内存空间
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream,new Rect(),options);
        options.inSampleSize = calculateInSampleSize(options, width, height);
        options.inJustDecodeBounds = false;
        return  BitmapFactory.decodeStream(inputStream, new Rect(), options);
    }

    /**
     * 指定输出图片的缩放比例
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 获得原始图片的宽高
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        int inSimpleSize = 1;
        if (imageHeight > reqHeight || imageWidth > reqWidth) {

            // 计算压缩的比例：分为宽高比例
            final int heightRatio = Math.round((float) imageHeight
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) imageWidth
                    / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSimpleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSimpleSize;
    }

}
