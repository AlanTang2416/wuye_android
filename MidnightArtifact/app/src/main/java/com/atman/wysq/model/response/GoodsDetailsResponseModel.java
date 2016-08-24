package com.atman.wysq.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/19 14:08
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class GoodsDetailsResponseModel implements Parcelable{
    /**
     * result : 1
     * body : {"goods_id":120,"title":"美女特工尼基塔","pic_img":"/imageServer/127Eddd40bc72a1346e089483464749d3e69.jpg","store":"45","price":"191","discount_price":"159","sales":78,"special_edition_id":18,"status":2,"key_words":"充气娃娃","gold_coin":5,"integral":159,"chat_prop_id":12,"update_time":1458808849000,"source":1,"postage":0,"goodsDetailImgs":[{"img":"/imageServer/22BB7f0e62d66b7d438abb2f3d75af7bf83f.jpg"},{"img":"/imageServer/C8145ed212e7fedd4b51a22ef651a82d8888.jpg"},{"img":"/imageServer/8BA9b7eda453af804709a298c84ab04919f5.jpg"},{"img":"/imageServer/95DEa96ced51ed7f4600970cd8a5b2cfb4d2.jpg"},{"img":"/imageServer/B62Ccac8b59131444f8e9f5280c53effda0f.jpg"},{"img":"/imageServer/BDBE72ee0420403146e69a896bc1948b09fc.jpg"},{"img":"/imageServer/5B693a1701696c8e4789b88754d94c4e64ca.jpg"},{"img":"/imageServer/3337d48d902301054ccb89bbfad648603845.jpg"},{"img":"/imageServer/7AC19fc8a1f89dc241d3bda4e279b178f179.jpg"}],"goodsTypes":[],"specialEdition":{"special_edition_id":18,"name":"婚礼奥斯卡","unlock_desc":"1","background_img":"/imageServer/96D1472558b200214a82a8e5b0109d351546.jpg","background_sound":"/imageServer/4DBD7ac82b04c4004b788c7b59034c6e9e50.MP3","create_time":1429097649000,"update_time":1429098162000}}
     */

    private String result;
    /**
     * goods_id : 120
     * title : 美女特工尼基塔
     * pic_img : /imageServer/127Eddd40bc72a1346e089483464749d3e69.jpg
     * store : 45
     * price : 191
     * discount_price : 159
     * sales : 78
     * special_edition_id : 18
     * status : 2
     * key_words : 充气娃娃
     * gold_coin : 5
     * integral : 159
     * chat_prop_id : 12
     * update_time : 1458808849000
     * source : 1
     * postage : 0
     * goodsDetailImgs : [{"img":"/imageServer/22BB7f0e62d66b7d438abb2f3d75af7bf83f.jpg"},{"img":"/imageServer/C8145ed212e7fedd4b51a22ef651a82d8888.jpg"},{"img":"/imageServer/8BA9b7eda453af804709a298c84ab04919f5.jpg"},{"img":"/imageServer/95DEa96ced51ed7f4600970cd8a5b2cfb4d2.jpg"},{"img":"/imageServer/B62Ccac8b59131444f8e9f5280c53effda0f.jpg"},{"img":"/imageServer/BDBE72ee0420403146e69a896bc1948b09fc.jpg"},{"img":"/imageServer/5B693a1701696c8e4789b88754d94c4e64ca.jpg"},{"img":"/imageServer/3337d48d902301054ccb89bbfad648603845.jpg"},{"img":"/imageServer/7AC19fc8a1f89dc241d3bda4e279b178f179.jpg"}]
     * goodsTypes : []
     * specialEdition : {"special_edition_id":18,"name":"婚礼奥斯卡","unlock_desc":"1","background_img":"/imageServer/96D1472558b200214a82a8e5b0109d351546.jpg","background_sound":"/imageServer/4DBD7ac82b04c4004b788c7b59034c6e9e50.MP3","create_time":1429097649000,"update_time":1429098162000}
     */

    private BodyEntity body;

    protected GoodsDetailsResponseModel(Parcel in) {
        result = in.readString();
        body = in.readParcelable(BodyEntity.class.getClassLoader());
    }

    public static final Creator<GoodsDetailsResponseModel> CREATOR = new Creator<GoodsDetailsResponseModel>() {
        @Override
        public GoodsDetailsResponseModel createFromParcel(Parcel in) {
            return new GoodsDetailsResponseModel(in);
        }

        @Override
        public GoodsDetailsResponseModel[] newArray(int size) {
            return new GoodsDetailsResponseModel[size];
        }
    };

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public BodyEntity getBody() {
        return body;
    }

    public void setBody(BodyEntity body) {
        this.body = body;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(result);
        dest.writeParcelable(body, flags);
    }

    public static class BodyEntity implements Parcelable{
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
        private int source;
        private int postage;
        private String icon;

        protected BodyEntity(Parcel in) {
            goods_id = in.readInt();
            title = in.readString();
            pic_img = in.readString();
            store = in.readString();
            price = in.readString();
            discount_price = in.readString();
            sales = in.readInt();
            special_edition_id = in.readInt();
            status = in.readInt();
            key_words = in.readString();
            gold_coin = in.readInt();
            integral = in.readInt();
            chat_prop_id = in.readInt();
            update_time = in.readLong();
            source = in.readInt();
            postage = in.readInt();
            icon = in.readString();
            goodsDetailImgs = in.createTypedArrayList(GoodsDetailImgsEntity.CREATOR);
        }

        public static final Creator<BodyEntity> CREATOR = new Creator<BodyEntity>() {
            @Override
            public BodyEntity createFromParcel(Parcel in) {
                return new BodyEntity(in);
            }

            @Override
            public BodyEntity[] newArray(int size) {
                return new BodyEntity[size];
            }
        };

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        /**
         * special_edition_id : 18
         * name : 婚礼奥斯卡
         * unlock_desc : 1
         * background_img : /imageServer/96D1472558b200214a82a8e5b0109d351546.jpg
         * background_sound : /imageServer/4DBD7ac82b04c4004b788c7b59034c6e9e50.MP3
         * create_time : 1429097649000
         * update_time : 1429098162000
         */
        /**
         * img : /imageServer/22BB7f0e62d66b7d438abb2f3d75af7bf83f.jpg
         */

        private List<GoodsDetailImgsEntity> goodsDetailImgs;

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

        public List<GoodsDetailImgsEntity> getGoodsDetailImgs() {
            return goodsDetailImgs;
        }

        public void setGoodsDetailImgs(List<GoodsDetailImgsEntity> goodsDetailImgs) {
            this.goodsDetailImgs = goodsDetailImgs;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(goods_id);
            dest.writeString(title);
            dest.writeString(pic_img);
            dest.writeString(store);
            dest.writeString(price);
            dest.writeString(discount_price);
            dest.writeInt(sales);
            dest.writeInt(special_edition_id);
            dest.writeInt(status);
            dest.writeString(key_words);
            dest.writeInt(gold_coin);
            dest.writeInt(integral);
            dest.writeInt(chat_prop_id);
            dest.writeLong(update_time);
            dest.writeInt(source);
            dest.writeInt(postage);
            dest.writeString(icon);
            dest.writeTypedList(goodsDetailImgs);
        }

        public static class GoodsDetailImgsEntity implements Parcelable {
            private String img;

            protected GoodsDetailImgsEntity(Parcel in) {
                img = in.readString();
            }

            public static final Creator<GoodsDetailImgsEntity> CREATOR = new Creator<GoodsDetailImgsEntity>() {
                @Override
                public GoodsDetailImgsEntity createFromParcel(Parcel in) {
                    return new GoodsDetailImgsEntity(in);
                }

                @Override
                public GoodsDetailImgsEntity[] newArray(int size) {
                    return new GoodsDetailImgsEntity[size];
                }
            };

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(img);
            }
        }
    }
}
