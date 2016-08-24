package com.atman.wysq.utils.updateversion;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.ui.base.MyBaseActivity;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/6/7 17:32
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class UpdateVersionHelp {

    /**
     * 版本更新提示
     */
    public static void updateNewVersion(final Activity mActivity, final String updateType, final String versionName,
                                        String apkCapacity, final String updateUrl, List<String> updateList) {
        final Dialog dialog = new Dialog(mActivity, R.style.MyDialogStyleCenterFuzzy);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        window.setContentView(R.layout.dialog_update);

        TextView versionNameTv = (TextView) window.findViewById(R.id.versionname_txt);
        TextView capacityTv = (TextView) window.findViewById(R.id.capacity_txt);
        ListView listView = (ListView) window.findViewById(R.id.update_content_list);
        Button btnUpdate = (Button) window.findViewById(R.id.btn_once_update);
        Button btnAfter = (Button) window.findViewById(R.id.btn_after_operation);

        versionNameTv.setText(versionName);
        capacityTv.setText(apkCapacity);
        listView.setAdapter(new UpdateContentAdapter(mActivity, updateList));

        if (updateType.equals("1")) {
            btnAfter.setVisibility(View.GONE);
            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode,
                                     KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        return true;
                    }
                    return false;
                }
            });
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final File updateFile = createFile(versionName);
                if (updateFile != null) {
                    /**调用系统浏览器在页面中下载*/
                    Uri uri = Uri.parse(updateUrl);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    mActivity.startActivity(intent);
                } else {
                    ((MyBaseActivity)mActivity).showToast("SD卡路径错误，无法下载");
                }
                dialog.dismiss();
            }
        });
        btnAfter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static File createFile(String versionName){
        File updateDir = null;
        File updateFile = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            updateDir = new File(Environment.getExternalStorageDirectory()+ "/atman/downLoad/");
            updateFile = new File(updateDir + "/" + versionName + ".apk");

            if (!updateDir.exists()) {
                updateDir.mkdirs();
            }
            if (!updateFile.exists()) {
                try {
                    updateFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return updateFile;
    }
}
