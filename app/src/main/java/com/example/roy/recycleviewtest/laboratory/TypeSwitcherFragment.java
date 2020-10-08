package com.example.roy.recycleviewtest.laboratory;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import com.example.roy.recycleviewtest.R;
import com.example.roy.recycleviewtest.databinding.FragmentTypeSwitcherBinding;
import com.example.roy.recycleviewtest.util.LogUtil;
import com.stanza.typeswitcher.TypeSwitcher;

public class TypeSwitcherFragment extends Fragment {
    FragmentTypeSwitcherBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_type_switcher, container, false);
        TypeSwitcher ascv2 = new TypeSwitcher(getContext());
        ascv2.setColors(Color.parseColor("#D24E4E"), Color.parseColor("#FFFFFF"))
                .setItems(new String[]{"Test4", "Test5", "XXXXXX6"}, new String[]{"4", "5", "6"})
                .setCornersRadius(7)
                .setup();
        ascv2.setOnSelectionChangedListener((identifier, value) -> {
            LogUtil.i("identifier:" + identifier + "value:" + value);
        });
        binding.holder.addView(ascv2);

        TypeSwitcher ascv = new TypeSwitcher(getContext());
        ascv.setItems(new String[]{"Test4", "Test5", "Test6"}, new String[]{"4", "5", "6"})
                .setCornersRadius(28)
                .setup();
        binding.holder.addView(ascv);
        return binding.getRoot();
    }

    public class Listener {

    }

    public class VM extends ViewModel {

    }
}
