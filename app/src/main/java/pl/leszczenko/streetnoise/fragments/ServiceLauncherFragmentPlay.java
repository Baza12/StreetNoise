
package pl.leszczenko.streetnoise.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import pl.leszczenko.streetnoise.Play;
import pl.leszczenko.streetnoise.PlayDefault;
import pl.leszczenko.streetnoise.R;

public class ServiceLauncherFragmentPlay extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.play, container, false);

       super.onCreate(savedInstanceState);

        final Button btnStart = (Button) myView.findViewById(R.id.btn_start);
        final Button btnStop = (Button) myView.findViewById(R.id.btn_stop);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startService(new Intent(getActivity(), PlayDefault.class));

            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().stopService(new Intent(getActivity(), PlayDefault.class));
            }
        });

        return myView;


}







     }



