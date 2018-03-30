package com.bb.dongx.mysupportlib;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bb.dongx.mysupportlib.stock.StockActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private Button mNext_btn;
    public MainActivityFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_main, container, false);
        //return inflater.inflate(R.layout.content_main, container, false);

        mNext_btn = (Button) v.findViewById(R.id.next_button);

        mNext_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), StockActivity.class);
                startActivity(i);
            }
        });

        return v;
    }
}
