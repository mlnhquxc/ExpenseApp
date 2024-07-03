package com.example.projectmobileapp.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.projectmobileapp.DatabaseHelper;
import com.example.projectmobileapp.R;
import com.example.projectmobileapp.model.TransactionGroups;
import com.example.projectmobileapp.model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddTransactionGroup extends Fragment {
    Boolean isexpenses;
    Boolean isEdit;
    Spinner transactionGroup;
    int selectedTransactiongroup;

    EditText money;

    Button pickDate;
    int day;
    int month;
    int year;
    MultiAutoCompleteTextView note;
    Button add,exit;

    SharedPreferences sharedPreferences;
    DatabaseHelper databaseHelper;
    String transactionType = "";

    public AddTransactionGroup(Boolean isexpenses) {
        this.isexpenses = isexpenses;
        this.isEdit = false;
    }
    List<TransactionGroups> transactionGroupsList;

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
        sharedPreferences = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        databaseHelper = new DatabaseHelper(getActivity());
        List<String> spinnerItems = new ArrayList<>();
        transactionGroupsList = new ArrayList<>();
        if (isexpenses){
            transactionGroupsList = databaseHelper.getTransactionGroupsExpenseList();
            transactionType = "Expense";
            for (TransactionGroups transactionGroups: transactionGroupsList) {
                spinnerItems.add(transactionGroups.getGroupName());
            }
        }else{
            transactionGroupsList = databaseHelper.getTransactionGroupsIncomeList();
            transactionType = "Income";
            for (TransactionGroups transactionGroups: transactionGroupsList) {
                spinnerItems.add(transactionGroups.getGroupName());
            }
        }

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_WEEK)-1;
        setDate();

        if (isEdit){
            money.setText( String.valueOf(200000));
        }





        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transactionGroup.setAdapter(adapter);
        transactionGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Xử lý khi một mục được chọn

                String itemText = parentView.getItemAtPosition(position).toString();
                for (TransactionGroups tg:transactionGroupsList) {
                    if (tg.getGroupName().equals(itemText)){
                        selectedTransactiongroup = tg.getGroupID();
                        break;
                    }
                }
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
                        month = monthpick+1;
                        day = daypick;
                        setDate();
                    }
                }, year,month-1,day);
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
                String username,notes, amountText;
                double  groupID, amount;
                username = sharedPreferences.getString("currentUsername", "");
                notes = note.getText().toString();
                groupID = selectedTransactiongroup;
                amountText = money.getText().toString();


                if (amountText.equals("")){
                    Toast.makeText(getContext(), "Vui lòng nhập số tiền", Toast.LENGTH_SHORT).show();
                }else{
                    amount = Double.parseDouble(amountText);
                    User user = databaseHelper.getUser(username);
                    double balance = user.getCash();
                    if (transactionType.equals("Expense")){
                        balance -= amount;
                    }else {
                        balance += amount;
                    }
                    databaseHelper.updateUserBalance(username,balance);
                    databaseHelper.addTransaction(username,transactionType,selectedTransactiongroup,amount,notes,day,month,year);
                    Toast.makeText(getContext(), "Thêm giao dịch thành công", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }



            }
        });

        return view;
    }



    public void setDate(){
        pickDate.setText("Ngày "+ day +", tháng " + month +" năm " + year);
    }
}