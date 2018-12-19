package com.burhanuday.cubetestreminder.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.burhanuday.cubetestreminder.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by burhanuday on 18-12-2018.
 */
public class MoreFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.card_design_mix)
    CardView designMix;

    @BindView(R.id.card_nominal_mix)
    CardView nominalMix;

    @BindView(R.id.card_backup)
    CardView backup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_more, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        designMix.setOnClickListener(this);
        nominalMix.setOnClickListener(this);
        backup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.card_design_mix:
                intent = new Intent(getContext(), DesignMix.class);
                break;
            case R.id.card_nominal_mix:
                intent = new Intent(getContext(), NominalMix.class);
                break;
            case R.id.card_backup:
                break;
        }
        if (intent != null){
            startActivity(intent);
        }
    }
}
