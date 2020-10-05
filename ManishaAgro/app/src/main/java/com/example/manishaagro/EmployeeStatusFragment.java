package com.example.manishaagro;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manishaagro.model.TripModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manishaagro.utils.Constants.EMPLOYEE_VISITED_CUSTOMER;
import static com.example.manishaagro.utils.Constants.STATUS_DATE_OF_RETURN;
import static com.example.manishaagro.utils.Constants.STATUS_DATE_OF_TRAVEL;
import static com.example.manishaagro.utils.Constants.STATUS_EMPLOYEE_VISITED_CUSTOMER;
import static com.example.manishaagro.utils.Constants.STATUS_VISITED_CUSTOMER_NAME;

public class EmployeeStatusFragment extends Fragment {

    AdapterStatus.RecyclerViewClickListener listener;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerView;
    private List<TripModel> rptEmpList;
    public AdapterStatus adapter;
    public ApiInterface apiInterface;
    public String STEmpNames = "";
    public String STEmp_ID = "";

    private OnFragmentInteractionListener mListener;

    public EmployeeStatusFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            String parameter1 = getArguments().getString(ARG_PARAM1);
            String parameter2 = getArguments().getString(ARG_PARAM2);
            System.out.println(parameter1 + "" + parameter2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.employeestatus, container, false);
        recyclerView = view.findViewById(R.id.StatusTabrecyview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        EmployeeActivity activity = (EmployeeActivity) getActivity();
        if (activity != null) {
            Bundle results = activity.getEmpData();
            STEmpNames = results.getString("tempval1");
            STEmp_ID = results.getString("tempval2EMPID");

        }


        listener = new AdapterStatus.RecyclerViewClickListener() {
            @Override
            public void onCardClick(View view, int position) {
                Intent intent = new Intent(getContext(), EmployeeStatusActivity.class);
                intent.putExtra(STATUS_VISITED_CUSTOMER_NAME, rptEmpList.get(position).getVisitedCustomerName());
                intent.putExtra(STATUS_DATE_OF_TRAVEL, rptEmpList.get(position).getDateOfTravel());
                intent.putExtra(STATUS_DATE_OF_RETURN, rptEmpList.get(position).getDateOfReturn());
                intent.putExtra("EmpStsVal", STATUS_EMPLOYEE_VISITED_CUSTOMER);
                startActivity(intent);
            }
        };
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ProfileFragment.OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }  //     throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
    }

    @Override
    public void onResume() {
        super.onResume();
        getEmpVisit();
    }

    private void getEmpVisit() {
        final String STEmp_ID1 = STEmp_ID;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Log.v("CodeIncome", "user1" + STEmp_ID);
        Call<List<TripModel>> listCall = apiInterface.getVisitedAllCust(EMPLOYEE_VISITED_CUSTOMER, STEmp_ID1);
        listCall.enqueue(new Callback<List<TripModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<TripModel>> call, @NonNull Response<List<TripModel>> response) {
                rptEmpList = response.body();
                adapter = new AdapterStatus(rptEmpList, getContext(), listener);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<TripModel>> call, @NonNull Throwable t) {
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public OnFragmentInteractionListener getmListener() {
        return mListener;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
