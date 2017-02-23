package mems.jiaochuan.com.mems_2000.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	
	
	/**
	 * 打印吐司
	 * @param context	上下文环境
	 * @param msg		打印的内容
	 */
	public static void show(Context context, String msg){
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	
	
}
