package engine100.sdk.dataloader.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import engine100.sdk.dataloader.android.AndroidMapListener;
import engine100.sdk.dataloader.core.DataLoader;
import engine100.sdk.dataloader.core.DataManager;
import engine100.sdk.dataloader.core.DataType;
import engine100.sdk.dataloader.core.LoadMapListener;
import engine100.sdk.dataloader.core.LoadStatus;
import engine100.sdk.dataloader.core.Progress;
import engine100.sdk.dataloader.util.LoaderProgressUtils;

public class MainActivity extends Activity {
    TextView mText;
    DataManager manager = DataManager.getInstance();
    LoadMapListener lis = new AndroidMapListener() {

        @Override
        public void onProgressUI(Map<DataType, String> progressMap) {

            mText.setText(LoaderProgressUtils.flatProgress(progressMap));
        }

        @Override
        public void onLoadingUI() {
            Toast.makeText(getBaseContext(), "重复下载", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinishUI(Map<DataType, LoadStatus> resultMap) {

            mText.setText(LoaderProgressUtils.flatResult(resultMap));
            Toast.makeText(getBaseContext(), "完成", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (TextView) findViewById(R.id.textView1);
        manager.setDataLoader(new DataLoader() {

            @Override
            public boolean loadInBackground(DataType type, Progress pro) {
                W wmanager = new W();
                return wmanager.loadInBackground(type, pro);
            }
        });

        DataType[] ts = new DataType[5];
        for (int i = 0; i < 5; i++) {
            ts[i] = new DataType(i + "", "name" + i);
        }
        manager.setDataTypes(ts);
        manager.setConcurrently(false);

        manager.setLoadMapListener(lis);
    }

    public void starting(View view) {

        manager.startLoading();
    }

    public void clear(View view) {

        manager.setLoadMapListener(null);
    }

    public void add(View view) {

        manager.setLoadMapListener(lis);
    }
}
