package com.example.manishaagro;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manishaagro.utils.EmployeeType;

import java.util.ArrayList;
import java.util.List;

public class EmployeeFragment extends Fragment {
    private RecyclerView recyclerView;
    public AdapterEmp adapter;
    AdapterEmp.RecyclerViewClickListener listener;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public ApiInterface apiInterface;
    private List<ProfileModel> rptEmpList;

    private OnFragmentInteractionListener mListener;

    public EmployeeFragment() {
        // Required empty public constructor
    }
/*
    public static EmployeeFragment newInstance(String param1, String param2) {
        EmployeeFragment fragment = new EmployeeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

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
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getReportsEmp();
    }

    private void getReportsEmp() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        ProfileModel profileModel1 = new ProfileModel();
        profileModel1.setEmpid("232");
        profileModel1.setDesignation(EmployeeType.EMPLOYEE.name());
        profileModel1.setName("Ramesh");

        ProfileModel profileModel2 = new ProfileModel();
        profileModel2.setEmpid("134");
        profileModel2.setDesignation(EmployeeType.EMPLOYEE.name());
        profileModel2.setName("Umesh");

        rptEmpList = new ArrayList<>();
        rptEmpList.add(profileModel1);
        rptEmpList.add(profileModel2);

        adapter = new AdapterEmp(rptEmpList, getContext(), listener);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

/*        Call<List<ProfileModel>> listCall = apiInterface.getAllReportsEmp(REPORTS_EMPLOYEE);
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
        });*/
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