package engine100.sdk.dataloader.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import engine100.sdk.dataloader.core.DataLoader;
import engine100.sdk.dataloader.core.DataManager;
import engine100.sdk.dataloader.core.DataType;
import engine100.sdk.dataloader.core.LoadMapListener;
import engine100.sdk.dataloader.core.LoadStatus;
import engine100.sdk.dataloader.core.Progress;

public class MainActivity extends Activity {
    TextView mText;
    DataManager manager = DataManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (TextView) findViewById(R.id.textView1);
        manager.setDataLoader(new DataLoader() {

            @Override
            public boolean loadInBackground(DataType type, Progress pro) {
                pro.onPublishProgress(type, "正在下载:" + type.getName());
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
                return false;
            }
        });

        DataType[] ts = new DataType[100];
        for (int i = 0; i < 100; i++) {
            ts[i] = new DataType(i + "", "name" + i);
        }
        manager.setDataTypes(ts);
        manager.setConcurrently(false);

        manager.setLoadMapListener(lis);
    }

    final StringBuilder sb = new StringBuilder();
    LoadMapListener lis = new LoadMapListener() {

        @Override
        public void onProgress(Map<DataType, String> progressMap) {
            sb.setLength(0);

            for (DataType key : progressMap.keySet()) {
                String typename = key.getName();
                String msg = progressMap.get(key);
                sb.append(typename).append(":").append(msg).append("\n");

            }
            mText.setText(sb.toString());
        }

        @Override
        public void onLoading() {
            Toast.makeText(getBaseContext(), "重复下载", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish(Map<DataType, LoadStatus> resultMap) {
            Toast.makeText(getBaseContext(), "完成", Toast.LENGTH_SHORT).show();
        }
    };

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
