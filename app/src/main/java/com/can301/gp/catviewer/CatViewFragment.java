package com.can301.gp.catviewer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.can301.gp.Category;
import com.can301.gp.Demonstration;
import com.can301.gp.MainActivity;

import java.util.ArrayList;
import java.util.List;

import com.can301.gp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CatViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CatViewFragment extends Fragment {

    // The key used
    private static final String CAT_INDEX_PARAM_KEY = "cat_param";

    Category category;
    ArrayList<Demonstration> demos;

    private RecyclerView rv_data;

    public CatViewFragment() {
        // Required empty public constructor
    }

    /**
     * Create a new catview fragment with the index
     * @param catIndex the index of the category
     * @return the new catview fragment created
     */
    public static CatViewFragment newInstance(int catIndex) {
        CatViewFragment fragment = new CatViewFragment();
        Bundle args = new Bundle();
        args.putInt(CAT_INDEX_PARAM_KEY, catIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        if (b != null) {
            Integer catInd = b.getInt(CAT_INDEX_PARAM_KEY);
            category = MainActivity.catList.get(catInd);
            demos = new ArrayList<Demonstration>(MainActivity.demos.get(category.title).values());
        }
        else {
            throw new RuntimeException("You must pass the category name to me.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        rv_data = view.findViewById(R.id.rv_data);
        rv_data.setLayoutManager(new LinearLayoutManager(getActivity()));
        CatViewRvAdapter rvAdapter = new CatViewRvAdapter(getActivity());
        rvAdapter.setDemosList(demos);
        rv_data.setAdapter(rvAdapter);
        return view;
    }
}