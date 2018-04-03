package com.bb.dongx.mysupportlib.stock;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bb.dongx.mysupportlib.R;

import java.io.IOException;
import java.util.List;

public class StockActivity extends AppCompatActivity {
    private StockAdapter mAdapter;
    private RecyclerView mStockRecyclerView;
    StockInfoList siList;
    private ThumbnailDownloader<StockHolder> mThumbnailDownloader;

    private static final String TAG = "SinaStockDataFetch";


    private class FetchItemsTask extends AsyncTask<String,Void,StockInfo > {
        @Override
        protected StockInfo doInBackground(String... params) {
            try {
                String result = new SinaStockDataFetch()
                        .getUrlString("http://hq.sinajs.cn/list="+params[0]);
                Log.i(TAG, "Fetched contents of URL: " + result);
                StockInfo si = new StockInfo();
                si.setStockAllInfo(result);

                return si;
            } catch (IOException ioe) {
                Log.e(TAG, "Failed to fetch URL: ", ioe);
            }
            return null;
        }

        @Override
        protected void onPostExecute(StockInfo item) {
            siList.addStockInfo(item);
            updateUI() ;
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        mStockRecyclerView = (RecyclerView)findViewById(R.id.stock_recycler_view);
        //mStockRecyclerView.setLayoutManager(new LinearLayoutManager(StockActivity.this));
        mStockRecyclerView.setLayoutManager( new GridLayoutManager(this, 3));

        siList = StockInfoList.get(StockActivity.this);
        siList.addStockInfoByID(getResources().getString(R.string.sh601006));
        siList.addStockInfoByID(getResources().getString(R.string.sh600198));
        siList.addStockInfoByID(getResources().getString(R.string.sh601880));
        siList.addStockInfoByID(getResources().getString(R.string.sh601398));
        Handler responseHandler = new Handler();
        mThumbnailDownloader = new ThumbnailDownloader<>(responseHandler);
        mThumbnailDownloader.setThumbnailDownloadListener(
                new ThumbnailDownloader.ThumbnailDownloadListener<StockHolder>() {
                    @Override
                    public void onThumbnailDownloaded(StockHolder photoHolder, Bitmap bitmap) {
                        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                        photoHolder.bindDrawable(drawable,0);
                    }
                }
        );
        mThumbnailDownloader.start();
        mThumbnailDownloader.getLooper();
        Log.i(TAG, "Background thread started");

       // FetchItemsTask fit = new FetchItemsTask();
        /*
        new FetchItemsTask().execute(getResources().getString(R.string.sh601006));
        new FetchItemsTask().execute(getResources().getString(R.string.sh600198));
        new FetchItemsTask().execute(getResources().getString(R.string.sh601880));
        new FetchItemsTask().execute(getResources().getString(R.string.sh601398));

        */
        updateUI();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mThumbnailDownloader.clearQueue();
        mThumbnailDownloader.quit();
        Log.i(TAG, "Background thread destroyed");
    }


    private void updateUI() {
        List<StockInfo> sis = siList.getStockInfos();
        if(sis!=null  && sis.size()>0) {
            mAdapter = new StockAdapter(sis);
            mStockRecyclerView.setAdapter(mAdapter);
        }
    }
    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return null;
    }
    */

    private class StockHolder extends RecyclerView.ViewHolder {
        //public TextView mTitleTextView;
        public ImageView mItemImageView;
        public StockHolder(View itemView) {
            super(itemView);
            //mTitleTextView = (TextView) itemView;
            mItemImageView = (ImageView) itemView.findViewById(R.id.day_k_image_view);
        }
        public void bindDrawable(Drawable drawable,int position) {
            mItemImageView.setImageDrawable(drawable);
            Log.d( TAG, siList.getStockInfos().get(position).getStockId());

        }
    }

    private class StockAdapter extends RecyclerView.Adapter<StockHolder> {
        private List<StockInfo> mStockInfos;
        public StockAdapter(List<StockInfo> stockinfos) {
            mStockInfos = stockinfos;
        }

        public StockHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(StockActivity.this);
            View view = layoutInflater
                    .inflate(R.layout.day_k_p, parent, false);
            return new StockHolder(view);
        }
        @Override
        public void onBindViewHolder(StockHolder holder, int position) {
            StockInfo si = mStockInfos.get(position);
            Drawable placeholder;
            placeholder = ContextCompat.getDrawable(StockActivity.this,R.drawable.bill_up_close);
            holder.bindDrawable(placeholder,position);
            mThumbnailDownloader.queueThumbnail(holder, siList.getStockInfos().get(position).getStockId());
           // holder.mTitleTextView.setText(si.getStockAllInfo());
        }
        @Override
        public int getItemCount() {
            return mStockInfos.size();
        }
    }



}
