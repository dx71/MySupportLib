package com.bb.dongx.mysupportlib.stock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bb.dongx.mysupportlib.R;

import java.util.List;

public class StockActivity extends AppCompatActivity {
    private StockAdapter mAdapter;
    private RecyclerView mStockRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        mStockRecyclerView = (RecyclerView)findViewById(R.id.stock_recycler_view);
        mStockRecyclerView.setLayoutManager(new LinearLayoutManager(StockActivity.this));
        updateUI();
    }


    private void updateUI() {
        StockInfoList siList = StockInfoList.get(StockActivity.this);
        List<StockInfo> sis = siList.getStockInfos();
        mAdapter = new StockAdapter(sis);
        mStockRecyclerView.setAdapter(mAdapter);
    }
    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return null;
    }
    */

    private class StockHolder extends RecyclerView.ViewHolder {
        public TextView mTitleTextView;
        public StockHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView;
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
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new StockHolder(view);
        }
        @Override
        public void onBindViewHolder(StockHolder holder, int position) {
            StockInfo si = mStockInfos.get(position);
            holder.mTitleTextView.setText(si.getStockId());
        }
        @Override
        public int getItemCount() {
            return mStockInfos.size();
        }
    }



}
