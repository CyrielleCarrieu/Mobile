package com.example.mobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

public class HomeListFragment extends ListFragment implements AdapterView.OnItemClickListener{


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Categories, android.R.layout.simple_list_item_1);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
        System.out.println(getListView().getItemAtPosition(position).toString());

        FragmentTransaction ft = getFragmentManager().beginTransaction();

        switch (getListView().getItemAtPosition(position).toString()) {
            case "Article":
                Article article = new Article();
                ft.replace(R.id.container, article, "article");
                ft.addToBackStack(null);
                ft.commit();
                break;

            case "Image":
                Image image = new Image();
                ft.replace(R.id.container, image, "image");
                ft.addToBackStack(null);
                ft.commit();
                break;

            case "Drawing":
                Drawing drawing = new Drawing();
                ft.replace(R.id.container, drawing, "drawing");
                ft.addToBackStack(null);
                ft.commit();
                break;

        }

    }

}