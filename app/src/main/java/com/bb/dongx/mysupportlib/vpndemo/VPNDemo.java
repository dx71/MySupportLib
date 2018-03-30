package com.bb.dongx.mysupportlib.vpndemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bb.dongx.mysupportlib.R;

public class VPNDemo extends AppCompatActivity {
    private RecyclerView mVPNRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vpndemo);
        mVPNRecyclerView = (RecyclerView) findViewById(R.id.vpn_recycler_view);
        mVPNRecyclerView.setLayoutManager(new LinearLayoutManager(VPNDemo.this));
    }
}
