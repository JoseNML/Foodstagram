package wgs01.hva.nl.foodstagrm.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.TextView;

import wgs01.hva.nl.foodstagrm.BaseActivity;
import wgs01.hva.nl.foodstagrm.R;

public class AgendaActivity extends BaseActivity {

    CalendarView calendarView;
    TextView textView;
    TextView myDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        calendarView = findViewById(R.id.calendarView);
        textView = findViewById(R.id.selectDatum);
        myDate = findViewById(R.id.myDate);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2){
                String date = (i1 + 1) + "/" + i2 + "/" + i;
                myDate.setText(date);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }
}
