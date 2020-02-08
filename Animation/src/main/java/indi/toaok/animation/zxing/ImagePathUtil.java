package indi.toaok.animation.zxing;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Yun
 * @version 1.0  2020-01-19.
 */
public class ImagePathUtil {

    /**
     * 通过反射从uri获取文件
     *
     * @param context 上下文
     * @param uri     uri
     * @return uri 对应的file
     */
    public static File fileFromUri(Context context, Uri uri) {
        try {
            List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS);
            if (packs != null) {
                String fileProviderClassName = FileProvider.class.getName();
                for (PackageInfo pack : packs) {
                    ProviderInfo[] providers = pack.providers;
                    if (providers != null) {
                        for (ProviderInfo provider : providers) {
                            if (uri.getAuthority().equals(provider.authority)) {
                                if (provider.name.equalsIgnoreCase(fileProviderClassName)) {
                                    Class<FileProvider> fileProviderClass = FileProvider.class;
                                    try {
                                        Method getPathStrategy = fileProviderClass.getDeclaredMethod("getPathStrategy", Context.class, String.class);
                                        getPathStrategy.setAccessible(true);
                                        Object invoke = getPathStrategy.invoke(null, context, uri.getAuthority());
                                        if (invoke != null) {
                                            String PathStrategyStringClass = FileProvider.class.getName() + "$PathStrategy";
                                            Class<?> PathStrategy = Class.forName(PathStrategyStringClass);
                                            Method getFileForUri = PathStrategy.getDeclaredMethod("getFileForUri", Uri.class);
                                            getFileForUri.setAccessible(true);
                                            Object invoke1 = getFileForUri.invoke(invoke, uri);
                                            if (invoke1 instanceof File) {
                                                return (File) invoke1;
                                            }
                                        }
                                    } catch (NoSuchMethodException e) {
                                        e.printStackTrace();
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                }
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过反射从uri获取文件路径
     *
     * @param context 上下文
     * @param uri     uri
     * @return uri对应的filepath
     */
    public static String pathFromUri(Context context, Uri uri) {
        String filePath = null;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            File file = fileFromUri(context, uri);
            if (file != null) {
                filePath = file.getPath();
            }
        } else {
            if (filePath == null) {
                filePath = getAbsoluteUriPath(context, uri);
            }
            if (filePath == null) {//7.0以下没有FileProciderUrl,特殊处理
                filePath = getAbsolutePathFromNoStandardUri(uri);
            }
            if (filePath == null) {
                filePath = uri.getPath();
            }
        }

        return filePath;
    }

    public final static String SDCARD_MNT = "/mnt/sdcard";
    public final static String SDCARD = Environment.getExternalStorageDirectory().getPath();
    /**
     * <b>BuildTime:</b> 2014年10月23日<br>
     * <b>Description:</b> <br>
     *
     * @param mUri
     * @return
     */
    public static String getAbsolutePathFromNoStandardUri(Uri mUri) {
        String filePath = null;

        String mUriString = mUri.toString();
        mUriString = Uri.decode(mUriString);

        String pre1 = "file://" + SDCARD + File.separator;
        String pre2 = "file://" + SDCARD_MNT + File.separator;

        if (mUriString.startsWith(pre1)) {
            filePath = Environment.getExternalStorageDirectory().getPath()
                    + File.separator + mUriString.substring(pre1.length());
        } else if (mUriString.startsWith(pre2)) {
            filePath = Environment.getExternalStorageDirectory().getPath()
                    + File.separator + mUriString.substring(pre2.length());
        }
        return filePath;
    }

    /**
     * <b>BuildTime:</b> 2014年10月23日<br>
     * <b>Description:</b> Use the uri to get the file path<br>
     *
     * @param c
     * @param uri
     * @return
     */
    public static String getAbsoluteUriPath(Context c, Uri uri) {
        String imgPath = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        ContentResolver resolver = c.getContentResolver();
        Cursor cursor = resolver.query(uri, proj, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                imgPath = cursor.getString(column_index);
            }
        }

        return imgPath;
    }

}
