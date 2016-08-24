package com.atman.wysq.model.request;

import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/21 18:20
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class AddOrderRequestModel {
    /**
     * address_id : 3
     * oeder_type : 3  //订单类型,1:普通订单,11:酒店订单,21:金币订单,31:魅力值订单  41 众筹  51 支付宝微信购买APPLE金币 61 心愿墙订单
     * crowdfunding_stage_id : 3
     * gold_pay : 3
     * price : 3
     * order : [{"goods_id":1,"amount":2}]
     */

    private int address_id;
    private int order_type;
    private int crowdfunding_stage_id;
    private int gold_pay;
    private int price;
    /**
     * goods_id : 1
     * amount : 2
     */

    private List<OrderEntity> order;

    public AddOrderRequestModel(int address_id, List<OrderEntity> order, int order_type) {
        this.address_id = address_id;
        this.order = order;
        this.order_type = order_type;
    }

    public AddOrderRequestModel(int address_id, int crowdfunding_stage_id, int gold_pay,
                                int order_type, List<OrderEntity> order, int price) {
        this.address_id = address_id;
        this.crowdfunding_stage_id = crowdfunding_stage_id;
        this.gold_pay = gold_pay;
        this.order_type = order_type;
        this.order = order;
        this.price = price;
    }

    public static class OrderEntity {
        private int goods_id;
        private int amount;

        public OrderEntity(int goods_id, int amount) {
            this.goods_id = goods_id;
            this.amount = amount;
        }
    }
}
