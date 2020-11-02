package com.example.manishaagro.employee;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manishaagro.ApiClient;
import com.example.manishaagro.ApiInterface;
import com.example.manishaagro.DealerProductListActivity;
import com.example.manishaagro.R;
import com.example.manishaagro.model.DealerModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealerFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    DealerAdapterInEmp.RecyclerViewClickListener listener;

    private RecyclerView recyclerViewDealer;
    private List<DealerModel> rptDealerList;
    public DealerAdapterInEmp adapterDealer;
    public ApiInterface apiInterface;
    public String STEmpNames = "";
    public String STEmp_ID = "";
    boolean fabflag=true;


    public String employeeIdValue = "";




    private OnFragmentInteractionListener mListener;

    public DealerFragment() {
        // Required empty public constructor
    }

    public static DealerFragment newInstance(String param1, String param2) {
        DealerFragment fragment = new DealerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);


            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dealer_fragment, container, false);

        recyclerViewDealer = view.findViewById(R.id.DealerTabrecyview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewDealer.setLayoutManager(layoutManager);

        EmployeeActivity activity = (EmployeeActivity) getActivity();
        if (activity != null) {
            Bundle results = activity.getEmpData();
            String value1 = results.getString("tempval1");
            employeeIdValue = results.getString("tempval2EMPID");

            Log.v("Check Desig", "desig1" + value1);
            Log.v("check id", "id1" + employeeIdValue);
        }



        listener=new DealerAdapterInEmp.RecyclerViewClickListener() {
            @Override
            public void onDealerProductDetailClick(View view, int position) {

                    Intent intent = new Intent(getContext(), DealerProductListActivity.class);
                    intent.putExtra("emp_dealer_name", rptDealerList.get(position).getDealername());
                    intent.putExtra("emp_dealer_product_purchase_date", rptDealerList.get(position).getDate_of_purchase());
                    intent.putExtra("EmpId_Dealer_product",employeeIdValue);
                    intent.putExtra("Emp_dealerProductVal", "Emp_Dealer_Product_List_Status");
                    startActivity(intent);


            }
        };




        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        super.onResume();
        getDealerLists();
    }

    private void getDealerLists() {
        final String STEmp_ID1 = employeeIdValue;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Log.v("CodeIncome", "user1" + employeeIdValue);
        Call<List<DealerModel>> listCall = apiInterface.getAllDealerListInEmp("get@llDealerListInEmp", STEmp_ID1);
        listCall.enqueue(new Callback<List<DealerModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<DealerModel>> call, @NonNull Response<List<DealerModel>> response) {
                rptDealerList = response.body();
                adapterDealer = new DealerAdapterInEmp(rptDealerList, getContext(), listener);
                recyclerViewDealer.setAdapter(adapterDealer);
                adapterDealer.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<DealerModel>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

}
