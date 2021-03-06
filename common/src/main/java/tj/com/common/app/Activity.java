package tj.com.common.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Jun on 17/9/16.
 */

public abstract class Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //界面未初始化之前进行窗口初始化
        initWindows();

        if (initArgs(getIntent().getExtras())) {
            int layoutId = getContentLayoutId();
            setContentView(layoutId);
            initWidget();
            initData();
        } else {
            finish();
        }
    }

    /**
     * get the resource id
     *
     * @return resource Id
     */
    protected abstract int getContentLayoutId();

    /**
     * init Args
     *
     * @param bundle
     * @return
     */
    protected boolean initArgs(Bundle bundle) {
        return true;
    }

    /**
     * intit widget
     */
    protected void initWidget() {
        ButterKnife.bind(this);
    }

    /**
     * init data
     */
    protected void initData() {

    }

    /**
     * init windows
     */
    protected void initWindows() {

    }

    @Override
    public boolean onSupportNavigateUp() {
        // when click 导航界面的返回时，finish当前界面
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {

        // get all the fragment in the activity
        @SuppressLint("RestrictedApi")
        List<Fragment> fragments = getSupportFragmentManager().getFragments();

        if (fragments != null && fragments.size() > 0) {
            for (Fragment fragment : fragments) {
                //判定是否为我们自己需要处理的fragment
                if (fragment instanceof tj.com.common.app.Fragment) {
                    //判断是否拦截了返回按钮
                    if (((tj.com.common.app.Fragment) fragment).onBackPressed()) {
                        return;
                    }
                }
            }
        }

        super.onBackPressed();
        finish();
    }
}
