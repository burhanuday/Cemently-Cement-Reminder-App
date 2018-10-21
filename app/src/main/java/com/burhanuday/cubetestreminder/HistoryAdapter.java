package com.burhanuday.cubetestreminder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ramotion.foldingcell.FoldingCell;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Burhanuddin on 31-05-2018.
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class HistoryAdapter extends ArrayAdapter<CementCube> {
    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;
    Context context;
    DatabaseHelper databaseHelper;
    AlertDialog alertDialog;
    GlobalPrefs globalPrefs;

    public HistoryAdapter(Context context, List<CementCube> objects){
        super(context, 0, objects);
        this.context = context;
        this.databaseHelper = new DatabaseHelper(context);
        globalPrefs = new GlobalPrefs(context);
    }

    @NonNull
    @Override
    public View getView(int position, final View convertView, @NonNull ViewGroup parent) {
        // get item for selected view
        final CementCube item = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            // LayoutInflater vi = LayoutInflater.from(getContext());
            LayoutInflater vi = LayoutInflater.from(context);
            cell = (FoldingCell) vi.inflate(R.layout.cell, parent, false);
            // binding view parts to view holder
            viewHolder.unit = cell.findViewById(R.id.tv_unit);
            viewHolder.location = cell.findViewById(R.id.tv_cell_loc);
            viewHolder.doc = cell.findViewById(R.id.tv_cell_doc);
            viewHolder.day = cell.findViewById(R.id.tv_cell_day);
            viewHolder.day2 = cell.findViewById(R.id.tv_cell__day);
            viewHolder.location2 = cell.findViewById(R.id.tv_cell__loc);
            viewHolder.doc2 = cell.findViewById(R.id.tv_cell__doc);
            viewHolder.grade2 = cell.findViewById(R.id.tv_cell__grade);
            viewHolder.s3days = cell.findViewById(R.id.tv_cell_threedays);
            viewHolder.s3days2 = cell.findViewById(R.id.tv_cell_threedays2);
            viewHolder.s3days3 = cell.findViewById(R.id.tv_cell_threedays3);
            viewHolder.s7days = cell.findViewById(R.id.tv_cell_sevendays);
            viewHolder.s7days2 = cell.findViewById(R.id.tv_cell_sevendays2);
            viewHolder.s7days3 = cell.findViewById(R.id.tv_cell_sevendays3);
            viewHolder.s14days = cell.findViewById(R.id.tv_cell_fourdays);
            viewHolder.s14days2 = cell.findViewById(R.id.tv_cell_fourdays2);
            viewHolder.s14days3 = cell.findViewById(R.id.tv_cell_fourdays3);
            viewHolder.s21days = cell.findViewById(R.id.tv_cell_tonedays);
            viewHolder.s21days2 = cell.findViewById(R.id.tv_cell_tonedays2);
            viewHolder.s21days3 = cell.findViewById(R.id.tv_cell_tonedays3);
            viewHolder.s28days = cell.findViewById(R.id.tv_cell_teightdays);
            viewHolder.s28days2 = cell.findViewById(R.id.tv_cell_teightdays2);
            viewHolder.s28days3 = cell.findViewById(R.id.tv_cell_teightdays3);
            viewHolder.s56days = cell.findViewById(R.id.tv_cell_fsixdays);
            viewHolder.s56days2 = cell.findViewById(R.id.tv_cell_fsixdays2);
            viewHolder.s56days3 = cell.findViewById(R.id.tv_cell_fsixdays3);
            viewHolder.s3daysa = cell.findViewById(R.id.tv_cell_threedays_ave);
            viewHolder.s7daysa = cell.findViewById(R.id.tv_cell_sevendays_average);
            viewHolder.s14daysa = cell.findViewById(R.id.tv_cell_fourdays_average);
            viewHolder.s21daysa = cell.findViewById(R.id.tv_cell_tonedays_average);
            viewHolder.s28daysa = cell.findViewById(R.id.tv_cell_teightdays_average);
            viewHolder.s56daysa = cell.findViewById(R.id.tv_cell_fsixdays_average);
            viewHolder.grade = cell.findViewById(R.id.tv_cell_grade);
            viewHolder.edit = cell.findViewById(R.id.bt_cell_edit);
            viewHolder.delete = cell.findViewById(R.id.bt_cell_delete);
            viewHolder.move = cell.findViewById(R.id.bt_cell_move);
            viewHolder.export = cell.findViewById(R.id.bt_cell_export);
            cell.setTag(viewHolder);
        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                Log.i("FC", "unfold anim");
                cell.unfold(true);
            } else {
                Log.i("FC", "fold anim");
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        if (null == item)
            return cell;
        // bind data from selected element to view through view holder
        //  viewHolder.price.setText(item.getPrice());
        viewHolder.location.setText(item.getLocation());
        viewHolder.doc.setText(item.getDate1());
        GlobalPrefs globalPrefs = new GlobalPrefs(context);

        if (globalPrefs.getDivide()){
            viewHolder.unit.setText("Cube Data in MPa");
        }else {
            viewHolder.unit.setText("Cube Data in KN");
        }

        setStrength(viewHolder.s3days, item.getsThree1());
        setStrength(viewHolder.s3days2, item.getsThree2());
        setStrength(viewHolder.s3days3, item.getsThree3());
        setStrength(viewHolder.s7days, item.getsSeven1());
        setStrength(viewHolder.s7days2, item.getsSeven2());
        setStrength(viewHolder.s7days3, item.getsSeven3());
        setStrength(viewHolder.s14days, item.getsFourteen1());
        setStrength(viewHolder.s14days2, item.getsFourteen2());
        setStrength(viewHolder.s14days3, item.getsFourteen3());
        setStrength(viewHolder.s21days, item.getsTwentyOne1());
        setStrength(viewHolder.s21days2, item.getsTwentyOne2());
        setStrength(viewHolder.s21days3, item.getsTwentyOne3());
        setStrength(viewHolder.s28days, item.getsTwentyEight1());
        setStrength(viewHolder.s28days2, item.getsTwentyEight2());
        setStrength(viewHolder.s28days3, item.getsTwentyEight3());
        setStrength(viewHolder.s56days, item.getsFiftySix1());
        setStrength(viewHolder.s56days2, item.getsFiftySix2());
        setStrength(viewHolder.s56days3, item.getsFiftySix3());
        viewHolder.s56daysa.setText(getAverage(Float.valueOf(viewHolder.s56days.getText().toString()), Float.valueOf(viewHolder.s56days2.getText().toString()), Float.valueOf(viewHolder.s56days3.getText().toString())));
        viewHolder.s7daysa.setText(getAverage(Float.valueOf(viewHolder.s7days.getText().toString()), Float.valueOf(viewHolder.s7days2.getText().toString()), Float.valueOf(viewHolder.s7days3.getText().toString())));
        viewHolder.s14daysa.setText(getAverage(Float.valueOf(viewHolder.s14days.getText().toString()), Float.valueOf(viewHolder.s14days2.getText().toString()), Float.valueOf(viewHolder.s14days3.getText().toString())));
        viewHolder.s21daysa.setText(getAverage(Float.valueOf(viewHolder.s21days.getText().toString()), Float.valueOf(viewHolder.s21days2.getText().toString()), Float.valueOf(viewHolder.s21days3.getText().toString())));
        viewHolder.s28daysa.setText(getAverage(Float.valueOf(viewHolder.s28days.getText().toString()), Float.valueOf(viewHolder.s28days2.getText().toString()), Float.valueOf(viewHolder.s28days3.getText().toString())));
        viewHolder.s3daysa.setText(getAverage(Float.valueOf(viewHolder.s3days.getText().toString()), Float.valueOf(viewHolder.s3days2.getText().toString()), Float.valueOf(viewHolder.s3days3.getText().toString())));
        viewHolder.grade.setText(item.getConcreteGrade());
        viewHolder.location2.setText(item.getLocation());
        viewHolder.doc2.setText(item.getDate1());
        viewHolder.grade2.setText(item.getConcreteGrade());

        Date c = Calendar.getInstance().getTime();
        //  System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String todayString = df.format(c);

        viewHolder.day.setText(getDays(viewHolder.doc.getText().toString(), todayString));
        viewHolder.day2.setText(getDays(viewHolder.doc.getText().toString(), todayString));

        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditAlarm.class);
                // using putExtra(String key, Parcelable value) method
                intent.putExtra("parcel_data", item);
                intent.putExtra("table", 2);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });

        viewHolder.export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.exportIndi(item.getId(), 2);
            }
        });

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Do you really want to delete this cube?");
                builder.setMessage("The data cannot be recovered");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseHelper databaseHelper = new DatabaseHelper(context);
                        databaseHelper.delrow(item.getId(), 2);
                        context.startActivity(new Intent(context, MainActivity.class));
                        ((Activity) context).finish();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
            }
        });
        viewHolder.move.setText("Move cube to Ongoing");
        viewHolder.move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.moveRow(item.getLocation(), "history", "ongoing");
                context.startActivity(new Intent(context, MainActivity.class));
                ((Activity) context).finish();
            }
        });



      /*  // set custom btn handler for list item from that item
        if (item.getRequestBtnClickListener() != null) {
            viewHolder.edit.setOnClickListener(item.getRequestBtnClickListener());
        } else {
            // (optionally) add "default" handler if no handler found in item
            viewHolder.edit.setOnClickListener(defaultRequestBtnClickListener);
        }

        */

        return cell;
    }

    // simple methods for register cell state changes
    public void registerToggle(int position) {
        Log.i("FC", "reached register toggle fn");
        if (unfoldedIndexes.contains(position)) {
            registerFold(position);
            Log.i("FC", "register fold");
        }
        else {
            registerUnfold(position);
            Log.i("FC", "register unfold");
        }
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    public View.OnClickListener getDefaultRequestBtnClickListener() {
        return defaultRequestBtnClickListener;
    }

    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView location;
        TextView doc;
        TextView day, day2;
        TextView s3days, s3days2, s3days3, s3daysa;
        TextView s7days, s7days2, s7days3, s7daysa;
        TextView s14days, s14days2, s14days3, s14daysa;
        TextView s21days, s21days2, s21days3, s21daysa;
        TextView s28days, s28days2, s28days3, s28daysa;
        TextView s56days, s56days2, s56days3, s56daysa;
        TextView grade;
        TextView location2;
        TextView doc2;
        TextView grade2;
        TextView unit;
        Button edit, delete, move, export;
    }

    public String getDays(String doc, String today) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date todayDate = null, docDate = null;
        try {
            todayDate = dateFormat.parse(today);
            docDate = dateFormat.parse(doc);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int cYear = 0, cMonth = 0, cDay = 0;

        long diff = 0;
        if (todayDate != null && docDate != null) {
            diff = todayDate.getTime() - docDate.getTime();
        }
        // System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        // System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        float dayCount = (float) diff / (24 * 60 * 60 * 1000);
        return ("" + (int) dayCount);
    }

    public String getAverage(float a, float b, float c){
//        return Float.toString((a+b+c)/3);

        String s = String.format("%.3f", (a+b+c)/3);
        return s;
    }

    public void setStrength(TextView textView, float s){
        if (globalPrefs.getDivide()){
            s=s/22.5f;
        }
        textView.setText(String.format("%.3f", s));
    }
}
