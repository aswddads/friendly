package tj.com.friendly;

import android.widget.TextView;

import butterknife.BindView;
import tj.com.common.app.Activity;

public class MainActivity extends Activity {
    @BindView(R.id.txt_test)
    TextView textView;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        textView.setText("hello.");

    }
}
