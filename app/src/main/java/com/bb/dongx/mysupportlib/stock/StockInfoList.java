package com.bb.dongx.mysupportlib.stock;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dongx on 2018/3/30.
 */

public class StockInfoList {
    private static StockInfoList sStockInfoList;
    private List<StockInfo> mStockInfos;

    public static StockInfoList get(Context context) {
        if (sStockInfoList == null) {
            sStockInfoList = new StockInfoList(context);
        }
        return sStockInfoList;
    }
    private StockInfoList(Context context) {
        mStockInfos = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            StockInfo si = new StockInfo();
            si.setStockId(Integer.toString(i));
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            si.setStockData((df.format(new Date())));
            mStockInfos.add(si);
        }
    }

    public List<StockInfo> getStockInfos() {
        return mStockInfos;
    }

    public StockInfo getStockInfo(String id) {
        for (StockInfo stockinfo : mStockInfos) {
            if (stockinfo.getStockId().equals(id)) {
                return stockinfo;
            }
        }
        return null;
    }

}
