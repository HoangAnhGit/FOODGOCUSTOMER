package com.example.foodgocustomer.View.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.foodgocustomer.R;
import com.example.foodgocustomer.View.Adapter.BannerAdapter;
import com.example.foodgocustomer.databinding.FragmentIndexBinding;

import java.util.Arrays;
import java.util.List;

public class FragmentIndex extends Fragment {

    private ViewPager2 bannerViewPager;
    private Handler handler = new Handler();
    private int currentPage = 0;
    private FragmentIndexBinding binding;

    private final List<Integer> bannerImages = Arrays.asList(
           R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3
    );

    private final Runnable bannerRunnable = new Runnable() {
        @Override
        public void run() {
            if (bannerImages.isEmpty()) return;

            currentPage = (currentPage + 1) % bannerImages.size();
            bannerViewPager.setCurrentItem(currentPage, true);
            handler.postDelayed(this, 3000);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentIndexBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        startViewPager();

        return view;
    }

    private void startViewPager(){
        // Khởi tạo ViewPager
        bannerViewPager = binding.bannerViewPager;
        BannerAdapter adapter = new BannerAdapter(this.getContext(), bannerImages);
        bannerViewPager.setAdapter(adapter);

        // Bắt đầu auto slide
        handler.postDelayed(bannerRunnable, 3000);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(bannerRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(bannerRunnable, 3000);
    }
}
