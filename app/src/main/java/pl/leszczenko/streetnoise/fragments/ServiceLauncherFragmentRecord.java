package pl.leszczenko.streetnoise.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import pl.leszczenko.streetnoise.R;
import pl.leszczenko.streetnoise.Record;

/**
 * Created by Roma on 31.05.2015.
 */
public class ServiceLauncherFragmentRecord extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View myView2 = inflater.inflate(R.layout.record, container, false);

        super.onCreate(savedInstanceState);

        final Button btnStart = (Button)myView2.findViewById(R.id.btn_start_recording);
        final Button btnStop = (Button) myView2.findViewById(R.id.btn_stop_recording);


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startService(new Intent(getActivity(), Record.class));
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().stopService(new Intent(getActivity(), Record.class));
            }
        });

        return myView2;


    }




}
