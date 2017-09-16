package tj.com.common.widget.recycler;

/**
 * Created by Jun on 17/9/16.
 */

public interface AdapterCallback<Data> {
    void update(Data data,RecyclerAdapter.ViewHolder<Data> holder);
}
