package edu.hsl.myapphaha;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ListView mListView;
    UriUtil mUtil = new UriUtil();
    MyAdapter mAdapter;
    TextView  tv_load;
    View      view;
    private int visibleLastIndex = 0;   //最后的可视项索引
    private int visibleItemCount;       // 当前窗口可见项总数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initEvent();
        initData(mUtil.getTime());
    }

    private void initEvent() {
        mListView = (ListView) findViewById(R.id.lv_show);
        mAdapter = new MyAdapter(getApplicationContext());
        mListView.setAdapter(mAdapter);
        view = getLayoutInflater().inflate(R.layout.layout_load, null);
        tv_load = (TextView) view.findViewById(R.id.tv_load);
        mListView.addFooterView(view);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                int item = mAdapter.getCount() - 1;
                if (i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && visibleLastIndex ==
                        mAdapter.getCount()) {
                    tv_load.setText("加载中...");
                    initData(mAdapter.getItem(item).getUnixtime());
                    mListView.setSelection(visibleLastIndex - visibleItemCount - 1);
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                visibleItemCount = i1;
                visibleLastIndex = i + i1 - 1;
            }
        });
    }

    private void initData(String time) {
        new AsyncTask<String, Void, JsonBean>() {

            @Override
            protected JsonBean doInBackground(String... strings) {

                return mUtil.getData(strings[0]);
            }

            @Override
            protected void onPostExecute(JsonBean s) {
                if (s.getError_code() == 0 || s.getReason().equals("Success") || s.getReason()
                        .equals("成功") || s.getResult() != null) {
                    mAdapter.addData(s.getResult().getData());
                    mAdapter.notifyDataSetChanged();
                    tv_load.setText("上拉加载更多信息");
                }
                super.onPostExecute(s);
            }
        }.execute(time);
    }

}
