package com.example.foodgocustomer.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodgocustomer.R;
import com.example.foodgocustomer.databinding.FragmentProfileBinding;


public class FragmentProfile extends Fragment {

    private FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        setUpOptions();
        return binding.getRoot();
    }


    private void setUpOptions() {
        // CardView 1
        binding.itemProfile.tvOption.setText("Thông tin cá nhân");
     //   binding.itemProfile.imgIcon.setImageResource(R.drawable.ic_person);

        binding.itemCash.tvOption.setText("Ví & Thanh toán");
      //  binding.itemCash.imgIcon.setImageResource(R.drawable.ic_wallet);

        binding.itemSecurity.tvOption.setText("Bảo mật tài khoản");
     //   binding.itemSecurity.imgIcon.setImageResource(R.drawable.ic_lock);

        // CardView 2
        binding.itemOrders.tvOption.setText("Đơn hàng của tôi");
       // binding.itemOrders.imgIcon.setImageResource(R.drawable.ic_order);

        binding.itemVouchers.tvOption.setText("Kho Voucher");
       // binding.itemVouchers.imgIcon.setImageResource(R.drawable.ic_voucher);

        binding.itemReviews.tvOption.setText("Đánh giá của tôi");
       // binding.itemReviews.imgIcon.setImageResource(R.drawable.ic_star);

        // CardView 3
        binding.itemNotifications.tvOption.setText("Thông báo");
      //  binding.itemNotifications.imgIcon.setImageResource(R.drawable.ic_bell);

        binding.itemHelp.tvOption.setText("Trung tâm hỗ trợ");
      //  binding.itemHelp.imgIcon.setImageResource(R.drawable.ic_help);

        binding.itemLogout.tvOption.setText("Đăng xuất");
       // binding.itemLogout.imgIcon.setImageResource(R.drawable.ic_logout);
    }
}