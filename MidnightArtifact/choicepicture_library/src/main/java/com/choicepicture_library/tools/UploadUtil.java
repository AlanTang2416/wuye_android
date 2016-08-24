package com.choicepicture_library.tools;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

//上传工具类 支持上传文件和参数
public class UploadUtil {
	private static UploadUtil uploadUtil;
	private static final String BOUNDARY =  UUID.randomUUID().toString(); // 边界标识 随机生成
	private static final String PREFIX = "--";
	private static final String LINE_END = "\r\n";
	private static final String CONTENT_TYPE = "multipart/form-data"; // 内容类型

	private UploadUtil() {}
	/**
	 * 单例模式获取上传工具类
	 * @return
	 */
	public static UploadUtil getInstance() {
		if (null == uploadUtil) {
			uploadUtil = new UploadUtil();
		}
		return uploadUtil;
	}

	private static final String TAG = "UploadUtil";
	private int readTimeOut = 10 * 1000; // 读取超时
	private int connectTimeout = 10 * 1000; // 超时时间
	private static int requestTime = 0;	//请求使用多长时间
	private static final String CHARSET = "utf-8"; // 设置编码
	public static final int UPLOAD_SUCCESS_CODE = 1; //上传成功
	public static final int UPLOAD_FILE_NOT_EXISTS_CODE = 2;//文件不存在
	//服务器出错
	public static final int UPLOAD_SERVER_ERROR_CODE = 3;
	protected static final int WHAT_TO_UPLOAD = 1;
	protected static final int WHAT_UPLOAD_DONE = 2;
	protected static final int MSG_ERROR = 6;

	Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case MSG_ERROR:
					onUploadProcessListener.onUploadFail(msg.getData().getString("error"));
					break;
			}
			super.handleMessage(msg);
		}
	};

	/**
	 * android上传文件到服务器
	 * 
	 * @param filePath
	 *            需要上传的文件的路径
	 * @param RequestURL
	 *            请求的URL
	 */
	public void uploadFile(List<String> filePath, String RequestURL, Map<String, String> param) {
		if (filePath == null) {
			sendMessage(UPLOAD_FILE_NOT_EXISTS_CODE,"文件不存在");
			return;
		}
		try {
			List<File> filePath_list = new ArrayList<File>();
			for(int i=0;i<filePath.size();i++){
				File file = new File(filePath.get(i));
				filePath_list.add(file);
			}
			uploadFileList(filePath_list, RequestURL, param);
		} catch (Exception e) {
			sendMessage(UPLOAD_FILE_NOT_EXISTS_CODE,"文件不存在");
			e.printStackTrace();
			return;
		}
	}

	/**
	 * android上传文件到服务器
	 * 
	 * @param file
	 *            需要上传的文件
	 * @param RequestURL
	 *            请求的URL
	 */
	public void uploadFileList(final List<File> file, final String RequestURL, final Map<String, String> param) {

		new Thread(new Runnable() {  //开启线程上传文件
			 
			public void run() {
				toUploadFile(file, RequestURL, param);
			}
		}).start();
		
	}

	private void toUploadFile(List<File> file, String RequestURL,
							  Map<String, String> param) {
		String result = null;
		requestTime= 0;

		try {
			URL url = new URL(RequestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(readTimeOut);
			conn.setConnectTimeout(connectTimeout);
			conn.setDoInput(true); //允许输入流
			conn.setDoOutput(true); //允许输出流
			conn.setUseCaches(false); //不允许使用缓存
			conn.setRequestMethod("POST"); //请求方式
			conn.setRequestProperty("Charset", CHARSET);
			//设置编码
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);

			/** * 当文件不为空，把文件包装并且上传 */
			OutputStream outputSteam=conn.getOutputStream();
			DataOutputStream dos = new DataOutputStream(outputSteam);
			for(int i=0;i<file.size();i++){
				StringBuffer sb = new StringBuffer();
				sb.append(PREFIX);
				sb.append(BOUNDARY); sb.append(LINE_END);
				/**
				 * 这里重点注意：
				 * name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
				 * filename是文件的名字，包含后缀名的 比如:abc.png
				 */
				String name = "files"+i+"_name";
				sb.append("Content-Disposition: form-data; name=\""+name+"\"; filename=\""+getFileName(file.get(i).toString())+"\""+LINE_END);
				sb.append("Content-Type: application/octet-stream; charset="+CHARSET+LINE_END);
				sb.append(LINE_END);
				dos.write(sb.toString().getBytes());
				InputStream is = Bimp.revitionImage(file.get(i).toString());
				byte[] bytes = new byte[1024];
				int len = 0;
				while((len=is.read(bytes))!=-1) {
					dos.write(bytes, 0, len);
//					Log.e(">>>>len","len:"+len);
				}
//				Log.e(">>>>is","is:"+Bimp.revitionImage(file.get(i).toString()).available());
				is.close();
				dos.write(LINE_END.getBytes());

			}

			byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
			dos.write(end_data);
			dos.flush();

			/**
			 * 获取响应码 200=成功
			 * 当响应成功，获取响应的流
			 */
			int res = conn.getResponseCode();
			Log.e(TAG, "response code:"+res);
			if(res==200) {
				Log.e(TAG, "request success");
				InputStream input = conn.getInputStream();
				StringBuffer sb1 = new StringBuffer();
				int ss;
				while ((ss = input.read()) != -1) {
					sb1.append((char) ss);
				}
				result = sb1.toString();
				Log.e(TAG, "result : " + result);
				sendMessage(UPLOAD_SUCCESS_CODE, result);
				return;
			} else {
				Log.e(TAG, "request error");
				sendFailMessage("上传失败：code=" + res);
				return;
			}
		} catch (MalformedURLException e) {
			sendFailMessage("上传失败：error=" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			sendFailMessage("上传失败：error=" + e.getMessage());
			e.printStackTrace();
		}
	}

	//发送上传结果
	private void sendMessage(int responseCode,String responseMessage) {
		onUploadProcessListener.onUploadDone(responseCode, responseMessage);
	}
	private void sendFailMessage(String responseMessage) {
		Message message=new Message();
		Bundle bundle=new Bundle();
		bundle.putString("error", responseMessage);
		message.setData(bundle);//bundle传值，耗时，效率低
		message.what=MSG_ERROR;//标志是哪个线程传数据
		myHandler.sendMessage(message);//发送message信息
	}
	
	//自定义的回调函数，用到回调上传文件是否完成
	public static interface OnUploadProcessListener {
		/**
		 * 上传响应
		 * @param responseCode
		 * @param message
		 */
		void onUploadDone(int responseCode, String message);
		/**
		 * 上传中
		 * @param uploadSize
		 */
		void onUploadProcess(int uploadSize);
		/**
		 * 准备上传
		 * @param fileSize
		 */
		void initUpload(int fileSize);
		/**
		 * 上传失败
		 * @param e
		 */
		void onUploadFail(String e);
	}
	private OnUploadProcessListener onUploadProcessListener;
	

	public void setOnUploadProcessListener(
			OnUploadProcessListener onUploadProcessListener) {
		this.onUploadProcessListener = onUploadProcessListener;
	}

	public int getReadTimeOut() {
		return readTimeOut;
	}

	public void setReadTimeOut(int readTimeOut) {
		this.readTimeOut = readTimeOut;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	//获取上传使用的时间
	public static int getRequestTime() {
		return requestTime;
	}

	public static interface uploadProcessListener{
		
	}
	
	public String getFileName(String pathandname){
        
        int start=pathandname.lastIndexOf("/");  
//        int end=pathandname.lastIndexOf(".");  
        if(start!=-1){  
            return pathandname.substring(start+1);    
        }else{  
            return null;  
        }  
          
    }


}
