package com.atman.wysq.model.response;

import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/12 13:20
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class GetTaskAllModel {
    /**
     * result : 1
     * body : [{"task_id":1,"name":"每日签到","pic":"/imageServer/prop/task/1qd.png","status":1,"repeat_type":1,"task_type":1,"coin":80,"experience":49,"prop":1,"download_url":"","url_scheme":"","create_time":1458041327000,"update_time":1428661113000,"task_complete":1,"rewarded":0},{"task_id":3,"name":"分享给QQ好友","pic":"/imageServer/prop/task/3tx.png","status":1,"repeat_type":2,"task_type":3,"share_type":1,"coin":5,"experience":599,"prop":5,"download_url":"https://itunes.apple.com/cn/app/mei-ri0yuan-gou/id921519459?mt=8","url_scheme":"","create_time":1458041336000,"update_time":1428661137000,"task_complete":0,"rewarded":0},{"task_id":4,"name":"分享给微信好友","pic":"/imageServer/prop/task/4wx.png","status":1,"repeat_type":2,"task_type":3,"share_type":2,"coin":1001,"experience":2199,"prop":3,"download_url":"https://itunes.apple.com/cn/app/ji-ying-bang-ni-fa-xian-shen/id737620988?mt=8","url_scheme":"","create_time":1458041346000,"update_time":1428661148000,"task_complete":0,"rewarded":0},{"task_id":5,"name":"分享到新浪微博","pic":"/imageServer/prop/task/5wb.png","status":1,"repeat_type":2,"task_type":3,"share_type":3,"coin":30001,"experience":0,"prop":5,"download_url":"https://itunes.apple.com/cn/app/ji-ting-zui-kuai-fa-xian-ni/id747656888?mt=8","url_scheme":"","create_time":1458041402000,"update_time":1428661164000,"task_complete":0,"rewarded":0},{"task_id":9,"name":"发帖","pic":"/imageServer/prop/task/9ft.png","status":1,"repeat_type":1,"task_type":5,"coin":20,"experience":11999,"download_url":"","url_scheme":"","create_time":1468232777000,"update_time":1438156005000,"task_complete":0,"rewarded":0},{"task_id":10,"name":"回复帖子","pic":"/imageServer/prop/task/10hf.png","status":1,"repeat_type":1,"task_type":6,"coin":20,"experience":1,"download_url":"","url_scheme":"","create_time":1468232802000,"update_time":1438156028000,"task_complete":0,"rewarded":0},{"task_id":11,"name":"购物","pic":"/imageServer/prop/task/11gw.png","status":1,"repeat_type":1,"task_type":7,"coin":1,"experience":49999,"download_url":"","url_scheme":"","create_time":1458041375000,"update_time":1438156041000,"task_complete":0,"rewarded":0},{"task_id":12,"name":"私信","pic":"/imageServer/prop/task/12sx.png","status":1,"repeat_type":1,"task_type":8,"coin":5001,"experience":99999,"download_url":"","url_scheme":"","create_time":1458041385000,"update_time":1438156065000,"task_complete":0,"rewarded":0},{"task_id":13,"name":"魔聊","pic":"/imageServer/prop/task/13mo.png","status":1,"repeat_type":1,"task_type":9,"coin":3001,"experience":4999,"download_url":"","url_scheme":"","create_time":1458041398000,"update_time":1438156108000,"task_complete":0,"rewarded":0}]
     */

    private String result;
    /**
     * task_id : 1
     * name : 每日签到
     * pic : /imageServer/prop/task/1qd.png
     * status : 1
     * repeat_type : 1
     * task_type : 1
     * coin : 80
     * experience : 49
     * prop : 1
     * download_url :
     * url_scheme :
     * create_time : 1458041327000
     * update_time : 1428661113000
     * task_complete : 1
     * rewarded : 0
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
        private int task_id;
        private String name;
        private String pic;
        private int status;
        private int repeat_type;
        private int task_type;
        private int coin;
        private int share_type;
        private int experience;
        private int prop;
        private String download_url;
        private String url_scheme;
        private long create_time;
        private long update_time;
        private int task_complete;
        private int rewarded;

        public int getShare_type() {
            return share_type;
        }

        public void setShare_type(int share_type) {
            this.share_type = share_type;
        }

        public int getTask_id() {
            return task_id;
        }

        public void setTask_id(int task_id) {
            this.task_id = task_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getRepeat_type() {
            return repeat_type;
        }

        public void setRepeat_type(int repeat_type) {
            this.repeat_type = repeat_type;
        }

        public int getTask_type() {
            return task_type;
        }

        public void setTask_type(int task_type) {
            this.task_type = task_type;
        }

        public int getCoin() {
            return coin;
        }

        public void setCoin(int coin) {
            this.coin = coin;
        }

        public int getExperience() {
            return experience;
        }

        public void setExperience(int experience) {
            this.experience = experience;
        }

        public int getProp() {
            return prop;
        }

        public void setProp(int prop) {
            this.prop = prop;
        }

        public String getDownload_url() {
            return download_url;
        }

        public void setDownload_url(String download_url) {
            this.download_url = download_url;
        }

        public String getUrl_scheme() {
            return url_scheme;
        }

        public void setUrl_scheme(String url_scheme) {
            this.url_scheme = url_scheme;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public long getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(long update_time) {
            this.update_time = update_time;
        }

        public int getTask_complete() {
            return task_complete;
        }

        public void setTask_complete(int task_complete) {
            this.task_complete = task_complete;
        }

        public int getRewarded() {
            return rewarded;
        }

        public void setRewarded(int rewarded) {
            this.rewarded = rewarded;
        }
    }
}
