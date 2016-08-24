package com.atman.wysq.model.response;

import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/22 13:54
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class GetDataModel {
    /**
     * result : 1
     * body : [{"city_id":110000,"city_name":"北京","parent_id":1,"pinyin":"bei jing"},{"city_id":120000,"city_name":"天津","parent_id":1,"pinyin":"tian jin"},{"city_id":130000,"city_name":"河北省","parent_id":1,"pinyin":"he bei sheng"},{"city_id":140000,"city_name":"山西省","parent_id":1,"pinyin":"shan xi sheng"},{"city_id":150000,"city_name":"内蒙古自治区","parent_id":1,"pinyin":"nei meng gu zi zhi qu"},{"city_id":210000,"city_name":"辽宁省","parent_id":1,"pinyin":"liao ning sheng"},{"city_id":220000,"city_name":"吉林省","parent_id":1,"pinyin":"ji lin sheng"},{"city_id":230000,"city_name":"黑龙江省","parent_id":1,"pinyin":"hei long jiang sheng"},{"city_id":310000,"city_name":"上海","parent_id":1,"pinyin":"shang hai"},{"city_id":320000,"city_name":"江苏省","parent_id":1,"pinyin":"jiang su sheng"},{"city_id":330000,"city_name":"浙江省","parent_id":1,"pinyin":"zhe jiang sheng"},{"city_id":340000,"city_name":"安徽省","parent_id":1,"pinyin":"an hui sheng"},{"city_id":350000,"city_name":"福建省","parent_id":1,"pinyin":"fu jian sheng"},{"city_id":360000,"city_name":"江西省","parent_id":1,"pinyin":"jiang xi sheng"},{"city_id":370000,"city_name":"山东省","parent_id":1,"pinyin":"shan dong sheng"},{"city_id":410000,"city_name":"河南省","parent_id":1,"pinyin":"he nan sheng"},{"city_id":420000,"city_name":"湖北省","parent_id":1,"pinyin":"hu bei sheng"},{"city_id":430000,"city_name":"湖南省","parent_id":1,"pinyin":"hu nan sheng"},{"city_id":440000,"city_name":"广东省","parent_id":1,"pinyin":"guang dong sheng"},{"city_id":450000,"city_name":"广西壮族自治区","parent_id":1,"pinyin":"guang xi zhuang zu zi zhi qu"},{"city_id":460000,"city_name":"海南省","parent_id":1,"pinyin":"hai nan sheng"},{"city_id":500000,"city_name":"重庆","parent_id":1,"pinyin":"chong qing"},{"city_id":510000,"city_name":"四川省","parent_id":1,"pinyin":"si chuan sheng"},{"city_id":520000,"city_name":"贵州省","parent_id":1,"pinyin":"gui zhou sheng"},{"city_id":530000,"city_name":"云南省","parent_id":1,"pinyin":"yun nan sheng"},{"city_id":540000,"city_name":"西藏自治区","parent_id":1,"pinyin":"xi zang zi zhi qu"},{"city_id":610000,"city_name":"陕西省","parent_id":1,"pinyin":"shan xi sheng"},{"city_id":620000,"city_name":"甘肃省","parent_id":1,"pinyin":"gan su sheng"},{"city_id":630000,"city_name":"青海省","parent_id":1,"pinyin":"qing hai sheng"},{"city_id":640000,"city_name":"宁夏回族自治区","parent_id":1,"pinyin":"ning xia hui zu zi zhi qu"},{"city_id":650000,"city_name":"新疆维吾尔自治区","parent_id":1,"pinyin":"xin jiang wei wu er zi zhi qu"},{"city_id":710000,"city_name":"台湾省","parent_id":1,"pinyin":"tai wan sheng"},{"city_id":810000,"city_name":"香港特别行政区","parent_id":1,"pinyin":"xiang gang te bie xing zheng qu"},{"city_id":820000,"city_name":"澳门特别行政区","parent_id":1,"pinyin":"ao men te bie xing zheng qu"},{"city_id":990000,"city_name":"海外","parent_id":1,"pinyin":"hai wai"}]
     */

    private String result;
    /**
     * city_id : 110000
     * city_name : 北京
     * parent_id : 1
     * pinyin : bei jing
     */

    private List<BodyEntity> body;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<BodyEntity> getBody() {
        return body;
    }

    public void setBody(List<BodyEntity> body) {
        this.body = body;
    }

    public static class BodyEntity {
        private int city_id;
        private String city_name;
        private int parent_id;
        private String pinyin;

        public int getCity_id() {
            return city_id;
        }

        public void setCity_id(int city_id) {
            this.city_id = city_id;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }
    }
}
