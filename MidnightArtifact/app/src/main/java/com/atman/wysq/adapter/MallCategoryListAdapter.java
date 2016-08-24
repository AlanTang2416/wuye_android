package com.atman.wysq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.MallGetCategoryResponseModel;
import com.atman.wysq.model.response.MallGetTwoCategoryResponseModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.MyTools;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.util.DensityUtil;
import com.base.baselibs.util.LogUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/12 13:44
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class MallCategoryListAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<MallGetCategoryResponseModel.BodyEntity> oneCategory;
    private List<MallGetTwoCategoryResponseModel> twoCategory;
    private CategoryAdapterInterface mAdapterInterface;
    private int mWight;
    private LinearLayout.LayoutParams params1,params2,params3;
    private RelativeLayout.LayoutParams params4;
    private int[] imgs = {R.mipmap.category_one
            ,R.mipmap.category_two
            ,R.mipmap.category_three
            ,R.mipmap.category_four};

    public MallCategoryListAdapter(Context context, int mWight, List<MallGetCategoryResponseModel.BodyEntity> shop,
                                   CategoryAdapterInterface mAdapterInterface) {
        this.context = context;
        this.mAdapterInterface = mAdapterInterface;
        layoutInflater = LayoutInflater.from(context);
        this.oneCategory = shop;
        this.mWight = mWight;
        twoCategory = new ArrayList<>();

        params1 = new LinearLayout.LayoutParams((mWight - DensityUtil.dp2px(context,13))/2,
                ((mWight - DensityUtil.dp2px(context,13))/2) * 206 / 309);
        params2 = new LinearLayout.LayoutParams((mWight - DensityUtil.dp2px(context,16))/3,
                (mWight - DensityUtil.dp2px(context,16))/3);
        params4 = new RelativeLayout.LayoutParams((mWight - DensityUtil.dp2px(context,20)),
                ((mWight - DensityUtil.dp2px(context,20))) * 38 / 676);
        params3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public void addTwoCategory(MallGetTwoCategoryResponseModel twoCategory) {
        this.twoCategory.add(twoCategory);
        notifyDataSetChanged();
    }

    public void clearTwoCategory() {
        this.twoCategory.clear();
    }

    @Override
    public int getCount() {
        return oneCategory.size();
    }

    @Override
    public MallGetCategoryResponseModel.BodyEntity getItem(int position) {
        return oneCategory.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_mallcategory_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.itemTwocategoryTitleTx.setText("------ "+oneCategory.get(position).getName()+" ------");
        holder.itemTwocategoryTitleIv.setImageResource(imgs[position % 4]);
        holder.itemTwocategoryTitleIv.setLayoutParams(params4);

        ImageView[] iv = {holder.itemTwocategoryOneIv, holder.itemTwocategoryTwoIv
                , holder.itemTwocategoryThreeIv, holder.itemTwocategoryFourIv, holder.itemTwocategoryFiveIv};

        for (int i=0;i<twoCategory.size();i++) {
            if (twoCategory.get(i).getBody().size()>=1 &&
                    twoCategory.get(i).getBody().get(0).getParient_id() == oneCategory.get(position).getGoods_category_id()) {
                for (int n=0,j=0;n<twoCategory.get(i).getBody().size();n++) {
                    for (int m=0;m<twoCategory.get(i).getBody().get(n).getLeafCategorieList().size();m++,j++) {
                        iv[j].setImageResource(R.color.color_white);
                        iv[j].setVisibility(View.VISIBLE);
                        if (j==0 || j==1) {
                            iv[j].setLayoutParams(params1);
                        } else {
                            iv[j].setLayoutParams(params2);
                        }
                        ImageLoader.getInstance().displayImage(
                                Common.ImageUrl+twoCategory.get(i).getBody().get(n).getLeafCategorieList().get(m).getAd_club_pic()
                                , iv[j], MyBaseApplication.getApp().getOptionsNot());
                    }
                }
                break;
            } else {
                for(int z=0;z<iv.length;z++) {
                    iv[z].setLayoutParams(params3);
                    iv[z].setVisibility(View.GONE);
                }
            }
        }

        holder.itemTwocategoryOneIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<twoCategory.size();i++) {
                    if (twoCategory.get(i).getBody().size()>=1&&
                            twoCategory.get(i).getBody().get(0).getParient_id() == oneCategory.get(position).getGoods_category_id()) {
                        if (twoCategory.get(i).getBody().get(0).getLeafCategorieList().size()>=1) {
                            mAdapterInterface.onItemClick(v, twoCategory.get(i).getBody().get(0).getLeafCategorieList().get(0).getGoods_category_id()
                                    , twoCategory.get(i).getBody().get(0).getLeafCategorieList().get(0).getName());
                        } else {
                            int n = twoCategory.get(i).getBody().get(0).getLeafCategorieList().size();
                            mAdapterInterface.onItemClick(v, twoCategory.get(i).getBody().get(1).getLeafCategorieList().get(n).getGoods_category_id()
                                    , twoCategory.get(i).getBody().get(1).getLeafCategorieList().get(n).getName());
                        }
                    }
                }
            }
        });

        holder.itemTwocategoryTwoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<twoCategory.size();i++) {
                    if (twoCategory.get(i).getBody().size()>=1&&
                            twoCategory.get(i).getBody().get(0).getParient_id() == oneCategory.get(position).getGoods_category_id()) {
                        if (twoCategory.get(i).getBody().get(0).getLeafCategorieList().size()>=2) {
                            mAdapterInterface.onItemClick(v, twoCategory.get(i).getBody().get(0).getLeafCategorieList().get(1).getGoods_category_id()
                                    , twoCategory.get(i).getBody().get(0).getLeafCategorieList().get(1).getName());
                        } else {
                            int n = 1-twoCategory.get(i).getBody().get(0).getLeafCategorieList().size();
                            mAdapterInterface.onItemClick(v, twoCategory.get(i).getBody().get(1).getLeafCategorieList().get(n).getGoods_category_id()
                                    , twoCategory.get(i).getBody().get(1).getLeafCategorieList().get(n).getName());
                        }
                    }
                }
            }
        });

        holder.itemTwocategoryThreeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<twoCategory.size();i++) {
                    if (twoCategory.get(i).getBody().size()>=1&&
                            twoCategory.get(i).getBody().get(0).getParient_id() == oneCategory.get(position).getGoods_category_id()) {
                        if (twoCategory.get(i).getBody().get(0).getLeafCategorieList().size()>=3) {
                            mAdapterInterface.onItemClick(v, twoCategory.get(i).getBody().get(0).getLeafCategorieList().get(2).getGoods_category_id()
                                    , twoCategory.get(i).getBody().get(0).getLeafCategorieList().get(2).getName());
                        } else {
                            int n = 2-twoCategory.get(i).getBody().get(0).getLeafCategorieList().size();
                            mAdapterInterface.onItemClick(v, twoCategory.get(i).getBody().get(1).getLeafCategorieList().get(n).getGoods_category_id()
                                    , twoCategory.get(i).getBody().get(1).getLeafCategorieList().get(n).getName());
                        }
                    }
                }
            }
        });

        holder.itemTwocategoryFourIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<twoCategory.size();i++) {
                    if (twoCategory.get(i).getBody().size()>=1&&
                            twoCategory.get(i).getBody().get(0).getParient_id() == oneCategory.get(position).getGoods_category_id()) {
                        if (twoCategory.get(i).getBody().get(0).getLeafCategorieList().size()>=4) {
                            mAdapterInterface.onItemClick(v, twoCategory.get(i).getBody().get(0).getLeafCategorieList().get(3).getGoods_category_id()
                                    , twoCategory.get(i).getBody().get(0).getLeafCategorieList().get(3).getName());
                        } else {
                            int n = 3-twoCategory.get(i).getBody().get(0).getLeafCategorieList().size();
                            mAdapterInterface.onItemClick(v, twoCategory.get(i).getBody().get(1).getLeafCategorieList().get(n).getGoods_category_id()
                                    , twoCategory.get(i).getBody().get(1).getLeafCategorieList().get(n).getName());
                        }
                    }
                }
            }
        });

        holder.itemTwocategoryFiveIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<twoCategory.size();i++) {
                    if (twoCategory.get(i).getBody().size()>=1&&
                            twoCategory.get(i).getBody().get(0).getParient_id() == oneCategory.get(position).getGoods_category_id()) {
                        if (twoCategory.get(i).getBody().get(0).getLeafCategorieList().size()>=5) {
                            mAdapterInterface.onItemClick(v, twoCategory.get(i).getBody().get(0).getLeafCategorieList().get(4).getGoods_category_id()
                                    , twoCategory.get(i).getBody().get(0).getLeafCategorieList().get(4).getName());
                        } else {
                            int n = 4-twoCategory.get(i).getBody().get(0).getLeafCategorieList().size();
                            mAdapterInterface.onItemClick(v, twoCategory.get(i).getBody().get(1).getLeafCategorieList().get(n).getGoods_category_id()
                                    , twoCategory.get(i).getBody().get(1).getLeafCategorieList().get(n).getName());
                        }
                    }
                }
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.item_twocategory_title_iv)
        ImageView itemTwocategoryTitleIv;
        @Bind(R.id.item_twocategory_title_tx)
        TextView itemTwocategoryTitleTx;
        @Bind(R.id.item_twocategory_one_iv)
        ImageView itemTwocategoryOneIv;
        @Bind(R.id.item_twocategory_two_iv)
        ImageView itemTwocategoryTwoIv;
        @Bind(R.id.item_twocategory_three_iv)
        ImageView itemTwocategoryThreeIv;
        @Bind(R.id.item_twocategory_four_iv)
        ImageView itemTwocategoryFourIv;
        @Bind(R.id.item_twocategory_five_iv)
        ImageView itemTwocategoryFiveIv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface CategoryAdapterInterface {

        void onItemClick(View view, int id, String name);
    }
}
