package com.atman.wysq.model.response;

import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/26 16:32
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class GetMyUserIndexModel {

    private String result;

    private BodyBean body;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class BodyBean {

        private UserDetailBeanBean userDetailBean;

        private VisitorMapBean visitorMap;

        private VipInfoBean vipInfo;
        private long remainingTime;
        private boolean friend;

        private List<GoodsListBean> goodsList;

        private List<GiftListBean> giftList;

        private List<GuardlistBean> guardlist;

        private List<PropListBean> propList;

        private List<ModuelListBean> moduelList;
        private List<String> achieveList;

        public UserDetailBeanBean getUserDetailBean() {
            return userDetailBean;
        }

        public void setUserDetailBean(UserDetailBeanBean userDetailBean) {
            this.userDetailBean = userDetailBean;
        }

        public VisitorMapBean getVisitorMap() {
            return visitorMap;
        }

        public void setVisitorMap(VisitorMapBean visitorMap) {
            this.visitorMap = visitorMap;
        }

        public VipInfoBean getVipInfo() {
            return vipInfo;
        }

        public void setVipInfo(VipInfoBean vipInfo) {
            this.vipInfo = vipInfo;
        }

        public long getRemainingTime() {
            return remainingTime;
        }

        public void setRemainingTime(long remainingTime) {
            this.remainingTime = remainingTime;
        }

        public boolean isFriend() {
            return friend;
        }

        public void setFriend(boolean friend) {
            this.friend = friend;
        }

        public List<GoodsListBean> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<GoodsListBean> goodsList) {
            this.goodsList = goodsList;
        }

        public List<GiftListBean> getGiftList() {
            return giftList;
        }

        public void setGiftList(List<GiftListBean> giftList) {
            this.giftList = giftList;
        }

        public List<GuardlistBean> getGuardlist() {
            return guardlist;
        }

        public void setGuardlist(List<GuardlistBean> guardlist) {
            this.guardlist = guardlist;
        }

        public List<PropListBean> getPropList() {
            return propList;
        }

        public void setPropList(List<PropListBean> propList) {
            this.propList = propList;
        }

        public List<ModuelListBean> getModuelList() {
            return moduelList;
        }

        public void setModuelList(List<ModuelListBean> moduelList) {
            this.moduelList = moduelList;
        }

        public List<String> getAchieveList() {
            return achieveList;
        }

        public void setAchieveList(List<String> achieveList) {
            this.achieveList = achieveList;
        }

        public static class UserDetailBeanBean {
            private long userId;
            private String userName;
            private String nickName;
            private long addTime;
            /**
             * user_id : 450000168
             * integral : 205331
             * secret_type : 0
             * type : 2
             * icon : /imageServer/C029174175f689b3451f9a80f8c9c28005d0.jpg
             * sex : F
             * create_time : 1428647019000
             * update_time : 1470239397000
             * credit : 191
             * name_change : 0
             * next_level_integral : 9999999999999999
             * userLevel : 10
             * mobile : 13817673794
             * nick_name : 落霞与孤雾齐飞
             * status : 2
             * verify_status : 1
             * user_token : dPzBXpL4J9aQ3cySaQESEJ9QMcE8zfmW/LjFsMQgC1X16dCt9c4Rx/ql8KRrfIuDklLNgcU/ZLIlbRjLGqXXwtmF4MAhylgY
             * can_live_room : 1
             * gold_coin : 44328
             * charm : 150089
             * can_chat : 1
             * can_shake : 1
             * convert_coin : 72568
             * around_site : 这样你说了什么呢……一个人就是说好了……一起走过的路还长着呢。我的生活费了一个人就是这样一个好东西就要分享图片我们都市全部的确很久了？我是有的是因为自己太笨啦。这个夏天最后还是被迫在家呆板无趣和你说了句话
             * vip_level : 4
             * hotel_id : 0
             * chat_count : 161
             * accrue_coin : 513940
             */

            private UserExtBean userExt;
            private int follow_count;
            private int followed_count;
            private int favorited_count;
            private int comment_count;
            private int vipLevel;
            private String achieve_level_pic;
            private String achieve_charm_pic;
            private String achieve_gold_pic;

            public long getUserId() {
                return userId;
            }

            public void setUserId(long userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public long getAddTime() {
                return addTime;
            }

            public void setAddTime(long addTime) {
                this.addTime = addTime;
            }

            public UserExtBean getUserExt() {
                return userExt;
            }

            public void setUserExt(UserExtBean userExt) {
                this.userExt = userExt;
            }

            public int getFollow_count() {
                return follow_count;
            }

            public void setFollow_count(int follow_count) {
                this.follow_count = follow_count;
            }

            public int getFollowed_count() {
                return followed_count;
            }

            public void setFollowed_count(int followed_count) {
                this.followed_count = followed_count;
            }

            public int getFavorited_count() {
                return favorited_count;
            }

            public void setFavorited_count(int favorited_count) {
                this.favorited_count = favorited_count;
            }

            public int getComment_count() {
                return comment_count;
            }

            public void setComment_count(int comment_count) {
                this.comment_count = comment_count;
            }

            public int getVipLevel() {
                return vipLevel;
            }

            public void setVipLevel(int vipLevel) {
                this.vipLevel = vipLevel;
            }

            public String getAchieve_level_pic() {
                return achieve_level_pic;
            }

            public void setAchieve_level_pic(String achieve_level_pic) {
                this.achieve_level_pic = achieve_level_pic;
            }

            public String getAchieve_charm_pic() {
                return achieve_charm_pic;
            }

            public void setAchieve_charm_pic(String achieve_charm_pic) {
                this.achieve_charm_pic = achieve_charm_pic;
            }

            public String getAchieve_gold_pic() {
                return achieve_gold_pic;
            }

            public void setAchieve_gold_pic(String achieve_gold_pic) {
                this.achieve_gold_pic = achieve_gold_pic;
            }

            public static class UserExtBean {
                private long user_id;
                private String integral;
                private int secret_type;
                private int type;
                private String icon;
                private String sex;
                private long create_time;
                private long update_time;
                private int credit;
                private int name_change;
                private long next_level_integral;
                private int userLevel;
                private String mobile;
                private String nick_name;
                private int status;
                private int verify_status;
                private String user_token;
                private int can_live_room;
                private int gold_coin;
                private int charm;
                private int can_chat;
                private int can_shake;
                private int convert_coin;
                private String around_site;
                private int vip_level;
                private int hotel_id;
                private int chat_count;
                private int accrue_coin;

                public long getUser_id() {
                    return user_id;
                }

                public void setUser_id(long user_id) {
                    this.user_id = user_id;
                }

                public String getIntegral() {
                    return integral;
                }

                public void setIntegral(String integral) {
                    this.integral = integral;
                }

                public int getSecret_type() {
                    return secret_type;
                }

                public void setSecret_type(int secret_type) {
                    this.secret_type = secret_type;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getIcon() {
                    return icon;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }

                public String getSex() {
                    return sex;
                }

                public void setSex(String sex) {
                    this.sex = sex;
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

                public int getCredit() {
                    return credit;
                }

                public void setCredit(int credit) {
                    this.credit = credit;
                }

                public int getName_change() {
                    return name_change;
                }

                public void setName_change(int name_change) {
                    this.name_change = name_change;
                }

                public long getNext_level_integral() {
                    return next_level_integral;
                }

                public void setNext_level_integral(long next_level_integral) {
                    this.next_level_integral = next_level_integral;
                }

                public int getUserLevel() {
                    return userLevel;
                }

                public void setUserLevel(int userLevel) {
                    this.userLevel = userLevel;
                }

                public String getMobile() {
                    return mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }

                public String getNick_name() {
                    return nick_name;
                }

                public void setNick_name(String nick_name) {
                    this.nick_name = nick_name;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public int getVerify_status() {
                    return verify_status;
                }

                public void setVerify_status(int verify_status) {
                    this.verify_status = verify_status;
                }

                public String getUser_token() {
                    return user_token;
                }

                public void setUser_token(String user_token) {
                    this.user_token = user_token;
                }

                public int getCan_live_room() {
                    return can_live_room;
                }

                public void setCan_live_room(int can_live_room) {
                    this.can_live_room = can_live_room;
                }

                public int getGold_coin() {
                    return gold_coin;
                }

                public void setGold_coin(int gold_coin) {
                    this.gold_coin = gold_coin;
                }

                public int getCharm() {
                    return charm;
                }

                public void setCharm(int charm) {
                    this.charm = charm;
                }

                public int getCan_chat() {
                    return can_chat;
                }

                public void setCan_chat(int can_chat) {
                    this.can_chat = can_chat;
                }

                public int getCan_shake() {
                    return can_shake;
                }

                public void setCan_shake(int can_shake) {
                    this.can_shake = can_shake;
                }

                public int getConvert_coin() {
                    return convert_coin;
                }

                public void setConvert_coin(int convert_coin) {
                    this.convert_coin = convert_coin;
                }

                public String getAround_site() {
                    return around_site;
                }

                public void setAround_site(String around_site) {
                    this.around_site = around_site;
                }

                public int getVip_level() {
                    return vip_level;
                }

                public void setVip_level(int vip_level) {
                    this.vip_level = vip_level;
                }

                public int getHotel_id() {
                    return hotel_id;
                }

                public void setHotel_id(int hotel_id) {
                    this.hotel_id = hotel_id;
                }

                public int getChat_count() {
                    return chat_count;
                }

                public void setChat_count(int chat_count) {
                    this.chat_count = chat_count;
                }

                public int getAccrue_coin() {
                    return accrue_coin;
                }

                public void setAccrue_coin(int accrue_coin) {
                    this.accrue_coin = accrue_coin;
                }
            }
        }

        public static class VisitorMapBean {
            private int visitorSize;
            /**
             * user_id : 450000168
             * integral : 205331
             * secret_type : 0
             * type : 2
             * icon : /imageServer/C029174175f689b3451f9a80f8c9c28005d0.jpg
             * sex : F
             * create_time : 1474427434000
             * credit : 191
             * name_change : 0
             * next_level_integral : 9999999999999999
             * userLevel : 10
             * mobile : 13817673794
             * nick_name : 落霞与孤雾齐飞
             * status : 2
             * verify_status : 1
             * user_token : dPzBXpL4J9aQ3cySaQESEJ9QMcE8zfmW/LjFsMQgC1X16dCt9c4Rx/ql8KRrfIuDklLNgcU/ZLIlbRjLGqXXwtmF4MAhylgY
             * gold_coin : 44328
             * charm : 150089
             * can_chat : 1
             * can_shake : 1
             * vip_level : 4
             * chat_count : 161
             * accrue_coin : 513940
             */

            private List<VisitorListBean> visitorList;

            public int getVisitorSize() {
                return visitorSize;
            }

            public void setVisitorSize(int visitorSize) {
                this.visitorSize = visitorSize;
            }

            public List<VisitorListBean> getVisitorList() {
                return visitorList;
            }

            public void setVisitorList(List<VisitorListBean> visitorList) {
                this.visitorList = visitorList;
            }

            public static class VisitorListBean {
                private long user_id;
                private String integral;
                private int secret_type;
                private int type;
                private String icon;
                private String sex;
                private long create_time;
                private int credit;
                private int name_change;
                private long next_level_integral;
                private int userLevel;
                private String mobile;
                private String nick_name;
                private int status;
                private int verify_status;
                private String user_token;
                private int gold_coin;
                private int charm;
                private int can_chat;
                private int can_shake;
                private int vip_level;
                private int chat_count;
                private int accrue_coin;

                public long getUser_id() {
                    return user_id;
                }

                public void setUser_id(long user_id) {
                    this.user_id = user_id;
                }

                public String getIntegral() {
                    return integral;
                }

                public void setIntegral(String integral) {
                    this.integral = integral;
                }

                public int getSecret_type() {
                    return secret_type;
                }

                public void setSecret_type(int secret_type) {
                    this.secret_type = secret_type;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getIcon() {
                    return icon;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }

                public String getSex() {
                    return sex;
                }

                public void setSex(String sex) {
                    this.sex = sex;
                }

                public long getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(long create_time) {
                    this.create_time = create_time;
                }

                public int getCredit() {
                    return credit;
                }

                public void setCredit(int credit) {
                    this.credit = credit;
                }

                public int getName_change() {
                    return name_change;
                }

                public void setName_change(int name_change) {
                    this.name_change = name_change;
                }

                public long getNext_level_integral() {
                    return next_level_integral;
                }

                public void setNext_level_integral(long next_level_integral) {
                    this.next_level_integral = next_level_integral;
                }

                public int getUserLevel() {
                    return userLevel;
                }

                public void setUserLevel(int userLevel) {
                    this.userLevel = userLevel;
                }

                public String getMobile() {
                    return mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }

                public String getNick_name() {
                    return nick_name;
                }

                public void setNick_name(String nick_name) {
                    this.nick_name = nick_name;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public int getVerify_status() {
                    return verify_status;
                }

                public void setVerify_status(int verify_status) {
                    this.verify_status = verify_status;
                }

                public String getUser_token() {
                    return user_token;
                }

                public void setUser_token(String user_token) {
                    this.user_token = user_token;
                }

                public int getGold_coin() {
                    return gold_coin;
                }

                public void setGold_coin(int gold_coin) {
                    this.gold_coin = gold_coin;
                }

                public int getCharm() {
                    return charm;
                }

                public void setCharm(int charm) {
                    this.charm = charm;
                }

                public int getCan_chat() {
                    return can_chat;
                }

                public void setCan_chat(int can_chat) {
                    this.can_chat = can_chat;
                }

                public int getCan_shake() {
                    return can_shake;
                }

                public void setCan_shake(int can_shake) {
                    this.can_shake = can_shake;
                }

                public int getVip_level() {
                    return vip_level;
                }

                public void setVip_level(int vip_level) {
                    this.vip_level = vip_level;
                }

                public int getChat_count() {
                    return chat_count;
                }

                public void setChat_count(int chat_count) {
                    this.chat_count = chat_count;
                }

                public int getAccrue_coin() {
                    return accrue_coin;
                }

                public void setAccrue_coin(int accrue_coin) {
                    this.accrue_coin = accrue_coin;
                }
            }
        }

        public static class VipInfoBean {
            private int ad_info_id;
            private String name;
            private String description;
            private String ad_pic;
            private String ad_url;
            private int ad_goods_id;
            private int sort;
            private int series;
            private int type;
            private long create_time;
            private long update_time;

            public int getAd_info_id() {
                return ad_info_id;
            }

            public void setAd_info_id(int ad_info_id) {
                this.ad_info_id = ad_info_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getAd_pic() {
                return ad_pic;
            }

            public void setAd_pic(String ad_pic) {
                this.ad_pic = ad_pic;
            }

            public String getAd_url() {
                return ad_url;
            }

            public void setAd_url(String ad_url) {
                this.ad_url = ad_url;
            }

            public int getAd_goods_id() {
                return ad_goods_id;
            }

            public void setAd_goods_id(int ad_goods_id) {
                this.ad_goods_id = ad_goods_id;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getSeries() {
                return series;
            }

            public void setSeries(int series) {
                this.series = series;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
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
        }

        public static class GoodsListBean {
            private int goods_id;
            private String title;
            private String pic_img;
            private String store;
            private String price;
            private String discount_price;
            private int sales;
            private int special_edition_id;
            private int status;
            private String key_words;
            private int gold_coin;
            private int integral;
            private int chat_prop_id;
            private long update_time;
            private int goods_type;
            private int source;
            private int postage;
            private int gold_coin_price;
            private int charm_price;

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPic_img() {
                return pic_img;
            }

            public void setPic_img(String pic_img) {
                this.pic_img = pic_img;
            }

            public String getStore() {
                return store;
            }

            public void setStore(String store) {
                this.store = store;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getDiscount_price() {
                return discount_price;
            }

            public void setDiscount_price(String discount_price) {
                this.discount_price = discount_price;
            }

            public int getSales() {
                return sales;
            }

            public void setSales(int sales) {
                this.sales = sales;
            }

            public int getSpecial_edition_id() {
                return special_edition_id;
            }

            public void setSpecial_edition_id(int special_edition_id) {
                this.special_edition_id = special_edition_id;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getKey_words() {
                return key_words;
            }

            public void setKey_words(String key_words) {
                this.key_words = key_words;
            }

            public int getGold_coin() {
                return gold_coin;
            }

            public void setGold_coin(int gold_coin) {
                this.gold_coin = gold_coin;
            }

            public int getIntegral() {
                return integral;
            }

            public void setIntegral(int integral) {
                this.integral = integral;
            }

            public int getChat_prop_id() {
                return chat_prop_id;
            }

            public void setChat_prop_id(int chat_prop_id) {
                this.chat_prop_id = chat_prop_id;
            }

            public long getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(long update_time) {
                this.update_time = update_time;
            }

            public int getGoods_type() {
                return goods_type;
            }

            public void setGoods_type(int goods_type) {
                this.goods_type = goods_type;
            }

            public int getSource() {
                return source;
            }

            public void setSource(int source) {
                this.source = source;
            }

            public int getPostage() {
                return postage;
            }

            public void setPostage(int postage) {
                this.postage = postage;
            }

            public int getGold_coin_price() {
                return gold_coin_price;
            }

            public void setGold_coin_price(int gold_coin_price) {
                this.gold_coin_price = gold_coin_price;
            }

            public int getCharm_price() {
                return charm_price;
            }

            public void setCharm_price(int charm_price) {
                this.charm_price = charm_price;
            }
        }

        public static class GiftListBean {
            private int gift_id;
            private String name;
            private String paopao;
            private String pic_url;
            private int price;
            private int charm;
            private int status;
            private long create_time;
            private long update_time;
            private int type;
            private String gray_pic_url;

            public int getGift_id() {
                return gift_id;
            }

            public void setGift_id(int gift_id) {
                this.gift_id = gift_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPaopao() {
                return paopao;
            }

            public void setPaopao(String paopao) {
                this.paopao = paopao;
            }

            public String getPic_url() {
                return pic_url;
            }

            public void setPic_url(String pic_url) {
                this.pic_url = pic_url;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getCharm() {
                return charm;
            }

            public void setCharm(int charm) {
                this.charm = charm;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
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

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getGray_pic_url() {
                return gray_pic_url;
            }

            public void setGray_pic_url(String gray_pic_url) {
                this.gray_pic_url = gray_pic_url;
            }
        }

        public static class GuardlistBean {
            private long user_id;
            private String integral;
            private int secret_type;
            private int type;
            private String icon;
            private String sex;
            private long create_time;
            private int credit;
            private int name_change;
            private long next_level_integral;
            private int userLevel;
            private String mobile;
            private String nick_name;
            private int status;
            private int verify_status;
            private int can_live_room;
            private int gold_coin;
            private int charm;
            private int can_chat;
            private int can_shake;
            private int convert_coin;
            private int vip_level;
            private int chat_count;
            private int accrue_coin;

            public long getUser_id() {
                return user_id;
            }

            public void setUser_id(long user_id) {
                this.user_id = user_id;
            }

            public String getIntegral() {
                return integral;
            }

            public void setIntegral(String integral) {
                this.integral = integral;
            }

            public int getSecret_type() {
                return secret_type;
            }

            public void setSecret_type(int secret_type) {
                this.secret_type = secret_type;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public long getCreate_time() {
                return create_time;
            }

            public void setCreate_time(long create_time) {
                this.create_time = create_time;
            }

            public int getCredit() {
                return credit;
            }

            public void setCredit(int credit) {
                this.credit = credit;
            }

            public int getName_change() {
                return name_change;
            }

            public void setName_change(int name_change) {
                this.name_change = name_change;
            }

            public long getNext_level_integral() {
                return next_level_integral;
            }

            public void setNext_level_integral(long next_level_integral) {
                this.next_level_integral = next_level_integral;
            }

            public int getUserLevel() {
                return userLevel;
            }

            public void setUserLevel(int userLevel) {
                this.userLevel = userLevel;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getVerify_status() {
                return verify_status;
            }

            public void setVerify_status(int verify_status) {
                this.verify_status = verify_status;
            }

            public int getCan_live_room() {
                return can_live_room;
            }

            public void setCan_live_room(int can_live_room) {
                this.can_live_room = can_live_room;
            }

            public int getGold_coin() {
                return gold_coin;
            }

            public void setGold_coin(int gold_coin) {
                this.gold_coin = gold_coin;
            }

            public int getCharm() {
                return charm;
            }

            public void setCharm(int charm) {
                this.charm = charm;
            }

            public int getCan_chat() {
                return can_chat;
            }

            public void setCan_chat(int can_chat) {
                this.can_chat = can_chat;
            }

            public int getCan_shake() {
                return can_shake;
            }

            public void setCan_shake(int can_shake) {
                this.can_shake = can_shake;
            }

            public int getConvert_coin() {
                return convert_coin;
            }

            public void setConvert_coin(int convert_coin) {
                this.convert_coin = convert_coin;
            }

            public int getVip_level() {
                return vip_level;
            }

            public void setVip_level(int vip_level) {
                this.vip_level = vip_level;
            }

            public int getChat_count() {
                return chat_count;
            }

            public void setChat_count(int chat_count) {
                this.chat_count = chat_count;
            }

            public int getAccrue_coin() {
                return accrue_coin;
            }

            public void setAccrue_coin(int accrue_coin) {
                this.accrue_coin = accrue_coin;
            }
        }

        public static class PropListBean {
            private int chat_prop_id;
            private String name;
            private String description;
            private String icon;
            private String icon_gray;
            private int goods_id;
            private String paopao;
            private int status;
            private int price;
            private int is_unlock;
            private long create_time;
            private long update_time;
            private int user_chat_prop_id;
            private String pic_img;

            public int getChat_prop_id() {
                return chat_prop_id;
            }

            public void setChat_prop_id(int chat_prop_id) {
                this.chat_prop_id = chat_prop_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getIcon_gray() {
                return icon_gray;
            }

            public void setIcon_gray(String icon_gray) {
                this.icon_gray = icon_gray;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public String getPaopao() {
                return paopao;
            }

            public void setPaopao(String paopao) {
                this.paopao = paopao;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getIs_unlock() {
                return is_unlock;
            }

            public void setIs_unlock(int is_unlock) {
                this.is_unlock = is_unlock;
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

            public int getUser_chat_prop_id() {
                return user_chat_prop_id;
            }

            public void setUser_chat_prop_id(int user_chat_prop_id) {
                this.user_chat_prop_id = user_chat_prop_id;
            }

            public String getPic_img() {
                return pic_img;
            }

            public void setPic_img(String pic_img) {
                this.pic_img = pic_img;
            }
        }

        public static class ModuelListBean {
            private int user_module_auth_id;
            private int module_id;
            private String module_name;
            private String module_image;
            private String miss_image;
            private int need_level;
            private int sort;
            private int type;
            private int status;
            private String show_title;
            private String show_image;
            private String show_description;
            private long create_time;
            private long update_time;

            public int getUser_module_auth_id() {
                return user_module_auth_id;
            }

            public void setUser_module_auth_id(int user_module_auth_id) {
                this.user_module_auth_id = user_module_auth_id;
            }

            public int getModule_id() {
                return module_id;
            }

            public void setModule_id(int module_id) {
                this.module_id = module_id;
            }

            public String getModule_name() {
                return module_name;
            }

            public void setModule_name(String module_name) {
                this.module_name = module_name;
            }

            public String getModule_image() {
                return module_image;
            }

            public void setModule_image(String module_image) {
                this.module_image = module_image;
            }

            public String getMiss_image() {
                return miss_image;
            }

            public void setMiss_image(String miss_image) {
                this.miss_image = miss_image;
            }

            public int getNeed_level() {
                return need_level;
            }

            public void setNeed_level(int need_level) {
                this.need_level = need_level;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getShow_title() {
                return show_title;
            }

            public void setShow_title(String show_title) {
                this.show_title = show_title;
            }

            public String getShow_image() {
                return show_image;
            }

            public void setShow_image(String show_image) {
                this.show_image = show_image;
            }

            public String getShow_description() {
                return show_description;
            }

            public void setShow_description(String show_description) {
                this.show_description = show_description;
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
        }
    }
}
