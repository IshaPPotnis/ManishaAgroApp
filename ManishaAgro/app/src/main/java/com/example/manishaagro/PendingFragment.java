package com.example.manishaagro;

import android.content.Context;
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


import com.example.manishaagro.employee.EmployeeActivity;

import com.example.manishaagro.model.TripModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ConnectionDetector connectionDetector;
    public PendingAdapter pendingAdapter;
    PendingAdapter.RecyclerViewClickListener listener;
    private RecyclerView recyclerViewPen;
    private List<TripModel> rptDealerList;

    public PendingFragment() {

    }

    public ApiInterface apiInterface;

    public String employeeIdValue = "";




    private OnFragmentInteractionListener mListener;


    public static PendingFragment newInstance(String param1, String param2) {
        PendingFragment fragment = new PendingFragment();
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pending_fragment, container, false);
        connectionDetector=new ConnectionDetector();
        recyclerViewPen = view.findViewById(R.id.PendingTabrecyview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewPen.setLayoutManager(layoutManager);

        EmployeeActivity activity = (EmployeeActivity) getActivity();
        if (activity != null) {
            Bundle results = activity.getEmpData();
            String value1 = results.getString("tempval1");
            employeeIdValue = results.getString("tempval2EMPID");

            Log.v("Check Desig", "desig1" + value1);
            Log.v("check id", "id1" + employeeIdValue);
        }
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
        getPendingsLists();
    }

    private void getPendingsLists() {
        final String STEmp_ID1 = employeeIdValue;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Log.v("CodeIncome", "user1" + employeeIdValue);
        Call<List<TripModel>> listCall = apiInterface.getPendingListInEmp("get@AllPendingsNote", STEmp_ID1);
        listCall.enqueue(new Callback<List<TripModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<TripModel>> call, @NonNull Response<List<TripModel>> response) {
                rptDealerList = response.body();
                pendingAdapter= new PendingAdapter(rptDealerList, getContext(), listener);
                recyclerViewPen.setAdapter(pendingAdapter);
                pendingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<TripModel>> call, @NonNull Throwable t) {

                if (connectionDetector.isConnected(getContext()))
                {
                    Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
