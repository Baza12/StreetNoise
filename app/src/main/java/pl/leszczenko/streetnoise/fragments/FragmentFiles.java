package pl.leszczenko.streetnoise.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import pl.leszczenko.streetnoise.AudioPlayer;
import pl.leszczenko.streetnoise.Play;
import pl.leszczenko.streetnoise.R;


public class FragmentFiles extends Fragment {

    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;
    private static final String AUDIO_RECORDER_FOLDER = "StreetNoise";
    String filepath = Environment.getExternalStorageDirectory().getPath();
    File dir = new File(filepath, AUDIO_RECORDER_FOLDER);

    SharedPreferences sPref;

    final String SAVED_TEXT = "saved_text";
    final String SAVED_TEXT_DEFAULT = "saved_text_default";
    final String LOG_TAG = "myLogs";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

      //  Toast.makeText(getActivity(), dir.toString(), Toast.LENGTH_SHORT).show();

        View myView = inflater.inflate(R.layout.main, container, false);

        // Find the ListView resource.

        mainListView = (ListView) myView.findViewById(R.id.mainListView);
        mainListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        File[] filelist = dir.listFiles();
        String[] theNamesOfFiles = new String[filelist.length];

        for (int i = 0; i < theNamesOfFiles.length; i++) {

            theNamesOfFiles[i] = filelist[i].getName();
            Log.d(LOG_TAG, "I=" + i + " file = " + filelist[i].getName());

        }

        String[] theNamesOfFiles2 = new String[filelist.length+1];
                 theNamesOfFiles2[0] = "Street_Noise.mp3";
        for (int i = 0; i < theNamesOfFiles.length; i++) {

            theNamesOfFiles2[i+1] = theNamesOfFiles[i];
            Log.d(LOG_TAG, "NOF2=" + i + " file = " + filelist[i].getName());

        }
















        listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_single_choice, theNamesOfFiles2);





        mainListView.setAdapter( listAdapter );


        Button btnPlay2 = (Button) myView.findViewById(R.id.btnPlay2);
        Button btnStop2 = (Button) myView.findViewById(R.id.btnStop2);
        Button btnDefault = (Button) myView.findViewById(R.id.btnDefault);


        btnPlay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameFileCliked =  mainListView.getItemAtPosition(mainListView.getCheckedItemPosition()).toString()  ;

              //  Toast.makeText(getActivity(),"File " + nameFileCliked + " clicked",Toast.LENGTH_LONG).show();
                Log.d(LOG_TAG, "press on" + nameFileCliked);

                // сохраняем что выбрали
                sPref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString(SAVED_TEXT, nameFileCliked);
                ed.commit();
                // тестирую что сохранилось
                loadText();


                getActivity().startService(new Intent(getActivity(), AudioPlayer.class));

            }
        });

        btnStop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().stopService(new Intent(getActivity(), AudioPlayer.class));

            }
        });

        btnDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameFileToDefault =  mainListView.getItemAtPosition(mainListView.getCheckedItemPosition()).toString()  ;

              //  Toast.makeText(getActivity(),"File nameFileToDefault " + nameFileToDefault ,Toast.LENGTH_SHORT).show();
                Log.d(LOG_TAG, "nameFileToDefault is " + nameFileToDefault);

                // сохраняем что выбрали
                sPref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString(SAVED_TEXT_DEFAULT, nameFileToDefault);
                ed.commit();
                // тестирую что сохранилось
               // loadText();


               // getActivity().startService(new Intent(getActivity(), AudioPlayer.class));

            }
        });






        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

              //  String nameFileCliked =  parent.getItemAtPosition(position).toString();

//                Toast.makeText(getActivity(),"File " + nameFileCliked + " clicked",Toast.LENGTH_LONG).show();
//                Log.d(LOG_TAG, "press on" + nameFileCliked);








              //  getActivity().startService(new Intent(getActivity(), AudioPlayer.class));




            }
        });

        mainListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {


            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(),
//                        "Item in position LONG " + position + " clicked",
//                        Toast.LENGTH_LONG).show();
              //  getActivity().stopService(new Intent(getActivity(), AudioPlayer.class));

                return false;
            }
        });



        return myView;



    }
    public void loadText() {
        sPref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT, "");


        Log.d(LOG_TAG, "savedText in fragment " + savedText);

    }


}
