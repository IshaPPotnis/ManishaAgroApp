package com.example.manishaagro;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manishaagro.model.ProfileModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manishaagro.utils.Constants.REPORTS_EMPLOYEE;

public class EmployeeFragment extends Fragment {
    AdapterEmp.RecyclerViewClickListener listener;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerView;
    private List<ProfileModel> rptEmpList;
    public AdapterEmp adapter;
    public ApiInterface apiInterface;
    public String ManagerIDValue = "";

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
            ManagerIDValue = results.getString("tempManagerIDval2");
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getReportsEmp(REPORTS_EMPLOYEE);
    }

    private void getReportsEmp(final String key) {
        final String managerIdForGetEmp = ManagerIDValue;
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