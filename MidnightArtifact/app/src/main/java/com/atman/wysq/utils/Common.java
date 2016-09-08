package com.atman.wysq.utils;

import okhttp3.MediaType;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/6 09:48
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class Common {

    /**************************http通用设置*****************************/
    public static int timeOut = 15000;
    public static int timeOutTwo = 120000;
    public static MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static String ContentType = "application/json; charset=utf-8";
    /**************************http通用设置*****************************/



    /**************************http基础访问路径*****************************/
    public static String hostUrl = "http://192.168.1.134:8080/";
    public static String ImageUrl = "http://192.168.1.134:8001";

//    public static String hostUrl = "http://api.5ys7.com/";
//    public static String ImageUrl = "http://api.5ys7.com";
    /**************************http基础访问路径*****************************/



    /**************************http访问路径*****************************/
    //登录
    public static String Url_Login = hostUrl + "login";
    //发送验证码
    public static String Url_SeedCode = hostUrl + "rest/message/";
    //注册
    public static String Url_Register = hostUrl + "rest/register";
    //忘记密码
    public static String Url_NewPW = hostUrl + "rest/forgotAndChangePwd";
    //获取用户信息
    public static String Url_GetUserInfo = hostUrl + "rest/user/info/";
    //修改密码
    public static String Url_Reset_PW = hostUrl + "rest/user/password";
    //修改昵称
    public static String Url_Reset_Nick = hostUrl + "rest/user/username/";
    //修改头像
    public static String Url_Reset_Head = hostUrl + "uploadFile";
    //退出
    public static String Url_Logout = hostUrl + "rest/user/logout";
    //获取任务列表
    public static String Url_Get_Task = hostUrl + "rest/task/all";
    //获取任务奖励
    public static String Url_Rewarded = hostUrl + "rest/task/rewarded";
    //完成任务
    public static String Url_Finish_Task = hostUrl + "rest/task/usertask";
    //修改头像
    public static String Url_Modify_Head = hostUrl + "rest/user/icon";
    //充值－－增加订单
    public static String Url_Recharge_Add_Order = hostUrl + "rest/order/addgoldenorder";
    //充值－－增加订单－－支付宝
    public static String Url_Recharge_Add_Order_Alipay = hostUrl + "rest/order/generatepayparam/";
    //充值－－增加订单－－微信
    public static String Url_Recharge_Add_Order_WeiXin = hostUrl + "rest/order/weixin/prepay";
    //轮播图获取,1 广告系列 2 俱乐部顶部  3 俱乐部顶部banner之下  4 秘密说首页
    public static String Url_AdList = hostUrl + "rest/category/serieslist/";
    //获取俱乐部分类
    public static String Url_Category_List = hostUrl + "rest/category/list";
    //获取俱乐部二级分类
    public static String Url_Two_Category_List = hostUrl + "rest/category/leaflist/";
    //获取分类下商品
    public static String Url_Get_Category_List = hostUrl + "rest/goods/category/";
    public static String Url_Get_Type_List = hostUrl + "rest/goods/type/";
    //获取商品详情
    public static String Url_Get_Category_Detail = hostUrl + "rest/goods/detail/";
    //获取商品评论
    public static String Url_Get_Goods_comment = hostUrl + "rest/goods/comment/";
    //获取用户收货地址
    public static String Url_Get_Address_List = hostUrl + "rest/user/address";
    public static String Url_Get_Address_ByOrderId = hostUrl + "rest/order/address/";
    //添加订单
    public static String Url_Add_Order = hostUrl + "rest/order/add";
    //获取省数据
    public static String Url_Get_Province = hostUrl + "rest/order/getProvince";
    //获取市数据
    public static String Url_Get_City = hostUrl + "rest/order/getCity/";
    //获取县数据
    public static String Url_Get_District = hostUrl + "rest/order/getDistrict/";
    //添加新地址
    public static String Url_Add_Address = hostUrl + "rest/user/address";
    //删除地址
    public static String Url_Delete_Address = hostUrl + "rest/user/address/";
    //获取我的订单
    public static String Url_Get_OrderList = hostUrl + "rest/order/my/";
    //获取订单详情
    public static String Url_Get_OrderDetail = hostUrl + "rest/order/status/";
    //获取公用配置信息
    public static String Url_Get_GoldenRole = hostUrl + "rest/user/goldenrole";
    //检查版本更新
    public static String Url_Get_Version = hostUrl + "rest/androidUpdate";
    //获取社区模块分类
    public static String Url_Get_BlogBoard = hostUrl + "rest/blog/board";
    //获取论坛帖子列表 rest/blog/boardlist/{blog_board_id}/{type}/{update_time}
    public static String Url_Get_BlogList = hostUrl + "rest/blog/bloglist/";
    //获取帖子详情
    public static String Url_Get_BlogDetail = hostUrl + "rest/blog/detail/";
    //获取帖子评论
    public static String Url_Get_BlogDetail_CommentList = hostUrl + "rest/blog/comment/";
    //收藏帖子
    public static String Url_Get_BlogCollection = hostUrl + "rest/blog/favorite/";
    //取消收藏帖子
    public static String Url_Get_BlogCollection_Not = hostUrl + "rest/blog/favorite/";
    //举报
    public static String Url_RePort = hostUrl + "rest/user/feedback";
    //增加评论
    public static String Url_Add_Comment = hostUrl + "rest/blog/comment";
    //评论点赞
    public static String Url_Add_Like = hostUrl + "rest/magic/commentlike/";
    //拉黑
    public static String Url_Add_BlackList = hostUrl + "rest/corruption/add";
    //帖子评论的评论列表
    public static String Url_SubComment_List = hostUrl + "rest/blog/subcomment/";
    //发帖
    public static String Url_Add_Post = hostUrl + "rest/blog/add";
    //添加打赏
    public static String Url_Add_Award = hostUrl + "rest/award/add";
    //获取打赏列表
    public static String Url_Get_Award_List = hostUrl + "rest/award/list/";
    //获取我的收藏
    public static String Url_Get_My_Collection = hostUrl + "rest/blog/favorite/";
    //获取我的秘密
    public static String Url_Get_My_Sceret = hostUrl + "rest/blog/user/";
    //获取我的评论或评论我的
    public static String Url_Get_UserComment = hostUrl + "rest/blog/usercomment/";
    //添加帖子浏览量
    public static String Url_Add_Browse = hostUrl + "rest/blog/increaseviewcount/";
    //删除帖子
    public static String Url_Delete_Post = hostUrl + "rest/blog/delete/";
    //性别认证
    public static String Url_Verify = hostUrl + "rest/user/verify";
    //获取聊天token
    public static String Url_Get_ChatToken = hostUrl + "rest/chat/chattoken";
    //获取好友列表
    public static String Url_Get_Fans = hostUrl + "rest/user/fans";
    //获取用户基本信息
    public static String Url_Get_UserIndex = hostUrl + "rest/user/index";
    //获取访客信息
    public static String Url_Get_Browse = hostUrl + "rest/user/browse/";
    //获取守护者信息
    public static String Url_Get_Guard = hostUrl + "rest/user/guard/";
    //加好友
    public static String Url_Add_Friends = hostUrl + "rest/user/addfan/";
    //删除好友
    public static String Url_Delete_Friends = hostUrl + "rest/user/follow/";
    //获取物流URL
    public static String Url_Get_Logistics = hostUrl + "rest/order/logistics/";
    //获取礼物列表
    public static String Url_Get_GiftList = hostUrl + "rest/gift/list";
    //购买礼物
    public static String Url_Pay_GiftList = hostUrl + "rest/gift/buy/";
    /**************************http访问路径*****************************/



    /**************************http访问回应识别码*****************************/
    //登录
    public static int NET_LOGIN_ID = 1;
    //发送验证码
    public static int NET_SMS_ID = 2;
    //注册
    public static int NET_REGISTER_ID = 3;
    //忘记密码
    public static int NET_NEWPW = 4;
    //获取用户信息
    public static int NET_GETUSERINFO = 5;
    //修改密码
    public static int NET_RESET_PW = 6;
    //修改昵称
    public static int NET_RESET_NICK = 7;
    //修改头像
    public static int NET_RESET_HEAD = 8;
    //退出
    public static int NET_LOGOUT = 9;
    //任务列表
    public static int NET_GET_RASK = 10;
    //获取任务奖励
    public static int NET_REWARDED = 11;
    //完成任务
    public static int NET_FINISH_TASK = 12;
    //修改头像
    public static int NET_MODIFY_HEAD = 13;
    //充值－－增加订单
    public static int NET_RECHARGE_ADD_ORDER = 14;
    //充值－－增加订单－－支付宝
    public static int NET_RECHARGE_ADD_ORDER_ALIPAY = 15;
    //充值－－增加订单－－微信
    public static int NET_RECHARGE_ADD_ORDER_WEIXIN = 16;
    //轮播图获取
    public static int NET_AD_LIST = 17;
    //获取俱乐部分类
    public static int NET_CATEGORY_LIST = 18;
    //获取俱乐部二级分类
    public static int NET_TWO_CATEGORY_LIST = 19;
    //获取分类下商品
    public static int NET_GET_CATEGORY_LIST = 20;
    //获取商品详情
    public static int NET_GET_CATEGORY_DETAIL = 21;
    //获取商品评论
    public static int NET_GET_GOODS_COMMENT = 22;
    //获取用户收货地址
    public static int NET_GET_ADDRESS_LIST = 23;
    //添加订单
    public static int NET_ADD_ORDER = 24;
    //获取省数据
    public static int NET_GET_PROVINCE = 25;
    //获取市数据
    public static int NET_GET_CITY = 26;
    //获取县数据
    public static int NET_GET_DISTRICT = 27;
    //添加新地址
    public static int NET_ADD_ADDRESS = 28;
    //删除地址
    public static int NET_DELETE_ADDRESS = 29;
    //获取我的订单
    public static int NET_GET_ORDERLIST = 30;
    //获取订单详情
    public static int NET_GET_ORDERDETAIL = 31;
    //获取公用配置信息
    public static int NET_GET_GOLDENROLE = 32;
    //检查版本更新
    public static int NET_GET_VERSION = 33;
    //获取社区模块分类
    public static int NET_GET_BLOGBOARD = 34;
    //获取论坛帖子列表
    public static int NET_GET_BLOGLIST = 35;
    //获取帖子详情
    public static int NET_GET_BLOGDETAIL = 36;
    //获取帖子评论
    public static int NET_GET_BLOGDETAIL_COMMENTLIST = 37;
    //收藏帖子
    public static int NET_GET_BLOGCOLLECTION = 38;
    //取消收藏帖子
    public static int NET_GET_BLOGCOLLECTION_NOT = 39;
    //举报
    public static int NET_REPORT = 40;
    //增加评论
    public static int NET_ADD_COMMENT = 41;
    //评论点赞
    public static int NET_ADD_LIKE = 42;
    //拉黑
    public static int NET_ADD_BLACKLIST = 43;
    //帖子评论的评论列表
    public static int NET_SUBCOMMENT_LIST = 44;
    //发帖
    public static int NET_ADD_POST = 45;
    //添加打赏
    public static int NET_ADD_AWARD = 46;
    //获取打赏列表
    public static int NET_GET_AWARDLIST = 47;
    //获取我的收藏
    public static int NET_GET_MYCOLLECTION = 48;
    //获取我的秘密
    public static int NET_GET_MYSECRET = 49;
    //获取我的评论或评论我的
    public static int NET_GET_USERCOMMENT = 50;
    //添加帖子浏览量
    public static int NET_ADD_BROWSE = 51;
    //删除帖子
    public static int NET_DELETE_POST = 52;
    //性别认证
    public static int NET_VERIFY = 53;
    //性别认证照相
    public static int NET_RESET_HEAD_TWO = 54;
    //获取聊天token
    public static int NET_GET_CHATTOKEN = 55;
    //获取好友列表
    public static int NET_GET_FANS = 56;
    //获取用户基本信息
    public static int NET_GET_USERINDEX = 57;
    //获取访客信息
    public static int NET_GET_BROWSE = 58;
    //获取守护者信息
    public static int NET_GET_GUARD = 59;
    //加好友
    public static int NET_ADD_FRIEND = 60;
    //删除好友
    public static int NET_DLELTE_FRIEND = 61;
    //获取物流URL
    public static int NET_GET_LOGISTICS = 62;
    //获取礼物列表
    public static int NET_GET_GIFTLIST = 63;
    //购买礼物
    public static int NET_PAY_GIFTLIST = 64;
    /**************************http访问回应识别码*****************************/



    /**************************跳转码*****************************/
    public static int toResetPW = 1000;
    public static int toRegister = 1001;
    public static int toResetNICK = 1002;
    public static int toMyInfo = 1003;
    public static int toLogin = 1004;
    public static int toCreateGesrure = 1005;
    public static int toLoginCreateGesrure = 1006;
    public static int toSettingGesrure = 1007;
    public static int toManageAddress = 1008;
    public static int toAddAddress = 1009;
    public static int toOrderDetail = 1010;
    public static int toPostDetail = 1011;
    public static int toSelectGift = 1012;
    /**************************跳转码*****************************/
}
