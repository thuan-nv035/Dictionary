package com.bigexercise.dictionaryexercise;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class DictionaryFragment extends Fragment {
    private String value = "Hello Dictionary";
    private FragmentListener listener;
    ArrayAdapter<String> adapter;
    ListView dicList;
    public DictionaryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dictionary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Button myButton = (Button)view.findViewById(R.id.myBtn);
//        myButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(listener != null){
//                    listener.OnItemClick(value); //
//                }
//            }
//        });
        // mapping ListView
        dicList = view.findViewById(R.id.dictionaryList);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,getListOfWord());// Sử dụng adapter có sẵn
        dicList.setAdapter(adapter);
        dicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(listener != null){
                    listener.OnItemClick(getListOfWord()[position]);
                }
            }
        });
    }

    // reset data
    public void  resetDataSource (String[] source) {
        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,source);// Sử dụng adapter có sẵn
        dicList.setAdapter(adapter);
    }
//tìm kiếm
    public void filterValue(String value) {
//        adapter.getFilter().filter(value);
        int size = adapter.getCount();
        for (int i = 0 ; i < size ; i++) {
            if (adapter.getItem(i).startsWith(value)) {
                dicList.setSelection(i);
                break;
            }
        }
    }

    // Hàn code trước
    String[] getListOfWord(){
        String[] source = new String[]{
                "abandon",
                "abandoned",
                "ability",
                "able",
                "about",
                "above",
                "abroad",
                "absence",
                "absent",
                "absolute",
                "absolutely",
                "absorb",
                "abuse",
                "academic",
                "accent",
                "accept",
                "acceptable",
                "access",
                "accident",
                "accidental",
                "accidentally",
                "accommodation",
                "accompany"
        };
        return source;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    //Abstract hóa để tránh sự phụ thuộc giữa các Class hoặc interface
    public void setOnFragmentListener(FragmentListener listener){
        this.listener = listener; // method này để gán listener của local cho toàn cục để thực call OnClickItem
    }
}