package com.burhanuday.cubetestreminder.view;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.burhanuday.cubetestreminder.util.DatabaseHelper;
import com.burhanuday.cubetestreminder.R;

public class Backup extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup);

        setTitle("Backup & Restore");

        TextView text = findViewById(R.id.backupText);
        text.setText("1. Click on the backup button to make a backup of all projects (Ongoing and History).\n\n" +
                "2. Go to your file manager and search for \""+ getString(R.string.app_name) +"\" folder in your Internal storage.\n\n" +
                "3. Share that folder with the device you want to restore the backup. \n\n" +
                "4. On the new device, paste the folder in your Internal storage. \n\n" +
                "5. Open this page on the new device and click on restore.");

        Button back, res;
        back = findViewById(R.id.button_backup);
        res = findViewById(R.id.button_restore);

        final DatabaseHelper dh = new DatabaseHelper(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dh.makeBackup();
            }
        });
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dh.recoverFromBackup();
            }
        });
    }
}
