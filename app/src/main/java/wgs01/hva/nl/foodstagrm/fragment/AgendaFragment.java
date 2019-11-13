package wgs01.hva.nl.foodstagrm.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import wgs01.hva.nl.foodstagrm.BaseFragment;
import wgs01.hva.nl.foodstagrm.R;

public class AgendaFragment extends BaseFragment {

    CalendarView calendarView;
    TextView textView, myDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_agenda, container, false);

        init();
        return view;
    }

    private void init(){
        textView = view.findViewById(R.id.selectDatum);
        myDate = view.findViewById(R.id.myDate);
        calendarView = view.findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2){
                String date = (i1 + 1) + "/" + i2 + "/" + i;
                myDate.setText(date);
            }
        });
    }
}
