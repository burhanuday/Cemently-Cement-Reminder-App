package com.burhanuday.cubetestreminder.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.burhanuday.cubetestreminder.R;
import com.burhanuday.cubetestreminder.interfaces.CubeDetailsFragmentListener;
import com.burhanuday.cubetestreminder.model.Cube;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by burhanuday on 16-12-2018.
 */
public class CubeDetailsFragment extends Fragment {
    private Cube cube;
    private CubeDetailsFragmentListener cubeDetailsFragmentListener;

    @BindView(R.id.et_3_days)
    EditText days_3;

    @BindView(R.id.et_5_days)
    EditText days_5;

    @BindView(R.id.et_7_days)
    EditText days_7;

    @BindView(R.id.et_14_days)
    EditText days_14;

    @BindView(R.id.et_21_days)
    EditText days_21;

    @BindView(R.id.et_28_days)
    EditText days_28;

    @BindView(R.id.et_56_days)
    EditText days_56;

    @BindView(R.id.btn_save_cube)
    Button save;

    @BindView(R.id.btn_delete_cube)
    Button delete;

    @BindView(R.id.btn_cancel_cube)
    Button cancel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cube_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        if (bundle!=null && bundle.containsKey("cube_object")){
            this.cube = (Cube) bundle.getSerializable("cube_object");
            setUpUI();
        }

        cubeDetailsFragmentListener = (CubeDetailsFragmentListener) getActivity();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTargetFragment().onActivityResult(
                        getTargetRequestCode(),
                        Activity.RESULT_OK,
                        new Intent().putExtra("cube_added", getCube())
                );
                cubeDetailsFragmentListener.onSaveButtonClicked();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cubeDetailsFragmentListener.onSaveButtonClicked();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTargetFragment().onActivityResult(
                        getTargetRequestCode(),
                        LocationDetailsFragment.RESULT_CODE_DELETE,
                        new Intent()
                );
                cubeDetailsFragmentListener.onSaveButtonClicked();
            }
        });
    }

    private Cube getCube(){
        Cube cube = new Cube();

        cube.setDay3Strength(setIfNotEmpty(days_3));
        cube.setDay5Strength(setIfNotEmpty(days_5));
        cube.setDay7Strength(setIfNotEmpty(days_7));
        cube.setDay14Strength(setIfNotEmpty(days_14));
        cube.setDay21Strength(setIfNotEmpty(days_21));
        cube.setDay28Strength(setIfNotEmpty(days_28));
        cube.setDay56Strength(setIfNotEmpty(days_56));

        return cube;
    }

    private float setIfNotEmpty(EditText days){
        if (TextUtils.isEmpty(days.getText().toString())){
            return 0.0f;
        }else {
            return Float.parseFloat(days.getText().toString());
        }
    }
    private void setUpUI(){
        setStrength(days_3, cube.getDay3Strength());
        setStrength(days_5, cube.getDay5Strength());
        setStrength(days_7, cube.getDay7Strength());
        setStrength(days_14, cube.getDay14Strength());
        setStrength(days_21, cube.getDay21Strength());
        setStrength(days_28, cube.getDay28Strength());
        setStrength(days_56, cube.getDay56Strength());
    }

    private void setStrength(EditText editText, float strength){
        if (strength != 0.0f){
            editText.setText(String.valueOf(strength));
        }else {
            editText.setText("");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
