package tj.com.common.widget.recycler;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import tj.com.common.R;

/**
 * Created by Jun on 17/9/16.
 */

public abstract class RecyclerAdapter<Data> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder<Data>>
        implements View.OnClickListener, View.OnLongClickListener, AdapterCallback<Data> {

    private final List<Data> mDataList;
    private AdapterListener<Data> mListener;

    public RecyclerAdapter(List<Data> dataList, AdapterListener<Data> listener) {
        this.mDataList = dataList;
        this.mListener = listener;
    }
    public RecyclerAdapter(AdapterListener<Data> listener) {
       this(new ArrayList<Data>(),listener);
    }
    public RecyclerAdapter() {
        this(null);
    }

    /**
     * 创建 viewHolder
     *
     * @param parent
     * @param viewType 为xml的id
     * @return
     */
    @Override
    public ViewHolder<Data> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(viewType, parent, false);
        ViewHolder<Data> holder = onCreateViewHolder(root, viewType);

        //设置view的Tag 为ViewHolder , 进行双向绑定
        root.setTag(R.id.tag_recycler_holder, holder);

        root.setOnClickListener(this);
        root.setOnLongClickListener(this);


        //注解绑定
        holder.mUnBinder = ButterKnife.bind(holder, root);
        //绑定callback
        holder.callback = this;

        return null;
    }

    protected abstract ViewHolder<Data> onCreateViewHolder(View root, int viewType);

    /**
     * override default layout type
     *
     * @param position
     * @return xml id
     */
    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mDataList.get(position));
    }

    /**
     * get layout type
     *
     * @param position
     * @param data
     * @return xml id to create ViewHolder
     */
    @LayoutRes
    protected abstract int getItemViewType(int position, Data data);

    @Override
    public void onBindViewHolder(ViewHolder<Data> holder, int position) {
        //get the data
        Data data = mDataList.get(position);
        // 触发绑定
        holder.bind(data);

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void add(Data data) {
        mDataList.add(data);
        notifyItemInserted(mDataList.size() - 1);
    }

    public void add(Data... datas) {
        if (datas != null && datas.length > 0) {
            int stsrtPosition = mDataList.size();
            Collections.addAll(mDataList, datas);
            notifyItemRangeInserted(stsrtPosition, datas.length);
        }
    }

    public void add(Collection<Data> datas) {
        if (datas != null && datas.size() > 0) {
            int stsrtPosition = mDataList.size();
            mDataList.addAll(datas);
            notifyItemRangeInserted(stsrtPosition, datas.size());
        }
    }

    /**
     * delete data
     */
    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    /**
     * replace data,include clear
     *
     * @param datas
     */
    public void replace(Collection<Data> datas) {
        mDataList.clear();
        if (datas == null || datas.size() == 0) {
            return;
        }
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        ViewHolder viewHolder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null) {
            int pos = viewHolder.getAdapterPosition();
            this.mListener.onItemClick(viewHolder, mDataList.get(pos));
        }
    }

    @Override
    public boolean onLongClick(View v) {
        ViewHolder viewHolder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null) {
            int pos = viewHolder.getAdapterPosition();
            this.mListener.onItemLongClick(viewHolder, mDataList.get(pos));
            return true;
        }
        return false;
    }

    /**
     * 设置适配器监听
     *
     * @param adapterListener
     */
    public void setListener(AdapterListener<Data> adapterListener) {
        this.mListener = adapterListener;
    }

    /**
     * 监听器
     *
     * @param <Data>
     */
    public interface AdapterListener<Data> {
        void onItemClick(RecyclerAdapter.ViewHolder holder, Data data);

        void onItemLongClick(RecyclerAdapter.ViewHolder holder, Data data);
    }


    public static abstract class ViewHolder<Data> extends RecyclerView.ViewHolder {

        protected Data mData;
        private Unbinder mUnBinder;
        private AdapterCallback<Data> callback;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        //bind data
        void bind(Data data) {
            this.mData = data;
            onBind(data);
        }

        protected abstract void onBind(Data data);

        //holder 进行数据刷新
        public void updateData(Data data) {
            if (this.callback != null) {
                this.callback.update(data, this);
            }
        }
    }

}
