package com.bing.lan.fm.ui.subscriber.delagate;

import android.view.View;
import android.widget.LinearLayout;

import com.bing.lan.comm.utils.load.ImageLoader;
import com.bing.lan.comm.utils.musicplay.Music;
import com.bing.lan.fm.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * @author 蓝兵
 * @time 2017/3/5  16:50
 */
public class SubscriberItemDelagate implements ItemViewDelegate<Music> {

    // 精品听单
    @Override
    public int getItemViewLayoutId() {
        return R.layout.subscrilber_item_adapter;
    }

    @Override
    public boolean isForViewType(Music item, int position) {
        return true;
        // return item.getList().get(0) instanceof ListItemSpecialBean;
    }

    @Override
    public void convert(final ViewHolder holder, Music itemBean, int position) {
        //这句话关掉IOS阻塞式交互效果 并依次打开左滑右滑
        ((SwipeMenuLayout) holder.itemView).setIos(false).setLeftSwipe(position % 2 == 0 ? true : false);

        //头
        holder.setText(R.id.tv_track_Title_name,itemBean.getTitle());
        //描述频道
        holder.setText(R.id.tv_track_subtitle,itemBean.getNickname());
        //播放时间
        holder.setText(R.id.tv_tracksCounts,"播放时间"+itemBean.getLastPlayPosition());
        //图片
        SimpleDraweeView ivCoverImage=holder.getView(R.id.iv_cover_image);
        ImageLoader.getInstance().loadImage(ivCoverImage,itemBean.getCoverSmall());

        //设置点击事件
        LinearLayout llChildContainer= holder.getView(R.id.ll_child_container);
        llChildContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return false;
            }
        });

        //删除点击事件
        holder.getView(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnSwipeListener.onDel(holder.getAdapterPosition());
            }
        });
    }

    /**
     * 和Activity通信的接口
     */
    public interface onSwipeListener {
        void onDel(int pos);

    }

    private SubscriberItemDelagate.onSwipeListener mOnSwipeListener;

    public SubscriberItemDelagate.onSwipeListener getOnDelListener() {
        return mOnSwipeListener;
    }

    public void setOnDelListener(SubscriberItemDelagate.onSwipeListener mOnDelListener) {
       this.mOnSwipeListener= mOnDelListener;
    }
}
