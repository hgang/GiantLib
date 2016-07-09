package com.pisen.baselib.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.pisen.baselib.utils.FileUtil;
import com.pisen.baselib.utils.Toastor;

import java.io.File;
import java.io.IOException;

public class FileUtilTestActivity extends Activity implements View.OnClickListener {

    private static final String ROOT_PATH = Environment.getExternalStorageDirectory().getPath();
    private TextView txtListFile;
    private TextView txtWriteToFile;
    private TextView txtCopy;
    private TextView txtDel;
    private TextView txtGetFileSize;
    private TextView txtGetFileExt;
    private TextView txtMove;
    private TextView txtRename;
    private TextView txtFileExist;
    private TextView txtGetFileContent;
    private TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_util_test);
        initView();
        setListener();
    }

    private void initView() {
        txtListFile = (TextView) findViewById(R.id.txt_list_sub_file_name);
        txtWriteToFile = (TextView) findViewById(R.id.txt_write_to_file);
        txtCopy = (TextView) findViewById(R.id.txt_copy);
        txtDel = (TextView) findViewById(R.id.txt_del);
        txtGetFileSize = (TextView) findViewById(R.id.txt_get_file_size);
        txtGetFileExt = (TextView) findViewById(R.id.txt_get_file_ext);
        txtMove = (TextView) findViewById(R.id.txt_move);
        txtFileExist = (TextView) findViewById(R.id.txt_file_exist);
        txtRename = (TextView) findViewById(R.id.txt_rename);
        txtGetFileContent = (TextView) findViewById(R.id.txt_get_file_content);
        txtResult = (TextView) findViewById(R.id.txt_result);

    }

    private void setListener() {
        txtListFile.setOnClickListener(this);
        txtWriteToFile.setOnClickListener(this);
        txtCopy.setOnClickListener(this);
        txtDel.setOnClickListener(this);
        txtGetFileSize.setOnClickListener(this);
        txtGetFileExt.setOnClickListener(this);
        txtMove.setOnClickListener(this);
        txtFileExist.setOnClickListener(this);
        txtRename.setOnClickListener(this);
        txtGetFileContent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_list_sub_file_name:
                doListSubFileNames();
                break;
            case R.id.txt_write_to_file:
                writeToFile();
                break;
            case R.id.txt_copy:
                doCopy();
                break;
            case R.id.txt_del:
                doDel();
                break;
            case R.id.txt_get_file_size:
                doGetFileSize();
                break;
            case R.id.txt_get_file_ext:
                doGetFileExt();
                break;
            case R.id.txt_move:
                doMove();
                break;
            case R.id.txt_file_exist:
                fileExist();
                break;
            case R.id.txt_rename:
                doRename();
                break;
            case R.id.txt_get_file_content:
                doGetFileContent();
                break;
        }
    }

    private void doGetFileContent() {
        String filePath = ROOT_PATH + File.separator + "test.txt";
        String fileContent = FileUtil.getFileOutputString(filePath);
        String info = "文件" + filePath + "_文件内容:"+fileContent;
        Toastor.build(this).text(info).show();
        txtResult.setText(info);
    }

    private void doRename() {
        String filePath = ROOT_PATH + File.separator + "test.txt";
        String newValue = /*ROOT_PATH + File.separator +*/ "aass.txt";
        boolean result = FileUtil.renameFile(filePath, newValue);
        Toastor.build(this).text(result?"重命名成功":"重命名失败").show();
    }

    private void fileExist() {
        String filePath = ROOT_PATH + File.separator + "test.txt";
        String info = FileUtil.fileExists(filePath) ? "文件" + filePath + "存在" : "文件" + filePath + "不存在";
        Toastor.build(this).text(info).show();
        txtResult.setText(info);
    }

    private void doMove() {
        // TODO  切换为测试手机上 存在的文件路径
        String resPath = ROOT_PATH + File.separator +"test.txt";
        String desPath = ROOT_PATH + File.separator+"360";

        FileUtil.move(resPath, desPath);
        StringBuffer sb = new StringBuffer();
        for (String s : FileUtil.listFileNames(new File(desPath))) {
            sb.append(s).append(":");
        }
        txtResult.setText("移动后目标文件夹当前文件列表:"+sb.toString());
    }

    private void doGetFileExt() {
        String filePath = ROOT_PATH + File.separator + "test.txt";
        String info = "文件:" + filePath + "后缀名:"
                + FileUtil.getFileExt(filePath) + "_无后缀文件名:"
                + FileUtil.getFileNameWithoutExt(filePath);
        Toastor.build(this).text(info).show();
        txtResult.setText(info);

    }

    private void doGetFileSize() {
        long size = FileUtil.getFileSize(ROOT_PATH + File.separator + "test.txt");
        String info = "文件大小:" + size + "_sizeFormat:" + FileUtil.formatFileSize(size);
        Toastor.build(this).text(info);
        txtResult.setText(info);

    }

    private void doDel() {
        String info = FileUtil.deleteFile(ROOT_PATH + File.separator + "test.txt") ? "删除成功" : "删除失败";
        Toastor.build(this).text(info).show();
    }

    private void doCopy() {
        // TODO  切换为测试手机上 存在的文件路径
        String resPath = ROOT_PATH + File.separator +"at";
        String desPath = ROOT_PATH + File.separator+"vipshop";
        String result = "";
        try {
            FileUtil.copy(resPath, desPath);
            result = "拷贝成功";
        } catch (IOException e) {
            e.printStackTrace();
            result = "拷贝失败";
        }
        Toastor.build(this).text(result).show();
    }

    private void writeToFile() {
        File desFile = FileUtil.createFile(ROOT_PATH, "test.txt");
        String info = FileUtil.writeFile(desFile, "123", false) ? "写入成功" : "写入失败";
        Toastor.build(this).text(info).show();
    }

    private void doListSubFileNames() {
        // 测试获取文件夹下所有文件名
        StringBuffer stringBuffer = new StringBuffer();
        for (String s : FileUtil.listFileNames(new File(ROOT_PATH))) {
            stringBuffer.append(s).append(":");
        }
        txtResult.setText(stringBuffer.toString());
    }
}
