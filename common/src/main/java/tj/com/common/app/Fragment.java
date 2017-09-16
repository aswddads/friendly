package tj.com.common.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jun on 17/9/16.
 */

public abstract class Fragment extends android.support.v4.app.Fragment {

    protected View mRoot;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //初始化参数
        initArgs(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mRoot == null) {
            int layoutId = getContentLayoutId();
            View root = inflater.inflate(layoutId, container, false);

            initWidget(root);

            mRoot = root;
        } else {
            if (mRoot.getParent() != null) {
                ((ViewGroup) mRoot.getParent()).removeView(mRoot);
            }
        }

        return mRoot;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();
    }

    /**
     * get the resource id
     *
     * @return resource Id
     */
    protected abstract int getContentLayoutId();

    /**
     * intit widget
     */
    protected void initWidget(View root) {

    }

    /**
     * init data
     */
    protected void initData() {

    }

    /**
     * init Args
     * @param bundle
     * @return
     */
    protected void initArgs(Bundle bundle) {
    }

    /**
     * click back button
     * @return true instead of had deal with,Activity don't worry,don't finish;
     *  false activity finish;
     */
    public boolean onBackPressed(){
        return false;
    }

}
