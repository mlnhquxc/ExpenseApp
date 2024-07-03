package com.example.projectmobileapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class AccountFragment extends Fragment {
    LinearLayout lnAccountManager;
    LinearLayout lnAddTransactionGroup;
    SharedPreferences sharedPreferences;
    TextView tvUsername, tvEmail;
    DatabaseHelper databaseHelper;
    Button btn_save, btn_cancel;
    EditText etGroupName;

    //phước thêm
    Spinner spTransactionType;
    String selectedTransactionType = "";
    //

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        lnAccountManager = view.findViewById(R.id.tab_account_management);
        lnAddTransactionGroup = view.findViewById(R.id.tab_add_transaction_group);
        tvUsername = view.findViewById(R.id.tvUsernameLoggedIn);
        tvEmail = view.findViewById(R.id.tvEmailLoggedIn);
        databaseHelper = new DatabaseHelper(getContext());

        sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);

        String username = sharedPreferences.getString("currentUsername", "");
        tvUsername.setText(username);

        String email = databaseHelper.getUserEmail(username);
        tvEmail.setText(email);

        lnAccountManager.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AccountManagerActivity.class);
            startActivity(intent);
        });

        lnAddTransactionGroup.setOnClickListener(v -> showAddGroupDialog());

        return view;
    }

    private void showAddGroupDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_transaction_group, null);
        builder.setView(dialogView);

        etGroupName = dialogView.findViewById(R.id.etGroupName);
        btn_save = dialogView.findViewById(R.id.btn_saveGroup);
        btn_cancel = dialogView.findViewById(R.id.btn_cancelGroup);

        //phước thêm

        spTransactionType = dialogView.findViewById(R.id.sp_transaction_type);
        List<String> spinnerItems = new ArrayList<>();
        spinnerItems.add("Chi tiêu");
        spinnerItems.add("Thu nhập");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTransactionType.setAdapter(adapter);

        spTransactionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Xử lý khi một mục được chọn
                String itemtext = spinnerItems.get(position);
                if (itemtext.equals("Chi tiêu")){
                    selectedTransactionType = "Expense";
                }else{
                    selectedTransactionType = "Income";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Xử lý khi không có mục nào được chọn
            }
        });

        //phước thêm



        AlertDialog alertDialog = builder.create();

        btn_save.setOnClickListener(v -> {
            String groupName = etGroupName.getText().toString().trim();
            if (!groupName.isEmpty()){
                if (databaseHelper.isGroupExists(groupName)) {
                    Toast.makeText(requireContext(), "Nhóm giao dịch này đã tồn tại", Toast.LENGTH_LONG).show();
                } else {
                    boolean isAdded = databaseHelper.addTransactionGroup(groupName,selectedTransactionType);
                    if (isAdded) {
                        Toast.makeText(requireContext(), "Thêm nhóm giao dịch thành công", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(requireContext(), "Thêm nhóm giao dịch thất bại", Toast.LENGTH_LONG).show();
                    }
                    alertDialog.dismiss();
                }
            }
            else {
                Toast.makeText(requireContext(), "Vui lòng điền nhóm giao dịch cần thêm", Toast.LENGTH_LONG).show();
            }
        });

        btn_cancel.setOnClickListener(v -> alertDialog.dismiss());
        alertDialog.show();
    }
}