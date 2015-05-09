package com.mmrx.health.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Hashtable;

/**
 * Created by mmrx on 2015/5/7.
 */
public class BitmapCache {

    static private BitmapCache cache;
    /** 用于cache内容的存储 */
    private Hashtable<Integer,MySoftRef> hashRefs;
    /** 垃圾Reference的队列（所引用的对象已经被回收，则将该引用存入队列中） */
    private ReferenceQueue<Bitmap> q;
    /**
     * 继承SoftReference，使得每一个实例都具有可识别的标识。
     */
    private class MySoftRef extends SoftReference<Bitmap>{
        private Integer _key = 0;

        public MySoftRef(Bitmap bmp,ReferenceQueue<Bitmap> q,int key){
            super(bmp,q);
            _key = key;
        }
    }
    //私有构造函数
    private BitmapCache(){
        hashRefs = new Hashtable<Integer,MySoftRef>();
        q = new ReferenceQueue<Bitmap>();
    }

    /**
     * 取得缓存器实例
     * */
    public static BitmapCache getInstance(){
        if(cache == null)
            cache = new BitmapCache();
        return cache;
    }

    /**
     * 以软引用的方式对一个Bitmap对象的实例进行引用并保存该引用
     */
    private void addCacheBitmap(Bitmap bmp,Integer key){
        //清除垃圾引用
        cleanCache();
        MySoftRef ref = new MySoftRef(bmp,q,key);
        hashRefs.put(key,ref);
    }
    /**
     * 依据所指定的drawable下的图片资源ID号（可以根据自己的需要从网络或本地path下获取）
     * 重新获取相应Bitmap对象的实例
     */
    public Bitmap getBitmap(int resid,Context context){
        Bitmap bmp = null;
        //缓存中是否有该bitmap实例的软引用，如果有，则从软引用中取得
        if(hashRefs.containsKey(resid)){
            MySoftRef ref = (MySoftRef)hashRefs.get(resid);
            bmp = (Bitmap)ref.get();
        }
        //如果没有软引用，或者从软引用中得到的实例是null，重新构建一个实例，
        //并保存这个新建实例的软引用
        if(bmp == null){
            bmp = BitmapFactory.decodeStream(context.getResources().openRawResource(resid));
            this.addCacheBitmap(bmp,resid);
        }
        return bmp;
    }

    /**
     * 依据所指定的drawable下的图片资源ID号（可以根据自己的需要从网络或本地path下获取）
     * 重新获取相应Bitmap添加了毛玻璃效果的对象的实例
     */
    public Bitmap getBitmapBlur(int resid,Context context,int radius,boolean canReuseInBitmap){
        Bitmap bmp = null;
        //缓存中是否有该bitmap实例的软引用，如果有，则从软引用中取得
        if(hashRefs.containsKey(resid)){
            MySoftRef ref = (MySoftRef)hashRefs.get(resid);
            bmp = (Bitmap)ref.get();
        }
        //如果没有软引用，或者从软引用中得到的实例是null，重新构建一个实例，
        //并保存这个新建实例的软引用
        if(bmp == null){
            bmp = FastBlur.doBlur(BitmapFactory.decodeStream(context.getResources().openRawResource(resid)),radius,canReuseInBitmap);
            this.addCacheBitmap(bmp,resid);
        }
        return bmp;
    }


    private void cleanCache(){
        MySoftRef ref = null;
        while ( (ref=(MySoftRef)q.poll())!=null )
            hashRefs.remove(ref._key);
    }

    /**
     * 清除Cache内的全部内容
     */
    public void clearCache(){
        cleanCache();
        hashRefs.clear();
        System.gc();
        System.runFinalization();
    }
 }
