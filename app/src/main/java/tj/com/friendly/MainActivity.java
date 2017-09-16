package tj.com.friendly;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import tj.com.common.Common;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Common();
    }
}
