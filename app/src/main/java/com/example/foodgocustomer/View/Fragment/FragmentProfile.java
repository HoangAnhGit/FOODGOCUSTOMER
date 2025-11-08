package com.example.foodgocustomer.View.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodgocustomer.R;
import com.example.foodgocustomer.Util.TokenManager;
import com.example.foodgocustomer.View.Activity.EditProfileActivity;
import com.example.foodgocustomer.View.Activity.LoginActivity;
import com.example.foodgocustomer.View.Activity.ManagerAddressActivity;
import com.example.foodgocustomer.View.MainActivity;
import com.example.foodgocustomer.ViewModel.ProfileViewModel;
import com.example.foodgocustomer.databinding.FragmentProfileBinding;
import com.example.foodgocustomer.network.DTO.UserProfileDto;


public class FragmentProfile extends Fragment {

    private FragmentProfileBinding binding;
    private ProfileViewModel profileViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);


        profileViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()))
                .get(ProfileViewModel.class);

        setUpOptions();
        loadProfileData();


        binding.itemProfile.tvOption.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), EditProfileActivity.class);
            startActivity(intent);
        });

        binding.btnManageAddress.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), ManagerAddressActivity.class);
            startActivity(intent);
        });

        binding.itemLogout.getRoot().setOnClickListener(view -> {
            logout();
        });

        return binding.getRoot();
    }


    public void logout() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Xác nhận đăng xuất") // Tiêu đề
                .setMessage("Bạn có chắc chắn muốn đăng xuất khỏi ứng dụng không?") // Nội dung thông báo


                .setPositiveButton("Có", (dialog, which) -> {

                    TokenManager tokenManager = TokenManager.getInstance(requireContext());
                    tokenManager.clear();

                    Intent intent = new Intent(requireContext(), LoginActivity.class);


                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    requireActivity().finish();
                })

                // Nút "Không" (Hủy bỏ)
                .setNegativeButton("Không", (dialog, which) -> {
                    // Đóng hộp thoại mà không làm gì cả
                    dialog.dismiss();
                })

                .show(); // Hiển thị hộp thoại
    }

    private void setUpOptions() {
        // ... (Giữ nguyên phần này)
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

    private void loadProfileData() {
        // ... (Giữ nguyên phần này)
        profileViewModel.getProfile().observe(getViewLifecycleOwner(), result -> {
            switch (result.status) {
                case SUCCESS:
                    UserProfileDto user = result.data;
                    if (user != null) {
                        binding.tvName.setText(user.getFullName());
                        binding.tvPhone.setText(user.getPhoneNumber());
                        binding.tvEmail.setText(user.getEmail());

                        // Load avatar (nếu có)
                        if (user.getAvatarUrl() != null && !user.getAvatarUrl().isEmpty()) {
                            Glide.with(this)
                                    .load(user.getAvatarUrl())
                                    .placeholder(R.drawable.avartar)
                                    .into(binding.imgAvatar);
                        }
                    }
                    break;
                case ERROR:
                    Toast.makeText(binding.getRoot().getContext(), "Lỗi khi tải thông tin", Toast.LENGTH_SHORT).show();
                    break;
                case LOADING:
                    break;
            }
        });
    }
}