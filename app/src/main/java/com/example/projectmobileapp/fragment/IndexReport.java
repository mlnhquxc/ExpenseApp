package com.example.projectmobileapp.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projectmobileapp.R;
import com.example.projectmobileapp.adapter.StatisticalListDateAdapter;
import com.example.projectmobileapp.model.Bill;
import com.example.projectmobileapp.model.ListBill;
import com.example.projectmobileapp.model.ListBillDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class IndexReport extends Fragment {

    TextView thunhap,chitieu,tongcong;
    RecyclerView listbill;
    int date;
    StatisticalListDateAdapter statisticalListDateAdapter;

    public IndexReport(int date) {
        this.date = date;
    }


    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index_report, container, false);
        thunhap = view.findViewById(R.id.index_reprot_income);
        chitieu = view.findViewById(R.id.index_reprot_expenses);
        tongcong = view.findViewById(R.id.index_reprot_total);

        listbill = view.findViewById(R.id.index_report_listbill);
        listbill.setLayoutManager(new LinearLayoutManager(getActivity()));
        listbill.setNestedScrollingEnabled(false);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2024, Calendar.JUNE, 25); // Ngày 25/06/2024
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2024, Calendar.JUNE, 26); // Ngày 26/06/2024
        Calendar calendar3 = Calendar.getInstance();
        calendar3.set(2024, Calendar.JUNE, 27); // Ngày 27/06/2024

        // Tạo các đối tượng Bill
        Bill bill1 = new Bill(1, "abc", 1, 20000, calendar1.getTime(), "");
        Bill bill2 = new Bill(2, "def", 1, 30000, calendar2.getTime(), "");
        Bill bill3 = new Bill(3, "ghi", 1, 40000, calendar3.getTime(), "");

        List<Bill> bills1 = new ArrayList<>();
        List<Bill> bills2 = new ArrayList<>();
        List<Bill> bills3 = new ArrayList<>();
        List<Bill> bills4 = new ArrayList<>();
        bills1.add(bill1);
        bills1.add(bill2);
        bills2.add(bill1);
        bills2.add(bill3);
        bills3.add(bill2);
        bills3.add(bill3);
        bills4.add(bill1);
        bills4.add(bill1);
        bills4.add(bill3);

        ListBill listBill1 = new ListBillDay(1,1,1,bills1);
        ListBill listBill2 = new ListBillDay(2,1,1,bills2);
        ListBill listBill3 = new ListBillDay(3,1,1,bills3);
        ListBill listBill4 = new ListBillDay(4,1,1,bills4);

        List<ListBill> list2 = new ArrayList<>();
        List<ListBill> list3 = new ArrayList<>();
        List<ListBill> list4 = new ArrayList<>();
        list2.add(listBill1);
        list2.add(listBill2);
        list3.add(listBill3);
        list3.add(listBill4);
        list3.add(listBill4);
        list4.add(listBill4);
        list4.add(listBill4);
        list4.add(listBill4);
        list4.add(listBill4);

        switch (date) {
            case 0:
                statisticalListDateAdapter = new StatisticalListDateAdapter(list2);
                break;
            case 1:
                statisticalListDateAdapter = new StatisticalListDateAdapter(list3);
                break;
            case 2:
                statisticalListDateAdapter = new StatisticalListDateAdapter(list4);
                break;
            default:
                statisticalListDateAdapter = new StatisticalListDateAdapter(list4);
                break;
        }

        listbill.setAdapter(statisticalListDateAdapter);

        listbill.post(new Runnable() {
            @Override
            public void run() {
                setRecyclerViewHeightBasedOnChildren(listbill);
            }
        });

        statisticalListDateAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                setRecyclerViewHeightBasedOnChildren(listbill);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                setRecyclerViewHeightBasedOnChildren(listbill);
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                setRecyclerViewHeightBasedOnChildren(listbill);
            }
        });



        return view;
    }

    private void setRecyclerViewHeightBasedOnChildren(RecyclerView recyclerView) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < adapter.getItemCount(); i++) {
            RecyclerView.ViewHolder holder = adapter.createViewHolder(recyclerView, adapter.getItemViewType(i));
            adapter.onBindViewHolder(holder, i);
            holder.itemView.measure(
                    View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            totalHeight += holder.itemView.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        params.height = totalHeight + (recyclerView.getItemDecorationCount() * (adapter.getItemCount() - 1));
        recyclerView.setLayoutParams(params);
        recyclerView.requestLayout();

    }
}