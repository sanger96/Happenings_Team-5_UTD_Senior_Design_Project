package com.example.happeningsapp.ui.eventList;

//import static androidx.databinding.DataBindingUtil.setContentView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.webkit.WebView;
//import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

//import com.example.happeningsapp.R;
import com.example.happeningsapp.databinding.FragmentEventListBinding;

public class EventListFragment extends Fragment {

    private FragmentEventListBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EventListViewModel EventListViewModel =
                new ViewModelProvider(this).get(com.example.happeningsapp.ui.eventList.EventListViewModel.class);

        binding = FragmentEventListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //use get method grab events list and populate a table
        //http://localhost:<port>/event/getAll

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}