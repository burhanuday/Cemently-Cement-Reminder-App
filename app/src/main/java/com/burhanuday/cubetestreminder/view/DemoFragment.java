package com.burhanuday.cubetestreminder.view;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.burhanuday.cubetestreminder.adapter.FoldingCellListAdapter;
import com.burhanuday.cubetestreminder.util.GlobalPrefs;
import com.burhanuday.cubetestreminder.adapter.HistoryAdapter;
import com.burhanuday.cubetestreminder.util.OnAlarmReceiver;
import com.burhanuday.cubetestreminder.R;
import com.burhanuday.cubetestreminder.util.ReminderManager;
import com.burhanuday.cubetestreminder.model.CementCube;
import com.burhanuday.cubetestreminder.util.onBootReceiver;
import com.burhanuday.cubetestreminder.util.DataFetch;
import com.burhanuday.cubetestreminder.util.DatabaseHelper;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

/**
 *
 */
public class DemoFragment extends Fragment {
    private FrameLayout fragmentContainer;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<CementCube> items = null;
    AlertDialog alertDialog;
    boolean isSearch;

    /**
     * Create a new instance of the fragment
     */
    public static DemoFragment newInstance(int index) {
        DemoFragment fragment = new DemoFragment();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isSearch = false;
        final DataFetch dataFetch = new DataFetch(getActivity().getApplicationContext());
        View view = null;
        int frag_number = getArguments().getInt("index", 0);
        switch(frag_number) {
            case 0:
                view = inflater.inflate(R.layout.fragment_today, container, false);
                items = dataFetch.getToday();
                if (items == null){
                    break;
                }
                initToday(view, items);
                break;
            case 1:
                view = inflater.inflate(R.layout.fragment_upcoming, container, false);
                items = dataFetch.getAll();
                if (items == null){
                    break;
                }
                initToday(view, items);
                break;
            case 2:
                view = inflater.inflate(R.layout.fragment_history, container, false);
                items = dataFetch.getHistory();
                if (items == null){
                    break;
                }
                initHistory(view, items);
                break;

            case 3:
                view = inflater.inflate(R.layout.fragment_settings, container, false);
                initSettings(view);
                break;
        }

        return view;
    }

    public void setAlarm(int hour, int minute){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, month, day, hour, minute);

