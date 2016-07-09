package com.pisen.baselib.utils;

import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * 文件操作辅助工具类
 *
 * @author chenkun
 * @date   16/2/18 下午4:43
 */

public class FileUtil {

    /**
     * 获取目录下所有文件名
     * 只获取一级目录下得所有文件名 非递归
     *
     * @param parentDir 目标文件夹
     * @return
     */
    public static List<String> listFileNames(File parentDir) {
        List<String> names = null;
        if (parentDir != null && parentDir.isDirectory()) {
            names = Arrays.asList(parentDir.list());
        }
        return names;
    }

    /**
     * 写文件
     *
     * @param file   目标文件
     * @param data   写入内容
     * @param append 末尾追加写入
     * @return
     */
    public static boolean writeFile(File file, String data, boolean append) {
        boolean result = false;
        if (!TextUtils.isEmpty(data) && file != null) {
            FileWriter fw = null;
            try {
                if (!file.exists()) file.createNewFile();
                fw = new FileWriter(file);
                if (append) fw.append(data);
                else fw.write(data);

                fw.flush();
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fw != null) fw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        return result;
    }


    /**
     * 判断文件是否存在
     *
     * @param path
     * @return
     */
    public static boolean fileExists(String path) {
        if (path == null) {
            return false;
        }

        File file = new File(path);
        if (file != null && file.exists()) {
            return true;
        }
        return false;
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName
     * @return
     */
    public static String getFileExt(String fileName) {
        if (TextUtils.isEmpty(fileName) || !fileName.contains(".")) {
            return "";
        }
        int point = fileName.lastIndexOf('.');
        return fileName.substring(point + 1);
    }


    /**
     * 获取文件大小
     *
     * @param filePath
     * @return
     */
    public static long getFileSize(String filePath) {
        long size = 0;
        File file = new File(filePath);
        if (file != null && file.exists()) {
            size = file.length();
        }
        return size;
    }


    /**
     * 转换文件大小
     *
     * @param fileS
     * @return B/KB/MB/GB
     */
    public static String formatFileSize(long fileS) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS <= 0) {
            fileSizeString = "0.00KB";
        } else if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 获取目录文件大小
     *
     * @param dir
     * @return
     */
    public static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getDirSize(file); // 递归调用继续统计
            }
        }
        return dirSize;
    }

    /**
     * 获取文件名 不带后缀
     * @param fileName
     * @return  返回不带后缀的文件名
     */
    public static String getFileNameWithoutExt(String fileName) {
        return fileName != null && fileName.contains(".") ? fileName
                .substring(0, fileName.lastIndexOf(".")) : (fileName != null ? fileName : "");
    }

    /**
     * 复制文件（夹）到一个目标文件夹
     *
     * @param resFilePath             源文件（夹）
     * @param objFolderFilePath 目标文件夹
     * @throws IOException 异常时抛出
     */
    public static void copy(String resFilePath, String objFolderFilePath) throws IOException {
        File resFile = new File(resFilePath);
        if (!resFile.exists()) return;
        File objFolderFile = new File(objFolderFilePath);
        if (!objFolderFile.exists()) objFolderFile.mkdirs();
        if (resFile.isFile()) {
            File objFile = new File(objFolderFile.getPath() + File.separator + resFile.getName());
            //复制文件到目标地
            InputStream ins = new FileInputStream(resFile);
            FileOutputStream outs = new FileOutputStream(objFile);
            byte[] buffer = new byte[1024 * 512];
            int length;
            while ((length = ins.read(buffer)) != -1) {
                outs.write(buffer, 0, length);
            }
            ins.close();
            outs.flush();
            outs.close();
        } else {
            String objFolder = objFolderFile.getPath() + File.separator + resFile.getName();
            File _objFolderFile = new File(objFolder);
            _objFolderFile.mkdirs();
            for (File sf : resFile.listFiles()) {
                copy(sf.getPath(), objFolder);
            }
        }
    }

    /**
     * 递归删除文件目录(包括：目录里的所有文件)
     *
     * @param filePath
     * @return
     */
    public static boolean deleteDirectory(String filePath) {
        boolean result = false;
        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            if (!file.exists()) {
                return result;
            }
            if (file.isDirectory()) {
                String[] listfile = file.list();
                try {
                    for (String subFileName : listfile) {
                        File subFile = new File(filePath, subFileName);
                        if (subFile.isDirectory()) {
                            result &= deleteDirectory(subFile.getPath());
                        } else {
                            result &= subFile.delete();
                        }
                    }
                    result &= file.delete();
                    Log.i("deleteDirectory", filePath);
                } catch (Exception e) {
                    e.printStackTrace();
                    result = false;
                }
            } else {
                result = deleteFile(filePath);
            }
        }

        return result;
    }


    /**
     * 删除单个文件
     *
     * @param filePath
     *            文件绝对路径
     * @return
     */
    public static boolean deleteFile(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            File newPath = new File(filePath);
            if (newPath.isFile()) {
                Log.i("deleteFile", filePath);
                return newPath.delete();
            }
        }
        return false;
    }

    /**
     * 移动
     *
     * @param fromPath
     * @param toPath
     */
    public static void move(String fromPath, String toPath) {
        try {
            copy(fromPath,toPath);
            deleteDirectory(fromPath);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *  重命名文件
     * @param filePath  被重命名文件路径
     * @param newName   新文件名
     * @return
     */
    public static boolean renameFile(String filePath,String newName){
        boolean result = false;
        if(filePath == null || newName == null){
             return  result;
         }
         File from = new File(filePath);
         if(from.exists()){
            String newPath = filePath.substring(0, filePath.lastIndexOf(File.separator)+1) + newName;
            File  to = new File(newPath);
            result = from.renameTo(to);
         }
        return  result;
    }


    /**
     * 新建目录
     *
     * @param pathName
     * @return
     */
    public static boolean createDirectory(String pathName) {
        if (!TextUtils.isEmpty(pathName)) {
            File newPath = new File(pathName);
            return newPath.mkdir();
        }
        return false;
    }

    public static File createFile(String folderPath, String fileName) {
        File destDir = new File(folderPath);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        return new File(folderPath, fileName);
    }


    /**
     * 读取指定文件的输出
     */
    public static String getFileOutputString(String path) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path), 8192);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append("\n").append(line);
            }
            bufferedReader.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 计算SD卡的剩余空间
     *
     * @return 返回-1，说明没有安装sd卡
     */
    public static long getFreeDiskSpace() {
        String status = Environment.getExternalStorageState();
        long freeSpace = 0;
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            try {
                File path = Environment.getExternalStorageDirectory();
                StatFs stat = new StatFs(path.getPath());
                long blockSize = stat.getBlockSize();
                long availableBlocks = stat.getAvailableBlocks();
                freeSpace = availableBlocks * blockSize / 1024;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return -1;
        }
        return (freeSpace);
    }

}
