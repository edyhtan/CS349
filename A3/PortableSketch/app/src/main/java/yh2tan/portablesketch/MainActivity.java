package yh2tan.portablesketch;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements IView {

    DrawView drawview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawview = (DrawView) findViewById(R.id.drawview);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) drawview.getLayoutParams();
        params.height = 1200;
        params.width = 1200;
        drawview.setLayoutParams(params);
    }

    public void notifyView() {

    }

}
