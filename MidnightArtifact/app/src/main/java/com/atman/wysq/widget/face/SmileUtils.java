package com.atman.wysq.widget.face;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.widget.EditText;
import android.widget.TextView;

import com.atman.wysq.R;
import com.base.baselibs.util.LogUtils;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/28 13:35
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class SmileUtils {

    public static SpannableString getEmotionContent( final Context context, final TextView tv, String source) {
        SpannableString spannableString = new SpannableString(source);
        Resources res = context.getResources();

        String regexEmotion = "\\/+([\u4e00-\u9fa5]{1})";
        Pattern patternEmotion = Pattern.compile(regexEmotion);
        Matcher matcherEmotion = patternEmotion.matcher(spannableString);

        while (matcherEmotion.find()) {
            // 获取匹配到的具体字符
            String key = matcherEmotion.group();
//            LogUtils.e("key:"+key);
            // 匹配字符串的开始位置
            int start = matcherEmotion.start();
            // 利用表情名字获取到对应的图片
            Integer imgRes = getImgByName(key);
            if (imgRes != null && imgRes!=0) {
                // 压缩表情图片
                int size = (int) tv.getTextSize();
                size = size + (size*2)/10;
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inPreferredConfig = Bitmap.Config.RGB_565;
                Bitmap bitmap = BitmapFactory.decodeResource(res, imgRes, opts);
                Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);

                ImageSpan span = new ImageSpan(context, scaleBitmap);
                spannableString.setSpan(span, start, start + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
            }
        }

        String regexEmotion2 = "\\/+([A-Z\\u4e00-\\u9fa5]{2})";
        Pattern patternEmotion2 = Pattern.compile(regexEmotion2);
        Matcher matcherEmotion2 = patternEmotion2.matcher(spannableString);

        while (matcherEmotion2.find()) {
            // 获取匹配到的具体字符
            String key = matcherEmotion2.group();
//            LogUtils.e("key:"+key);
            // 匹配字符串的开始位置
            int start = matcherEmotion2.start();
            // 利用表情名字获取到对应的图片
            Integer imgRes = getImgByName(key);
            if (imgRes != null && imgRes!=0) {
                // 压缩表情图片
                int size = (int) tv.getTextSize();
                size = size + (size*2)/10;
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inPreferredConfig = Bitmap.Config.RGB_565;
                Bitmap bitmap = BitmapFactory.decodeResource(res, imgRes, opts);
                Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);

                ImageSpan span = new ImageSpan(context, scaleBitmap);
                spannableString.setSpan(span, start, start + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
            }
        }

        String regexEmotion3 = "\\/+([\u4e00-\u9fa5]{3})";
        Pattern patternEmotion3 = Pattern.compile(regexEmotion3);
        Matcher matcherEmotion3 = patternEmotion3.matcher(spannableString);

        while (matcherEmotion3.find()) {
            // 获取匹配到的具体字符
            String key = matcherEmotion3.group();
//            LogUtils.e("key:"+key);
            // 匹配字符串的开始位置
            int start = matcherEmotion3.start();
            // 利用表情名字获取到对应的图片
            Integer imgRes = getImgByName(key);
            if (imgRes != null && imgRes!=0) {
                // 压缩表情图片
                int size = (int) tv.getTextSize();
                size = size + (size*2)/10;
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inPreferredConfig = Bitmap.Config.RGB_565;
                Bitmap bitmap = BitmapFactory.decodeResource(res, imgRes, opts);
                Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);

                ImageSpan span = new ImageSpan(context, scaleBitmap);
                spannableString.setSpan(span, start, start + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
            }
        }
        return spannableString;
    }

    public static SpannableString getEmotionContent(final Context context, final EditText tv, String source) {
        SpannableString spannableString = new SpannableString(source);
        Resources res = context.getResources();

        String regexEmotion = "\\/+([\u4e00-\u9fa5]{1})";
        Pattern patternEmotion = Pattern.compile(regexEmotion);
        Matcher matcherEmotion = patternEmotion.matcher(spannableString);

        while (matcherEmotion.find()) {
            // 获取匹配到的具体字符
            String key = matcherEmotion.group();
//            LogUtils.e("key:"+key);
            // 匹配字符串的开始位置
            int start = matcherEmotion.start();
            // 利用表情名字获取到对应的图片
            Integer imgRes = getImgByName(key);
            if (imgRes != null && imgRes!=0) {
                // 压缩表情图片
                int size = (int) tv.getTextSize();
                size = size + (size*4)/10;
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inPreferredConfig = Bitmap.Config.RGB_565;
                Bitmap bitmap = BitmapFactory.decodeResource(res, imgRes, opts);
                Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);

                ImageSpan span = new ImageSpan(context, scaleBitmap);
                spannableString.setSpan(span, start, start + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
            }
        }

        String regexEmotion2 = "\\/+([A-Z\\u4e00-\\u9fa5]{2})";
        Pattern patternEmotion2 = Pattern.compile(regexEmotion2);
        Matcher matcherEmotion2 = patternEmotion2.matcher(spannableString);

        while (matcherEmotion2.find()) {
            // 获取匹配到的具体字符
            String key = matcherEmotion2.group();
//            LogUtils.e("key:"+key);
            // 匹配字符串的开始位置
            int start = matcherEmotion2.start();
            // 利用表情名字获取到对应的图片
            Integer imgRes = getImgByName(key);
            if (imgRes != null && imgRes!=0) {
                // 压缩表情图片
                int size = (int) tv.getTextSize();
                size = size + (size*2)/10;
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inPreferredConfig = Bitmap.Config.RGB_565;
                Bitmap bitmap = BitmapFactory.decodeResource(res, imgRes, opts);
                Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);

                ImageSpan span = new ImageSpan(context, scaleBitmap);
                spannableString.setSpan(span, start, start + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
            }
        }

        String regexEmotion3 = "\\/+([\u4e00-\u9fa5]{3})";
        Pattern patternEmotion3 = Pattern.compile(regexEmotion3);
        Matcher matcherEmotion3 = patternEmotion3.matcher(spannableString);

        while (matcherEmotion3.find()) {
            // 获取匹配到的具体字符
            String key = matcherEmotion3.group();
//            LogUtils.e("key:"+key);
            // 匹配字符串的开始位置
            int start = matcherEmotion3.start();
            // 利用表情名字获取到对应的图片
            Integer imgRes = getImgByName(key);
            if (imgRes != null && imgRes!=0) {
                // 压缩表情图片
                int size = (int) tv.getTextSize();
                size = size + (size*2)/10;
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inPreferredConfig = Bitmap.Config.RGB_565;
                Bitmap bitmap = BitmapFactory.decodeResource(res, imgRes, opts);
                Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);

                ImageSpan span = new ImageSpan(context, scaleBitmap);
                spannableString.setSpan(span, start, start + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
            }
        }
        return spannableString;
    }

    public static boolean checkFace (Context context, String source) {

        String key = "";

        SpannableString spannableString = new SpannableString(source);
        String regexEmotion = "\\/+([\u4e00-\u9fa5]{1,3})";
        Pattern patternEmotion = Pattern.compile(regexEmotion);
        Matcher matcherEmotion = patternEmotion.matcher(spannableString);

        while (matcherEmotion.find()) {
            key = matcherEmotion.group();
        }

        return key!="";
    }

    private static Integer getImgByName(String key) {
        if (key.equals("/调皮")) {
            return R.mipmap.face_0;
        } else if (key.equals("/擦汗")) {
            return R.mipmap.face_1;
        } else if (key.equals("/猪头")) {
            return R.mipmap.face_2;
        } else if (key.equals("/呲牙")) {
            return R.mipmap.face_3;
        } else if (key.equals("/惊讶")) {
            return R.mipmap.face_4;
        } else if (key.equals("/微笑")) {
            return R.mipmap.face_5;
        } else if (key.equals("/得意")) {
            return R.mipmap.face_6;
        } else if (key.equals("/发怒")) {
            return R.mipmap.face_7;
        } else if (key.equals("/撇嘴")) {
            return R.mipmap.face_8;
        } else if (key.equals("/害羞")) {
            return R.mipmap.face_9;
        } else if (key.equals("/难过")) {
            return R.mipmap.face_10;
        } else if (key.equals("/流泪")) {
            return R.mipmap.face_11;
        } else if (key.equals("/发呆")) {
            return R.mipmap.face_12;
        } else if (key.equals("/偷笑")) {
            return R.mipmap.face_13;
        } else if (key.equals("/坏笑")) {
            return R.mipmap.face_14;
        } else if (key.equals("/色")) {
            return R.mipmap.face_15;
        } else if (key.equals("/吐")) {
            return R.mipmap.face_16;
        } else if (key.equals("/大兵")) {
            return R.mipmap.face_17;
        } else if (key.equals("/流汗")) {
            return R.mipmap.face_18;
        } else if (key.equals("/白眼")) {
            return R.mipmap.face_19;
        } else if (key.equals("/惊恐")) {
            return R.mipmap.face_20;
        } else if (key.equals("/闭嘴")) {
            return R.mipmap.face_21;
        } else if (key.equals("/骷髅")) {
            return R.mipmap.face_22;
        } else if (key.equals("/傲慢")) {
            return R.mipmap.face_23;
        } else if (key.equals("/鄙视")) {
            return R.mipmap.face_24;
        } else if (key.equals("/大哭")) {
            return R.mipmap.face_25;
        } else if (key.equals("/奋斗")) {
            return R.mipmap.face_26;
        } else if (key.equals("/尴尬")) {
            return R.mipmap.face_27;
        } else if (key.equals("/鼓掌")) {
            return R.mipmap.face_28;
        } else if (key.equals("/哈欠")) {
            return R.mipmap.face_29;
        } else if (key.equals("/憨笑")) {
            return R.mipmap.face_30;
        } else if (key.equals("/饥饿")) {
            return R.mipmap.face_31;
        } else if (key.equals("/可爱")) {
            return R.mipmap.face_32;
        } else if (key.equals("/可怜")) {
            return R.mipmap.face_33;
        } else if (key.equals("/抠鼻")) {
            return R.mipmap.face_34;
        } else if (key.contains("/左哼") || key.equals("/左哼哼")) {
            return R.mipmap.face_35;
        } else if (key.contains("/右哼") || key.equals("/右哼哼")) {
            return R.mipmap.face_36;
        } else if (key.equals("/示爱")) {
            return R.mipmap.face_37;
        } else if (key.equals("/玫瑰")) {
            return R.mipmap.face_38;
        } else if (key.equals("/凋谢")) {
            return R.mipmap.face_39;
        } else if (key.equals("/酷")) {
            return R.mipmap.face_40;
        } else if (key.contains("/快哭") || key.equals("/快哭了")) {
            return R.mipmap.face_41;
        } else if (key.equals("/困")) {
            return R.mipmap.face_42;
        } else if (key.equals("/冷汗")) {
            return R.mipmap.face_43;
        } else if (key.equals("/敲打")) {
            return R.mipmap.face_44;
        } else if (key.equals("/亲亲")) {
            return R.mipmap.face_45;
        } else if (key.contains("/糗大") || key.equals("/糗大了")) {
            return R.mipmap.face_46;
        } else if (key.equals("/睡")) {
            return R.mipmap.face_47;
        } else if (key.equals("/委屈")) {
            return R.mipmap.face_48;
        } else if (key.equals("/吓")) {
            return R.mipmap.face_49;
        } else if (key.equals("/嘘")) {
            return R.mipmap.face_50;
        } else if (key.equals("/疑问")) {
            return R.mipmap.face_51;
        } else if (key.equals("/阴险")) {
            return R.mipmap.face_52;
        } else if (key.equals("/晕")) {
            return R.mipmap.face_53;
        } else if (key.equals("/再见")) {
            return R.mipmap.face_54;
        } else if (key.equals("/折磨")) {
            return R.mipmap.face_55;
        } else if (key.equals("/咒骂")) {
            return R.mipmap.face_56;
        } else if (key.equals("/抓狂")) {
            return R.mipmap.face_57;
        } else if (key.equals("/NO")) {
            return R.mipmap.face_58;
        } else if (key.equals("/OK")) {
            return R.mipmap.face_59;
        } else if (key.equals("/爱你")) {
            return R.mipmap.face_60;
        } else if (key.equals("/差劲")) {
            return R.mipmap.face_61;
        } else if (key.equals("/勾引")) {
            return R.mipmap.face_62;
        } else if (key.equals("/抱拳")) {
            return R.mipmap.face_63;
        } else if (key.equals("/胜利")) {
            return R.mipmap.face_64;
        } else if (key.equals("/握手")) {
            return R.mipmap.face_65;
        } else if (key.equals("/拳头")) {
            return R.mipmap.face_66;
        } else if (key.equals("/强")) {
            return R.mipmap.face_67;
        } else if (key.equals("/弱")) {
            return R.mipmap.face_68;
        } else if (key.equals("/爱心")) {
            return R.mipmap.face_69;
        } else if (key.equals("/心碎")) {
            return R.mipmap.face_70;
        } else if (key.equals("/太阳")) {
            return R.mipmap.face_71;
        } else if (key.equals("/月亮")) {
            return R.mipmap.face_72;
        } else if (key.equals("/篮球")) {
            return R.mipmap.face_73;
        } else if (key.equals("/足球")) {
            return R.mipmap.face_74;
        } else if (key.equals("/便便")) {
            return R.mipmap.face_75;
        } else if (key.equals("/菜刀")) {
            return R.mipmap.face_76;
        } else if (key.equals("/蛋糕")) {
            return R.mipmap.face_77;
        } else if (key.equals("/刀")) {
            return R.mipmap.face_78;
        } else if (key.equals("/饭")) {
            return R.mipmap.face_79;
        } else if (key.equals("/咖啡")) {
            return R.mipmap.face_80;
        } else if (key.equals("/炸弹")) {
            return R.mipmap.face_81;
        } else if (key.equals("/礼品")) {
            return R.mipmap.face_82;
        } else if (key.equals("/啤酒")) {
            return R.mipmap.face_83;
        } else if (key.equals("/瓢虫")) {
            return R.mipmap.face_84;
        } else if (key.equals("/乒乓")) {
            return R.mipmap.face_85;
        } else if (key.equals("/闪电")) {
            return R.mipmap.face_86;
        } else if (key.equals("/衰")) {
            return R.mipmap.face_87;
        } else if (key.equals("/西瓜")) {
            return R.mipmap.face_88;
        } else {
            return 0;
        }
    }
}
