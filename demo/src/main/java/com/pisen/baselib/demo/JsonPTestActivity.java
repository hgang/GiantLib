package com.pisen.baselib.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.pisen.baselib.demo.R;
import com.pisen.baselib.utils.JsonUtil;

/**
 * FastJson 效率测试
 * @author shichuang
 * @date  2016/2/22 0022 10:57
 */
public class JsonPTestActivity extends Activity implements View.OnClickListener {

	private static final String JSON_STRING_OBJ = "[{\"S\":321061,\"T\":\"GetAttributeResp\"},{\"ERROR\":null,\"TS\":0," +
			"\"VAL\":{\"SqlList\":[{\"BatchSizeMax\":0,\"BatchSizeTotal\":0,\"ConcurrentMax\":1,\"DataSource\":\"jdbc:wrap-jdbc:filters=default," +
			"encoding:name=ds-offer:jdbc:mysql://100.10.10.10:8066/xxxx\",\"EffectedRowCount\":0,\"ErrorCount\":0,\"ExecuteCount\":5,\"FetchRowCount\":5," +
			"\"File\":null,\"ID\":2001,\"LastError\":null,\"LastTime\":1292742908178,\"MaxTimespan\":16,\"MaxTimespanOccurTime\":1292742668191," +
			"\"Name\":null," +
			"\"RunningCount\":0,\"SQL\":\"SELECT @@SQL_MODE\",\"TotalTime\":83}]}}]";

	public static final int TEST_TIMES = 10000;
	private long mStartTime = -1;
	private long mEndTime = -1;
	public Gson mGson = new Gson();

	private Button mGsonView;
	private Button mFastJsonView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_json_ptest);
		initView();
	}

	private void initView() {
		mGsonView = (Button)this.findViewById(R.id.txt_json_parse_gson);
		mFastJsonView = (Button)this.findViewById(R.id.txt_json_parse_fastjson);
		mGsonView.setOnClickListener(this);
		mFastJsonView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.txt_json_parse_gson:
				mGsonView.setEnabled(false);
				mGsonView.setText("解析进行中");
				Toast.makeText(this, "正在进行解析...", Toast.LENGTH_LONG).show();
				mStartTime = System.currentTimeMillis();
				new Thread(new ParserGsonTask()).start();
				break;
			case R.id.txt_json_parse_fastjson:
				mFastJsonView.setEnabled(false);
				mFastJsonView.setText("解析进行中");
				Toast.makeText(this, "正在进行解析...", Toast.LENGTH_LONG).show();
				mStartTime = System.currentTimeMillis();
				new Thread(new ParserFastJsonTask()).start();
				break;
			default:
				break;
		}
	}


	private class ParserGsonTask implements Runnable {

		@Override
		public void run() {
			for(int i = 0; i < TEST_TIMES; i++) {
				mGson.fromJson(JSON_STRING_OBJ, Object.class);
			}
			mEndTime = System.currentTimeMillis();
			System.gc();
			Message message = mHandler.obtainMessage();
			message.what = GSON_TEST;
			message.sendToTarget();
		}
	}

	private class ParserFastJsonTask implements Runnable {

		@Override
		public void run() {
			for(int i = 0; i < TEST_TIMES; i++) {
				JsonUtil.parseBean(JSON_STRING_OBJ,Object.class);
			}
			mEndTime = System.currentTimeMillis();
			System.gc();
			Message message = mHandler.obtainMessage();
			message.what = FASTJSON_TEST;
			message.sendToTarget();
		}
	}

	public static final int GSON_TEST = 1;
	public static final int FASTJSON_TEST = 2;

	public Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what) {
				case GSON_TEST:
					mGsonView.setText((mEndTime - mStartTime) / 1000.0f + "s");
					mGsonView.setEnabled(true);
					break;

				case FASTJSON_TEST:
					mFastJsonView.setText((mEndTime - mStartTime) / 1000.0f + "s");
					mFastJsonView.setEnabled(true);
					break;
				default:
					break;
			}
		}
	};
}

