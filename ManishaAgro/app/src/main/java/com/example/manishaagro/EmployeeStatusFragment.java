package com.example.manishaagro;

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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeStatusFragment extends Fragment {

    private RecyclerView recyclerView;
    public AdapterStatus adapter;
    AdapterStatus.RecyclerViewClickListener listener;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public ApiInterface apiInterface;
    public String STEmpNames;
    private List<TripModel> rptEmpList;

    private OnFragmentInteractionListener mListener;

    public EmployeeStatusFragment()
    {

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
        View view=inflater.inflate(R.layout.employeestatus, container, false);

        recyclerView = view.findViewById(R.id.StatusTabrecyview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        EmployeeActivity activity = (EmployeeActivity) getActivity();
        if (activity != null) {
            Bundle results = activity.getEmpData();
           STEmpNames = results.getString("tempval1");

        }


        listener= new AdapterStatus.RecyclerViewClickListener() {
            @Override
            public void onCardClick(View view, int position) {
                Intent intent = new Intent(getContext(), EmployeeStatusActivity.class);
                intent.putExtra("StatusCustName", rptEmpList.get(position).getVisitedCustomerName());
                intent.putExtra("StatusDtTravel", rptEmpList.get(position).getDateOfTravel());
                intent.putExtra("StatusdtReturn", rptEmpList.get(position).getDateOfReturn());

                String EmpStsVal="EmployeeStatusVisitedCustomer";
                intent.putExtra("EmpStsVal", EmpStsVal);
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

        getEmpVisit("EmpvisitedCustomer");
    }
    private void getEmpVisit(final String key)
    {




        final String custname=STEmpNames;

        Log.v("CodeIncom", "user1" + custname);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ProfileModel> listCall1 = apiInterface.getEmpid(key,custname);
        listCall1.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {

                String value = response.body().getValue();
                String message = response.body().getMassage();

                final String resempid = response.body().getEmpId();

                if (value.equals("1")) {
                    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                    Log.v("CodeIncome", "user1" + resempid);
                    Call<List<TripModel>> listCall = apiInterface.getVisitedAllCust(key,resempid);
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

                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                } else if (value.equals("0")) {
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {

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