        ReminderManager reminderManager = new ReminderManager(getActivity());
        reminderManager.setReminder(calendar);
    }

    public void initSettings(View view){
        CardView setT = view.findViewById(R.id.card_setTime);
        CardView checkPermissions = view.findViewById(R.id.card_permissions);
        CardView toExcel = view.findViewById(R.id.card_export);
        CardView takeBackup = view.findViewById(R.id.card_take_backup);
        CardView addWidget = view.findViewById(R.id.card_add_widget);
        final TextView setTime = view.findViewById(R.id.tv_rem_time);
        final GlobalPrefs globalPrefs = new GlobalPrefs(getActivity());
        setTime.setText(globalPrefs.getHour() + ":" + globalPrefs.getMinute());
        Switch setDivide = view.findViewById(R.id.switch_setDivide);

        if (globalPrefs.getDivide()){
            setDivide.setChecked(true);
        }else {
            setDivide.setChecked(false);
        }

        setDivide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                globalPrefs.setDivide(isChecked);
                Toasty.info(getActivity(), "Restart App for changes", Toast.LENGTH_SHORT, true).show();
            }
        });

        checkPermissions.setVisibility(View.GONE);

        addWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.info(getActivity(), "Add widget from widget picker on homescreen", Toast.LENGTH_LONG).show();
            }
        });


        takeBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(getActivity(), Backup.class));

            }
        });

        setT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        setTime.setText( selectedHour + ":" + selectedMinute);
                        globalPrefs.setReminderTime(selectedHour, selectedMinute);

                        ComponentName receiver = new ComponentName(getActivity(), onBootReceiver.class);
                        PackageManager pm = getActivity().getPackageManager();

                        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                        Intent myIntent = new Intent(getActivity(), OnAlarmReceiver.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                getActivity(), 0, myIntent, 0);

                        if (alarmManager != null) {
                            alarmManager.cancel(pendingIntent);
                        }
                        /*
                        pm.setComponentEnabledSetting(receiver,
                                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                                PackageManager.DONT_KILL_APP);
                                */
                        setAlarm(selectedHour, selectedMinute);
                        Toasty.success(getActivity(), "Alarm Set Successfully!", Toast.LENGTH_SHORT, true).show();
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        checkPermissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askPermissions();
            }
        });

        toExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Export to spreadsheet");
                    builder.setMessage("Which table do you want to export? \n Exported tables are saved in Internal Storage\\"+ getString(R.string.app_name));

                    // add the buttons
                    builder.setPositiveButton("History", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
                            databaseHelper.makeExcel(2);
                        }
                    });
                    builder.setNegativeButton("Ongoing", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
                            databaseHelper.makeExcel(1);
                        }
                    });
                    builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            alertDialog.dismiss();
                        }
                    });

                    // create and show the alert dialog
                    alertDialog = builder.create();
                    alertDialog.show();

            }
        });

    }

    public void searchCube(int fragment, View view, String doc, int table){
        ArrayList<CementCube> cementCubes;
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        cementCubes = databaseHelper.searchDate(doc, table);

        switch (fragment){
            case 0:
                initToday(view, cementCubes);
                break;
            case 1:
                initToday(view, cementCubes);
                break;
            case 2:
                initToday(view, cementCubes);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(getActivity(), "This permission is required to store and backup data", Toast.LENGTH_SHORT).show();
                    askPermissions();
                }
                break;
            }


            case 2: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toasty.error(getActivity(), "This permission is required to store and backup data", Toast.LENGTH_LONG, true).show();
                    askPermissions();
                }
                break;

            }
        }
    }

    public void askPermissions(){
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);

        } else {
            // Permission has already been granted
        }

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WAKE_LOCK)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WAKE_LOCK},
                    2);

        } else {
            // Permission has already been granted
        }

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    3);

        } else {
            // Permission has already been granted
        }

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.RECEIVE_BOOT_COMPLETED)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED},
                    4);

        } else {
            // Permission has already been granted
        }
    }


    public void initHistory(final View view, final ArrayList<CementCube> items){
        ListView theListView = view.findViewById(R.id.mainListView);
        final ImageView search = view.findViewById(R.id.iv_search);

        final Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR);
        final int mMonth = c.get(Calendar.MONTH);
        final int mDay = c.get(Calendar.DAY_OF_MONTH);
        final String[] date = new String[1];

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View ivsearch) {
                if (!isSearch) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                                    date[0] = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                    searchCube(2, view, date[0], 2);
                                    isSearch = true;
                                    search.setImageResource(R.drawable.ic_cancel);
                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }else {
                    search.setImageResource(R.drawable.ic_search);
                    isSearch = false;
                    getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                    ((Activity) getActivity()).finish();

                }

            }
        });
        // create custom adapter that holds elements and their state (we need hold a id's of unfolded elements for reusable elements)
        final HistoryAdapter adapter = new HistoryAdapter(getActivity(), items);
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Log.i("FC", "registered change");
                // toggle clicked cell state
                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
                adapter.registerToggle(pos);

            }
        });

        // set elements to adapter
        try {
            theListView.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void initToday(final View view, final ArrayList<CementCube> items) {
        ListView theListView = view.findViewById(R.id.mainListView);
        final ImageView search = view.findViewById(R.id.iv_search);
        final Calendar c = Calendar.getInstance();
       final int mYear = c.get(Calendar.YEAR);
       final int mMonth = c.get(Calendar.MONTH);
        final int mDay = c.get(Calendar.DAY_OF_MONTH);
        final String[] date = new String[1];

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View ivsearch) {
                if (!isSearch) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                                    date[0] = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                    searchCube(0, view, date[0], 1);
                                    isSearch = true;
                                    search.setImageResource(R.drawable.ic_cancel);
                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }else {
                    search.setImageResource(R.drawable.ic_search);
                    isSearch = false;
                    getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                    ((Activity) getActivity()).finish();

                }

            }
        });
        // prepare elements to display

        //final ArrayList<CementCube> items = CementCube.getTestingList();

        // add custom btn handler to first list item
        /*
        items.get(0).setRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "CUSTOM HANDLER FOR FIRST BUTTON", Toast.LENGTH_SHORT).show();
            }
        });
        */
        // create custom adapter that holds elements and their state (we need hold a id's of unfolded elements for reusable elements)
        final FoldingCellListAdapter adapter = new FoldingCellListAdapter(getActivity(), items);


        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Log.i("FC", "registered change");
                // toggle clicked cell state
                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
                adapter.registerToggle(pos);

            }
        });

        // set elements to adapter
        try {
            theListView.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

        /**
         * Refresh
         */
    public void refresh() {
        if (getArguments().getInt("index", 0) > 0 && recyclerView != null) {
            recyclerView.smoothScrollToPosition(0);
        }
    }

    /**
     * Called when a fragment will be displayed
     */
    public void willBeDisplayed() {
        // Do what you want here, for example animate the content


        if (fragmentContainer != null) {
            Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
            fragmentContainer.startAnimation(fadeIn);
        }
    }

    /**
     * Called when a fragment will be hidden
     */
    public void willBeHidden() {
        if (fragmentContainer != null) {
            Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
            fragmentContainer.startAnimation(fadeOut);
        }
    }
}