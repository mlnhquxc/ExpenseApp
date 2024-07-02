package com.example.projectmobileapp.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projectmobileapp.AddTransaction;
import com.example.projectmobileapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddTransactionGroup extends Fragment {
    Boolean isexpenses;
    Boolean isEdit;
    Spinner transactionGroup;
    String selectedTransactiongroup;

    EditText money;

    Button pickDate;
    int day;
    int month;
    int year;
    MultiAutoCompleteTextView note;
    Button add,exit;

    public AddTransactionGroup(Boolean isexpenses) {
        this.isexpenses = isexpenses;
        this.isEdit = false;
    }

    @SuppressLint("ResourceType")
    public AddTransactionGroup(int idtransaction) {
        this.isEdit = true;
    }




    private List<Button> buttons;
    @SuppressLint({"MissingInflatedId", "ResourceType"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_transaction_group, container, false);
        transactionGroup = view.findViewById(R.id.transactiongrouplist);
        money = view.findViewById(R.id.money);
        pickDate = view.findViewById(R.id.pickDate);
        note = view.findViewById(R.id.note);
        add = view.findViewById(R.id.addtransaction);
        exit = view.findViewById(R.id.exit);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_WEEK)-1;
        setDate();

        if (isEdit){
            money.setText( String.valueOf(200000));
        }

        List<String> spinnerItems = new ArrayList<>();
        spinnerItems.add("Item 1");
        spinnerItems.add("Item 2");
        spinnerItems.add("Item 3");
        spinnerItems.add("Item 4");


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transactionGroup.setAdapter(adapter);
        transactionGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Xử lý khi một mục được chọn
                selectedTransactiongroup = parentView.getItemAtPosition(position).toString();
//                Toast.makeText(getContext(), "Selected: " + selectedTransactiongroup, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Xử lý khi không có mục nào được chọn
            }
        });


        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int yearpick, int monthpick, int daypick) {
                        year = yearpick;
                        month = monthpick;
                        day = daypick;
                        setDate();
                    }
                }, year,month,day);
                datePickerDialog.show();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Thêm giao dịch thành công", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });

        return view;
    }

    public void setDate(){
        pickDate.setText("Ngày "+ day +", tháng " + month +" năm " + year);
    }
}