package com.example.manishaagro;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manishaagro.model.ProfileModel;
import com.example.manishaagro.model.TripModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manishaagro.utils.Constants.REPORTS_EMPLOYEE;
import static com.example.manishaagro.utils.Constants.STATUS_EMPLOYEE_VISITED_CUSTOMER;

public class EmployeeFragment extends Fragment {
    AdapterEmp.RecyclerViewClickListener listener;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerView;
    private List<ProfileModel> rptEmpList;

    public AdapterEmp adapter;
    public ApiInterface apiInterface;
    public String managerId = "";

    private OnFragmentInteractionListener mListener;

    public EmployeeFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.employee, container, false);
        recyclerView = view.findViewById(R.id.rcyview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        ManagerActivity activity = (ManagerActivity) getActivity();
        if (activity != null) {
            Bundle results = activity.getMgrData();
            String value1 = results.getString("tempval2");
            managerId = results.getString("tempManagerIDval2");
        }

        listener=new AdapterEmp.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, int position) {
                Intent intentadpEmp = new Intent(getContext(), EmployeeVisitDetailsToMgrActivity.class);
                intentadpEmp.putExtra("EmployeeId",rptEmpList.get(position).getEmpId());
                intentadpEmp.putExtra("EmployeeName",rptEmpList.get(position).getName());
                intentadpEmp.putExtra("EmpIDNAME", "EmployeeIDNamePassed");
                startActivity(intentadpEmp);

            }
        };




        return view;
    }





    @Override
    public void onResume() {
        super.onResume();
        getReportsEmp(REPORTS_EMPLOYEE);

    }



    private void getReportsEmp(final String key) {
        final String managerIdForGetEmp = managerId;
        Log.v("yek", "Manager id" + managerIdForGetEmp);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<ProfileModel>> listCall = apiInterface.getAllReportsEmp(key, managerIdForGetEmp);
        listCall.enqueue(new Callback<List<ProfileModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<ProfileModel>> call, @NonNull Response<List<ProfileModel>> response) {
                rptEmpList = response.body();
                adapter = new AdapterEmp(rptEmpList, getContext(), listener);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<ProfileModel>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Have some error", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ProfileFragment.OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }  //     throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
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