package com.example.manishaagro;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.manishaagro.employee.EmployeeActivity;

import com.example.manishaagro.model.TripModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView txtpenname,txtPenCnt;
    ConnectionDetector connectionDetector;
    public PendingAdapter pendingAdapter;
    PendingAdapter.RecyclerViewClickListener listener;
    private RecyclerView recyclerViewVisit;
    Button button1,button2,button3,button4,buttonpdtpen;

 //   private RecyclerView recyclerViewDemoimg;
   // private RecyclerView recyclerViewSelfimg;
    //private RecyclerView recyclerViewfollowimg;
    private List<TripModel> rptVisitList;
    private List<TripModel> rptDemoImageList;
    private List<TripModel> rptSelfieImageList;
    private List<TripModel> rptfollowupList;

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
        recyclerViewVisit = view.findViewById(R.id.PendingTabrecyviewVisit);
        button1=view.findViewById(R.id.button1);
        button2=view.findViewById(R.id.button2);
        button3=view.findViewById(R.id.button3);
        button4=view.findViewById(R.id.button4);
        txtpenname=view.findViewById(R.id.textPennding);
        txtPenCnt=view.findViewById(R.id.textPendingCount);
        buttonpdtpen=view.findViewById(R.id.buttonProductPen);
    //    recyclerViewDemoimg=view.findViewById(R.id.PendingTabrecyviewDemoImage);
      //  recyclerViewSelfimg=view.findViewById(R.id.PendingTabrecyviewSelfieImage);
       // recyclerViewfollowimg=view.findViewById(R.id.PendingTabrecyviewFollowImage);

        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        recyclerViewVisit.setLayoutManager(layoutManager1);

       /* RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        recyclerViewDemoimg.setLayoutManager(layoutManager2);

        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(getContext());
        recyclerViewSelfimg.setLayoutManager(layoutManager3);

        RecyclerView.LayoutManager layoutManager4 = new LinearLayoutManager(getContext());
        recyclerViewfollowimg.setLayoutManager(layoutManager4);*/

        EmployeeActivity activity = (EmployeeActivity) getActivity();
        if (activity != null) {
            Bundle results = activity.getEmpData();
            String value1 = results.getString("tempval1");
            employeeIdValue = results.getString("tempval2EMPID");

            Log.v("Check Desig", "desig1" + value1);
            Log.v("check id", "id1" + employeeIdValue);
        }
        visitPenCount();
        button1.setOnClickListener(this);
        buttonpdtpen.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.button1)
        {
            if (connectionDetector.isConnected(getContext()))
            {
                getPendingsLists();
            }
            else
            {
                Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
            }


        }
        if (v.getId()==R.id.button2)
        {
            if (connectionDetector.isConnected(getContext()))
            {
                getPendingsDemoImageLists();
            }
            else
            {
                Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
            }


        }
        if (v.getId()==R.id.button3)
        {
            if (connectionDetector.isConnected(getContext()))
            {
                getPendingsSelfieImageLists();
            }
            else
            {
                Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
            }


        }
        if (v.getId()==R.id.button4)
        {
            if (connectionDetector.isConnected(getContext()))
            {
                getPendingsFollowupLists();
            }
            else
            {
                Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
            }


        }
        if (v.getId()==R.id.buttonProductPen)
        {
            if (connectionDetector.isConnected(getContext()))
            {
                getPendingsProduct();
            }
            else
            {
                Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
            }


        }

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        super.onResume();
        visitPenCount();
    }
private void visitPenCount()
{
    final String STEmp_ID1 = employeeIdValue;
    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    Log.v("CodeIncome", "user1" + employeeIdValue);
    Call<TripModel> listCall = apiInterface.getVisitPendingCountInEmp("get@CountVisitPending", STEmp_ID1);
    listCall.enqueue(new Callback<TripModel>() {
        @Override
        public void onResponse(Call<TripModel> call, Response<TripModel> response) {
            String value=response.body().getValue();
            String message=response.body().getMassage();
            String visitcount=response.body().getData1();
            String demoimgcount=response.body().getData2();
            String selfieimgcount=response.body().getData3();
            String followupcount=response.body().getData4();
            String productPen=response.body().getData5();

            if(value.equals("1"))
            {
                txtPenCnt.setText(visitcount + " Visit Pending ,"+ productPen + " Product Pending " + demoimgcount + " Demo Photo Pending ,"+ selfieimgcount + " Selfie With Cutomer Pending ,"+ followupcount + " Follow Up Pending.");
            }
            else if(value.equals("0"))
            {
                    //Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<TripModel> call, Throwable t) {
            if (connectionDetector.isConnected(getContext()))
            {
                //Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
            }
        }
    });
}

    private void getPendingsProduct() {
        final String STEmp_ID1 = employeeIdValue;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Log.v("CodeIncome", "user1" + employeeIdValue);
        Call<List<TripModel>> listCall = apiInterface.getPendingListInEmp("get@AllProductPendingsVisit", STEmp_ID1);
        listCall.enqueue(new Callback<List<TripModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<TripModel>> call, @NonNull Response<List<TripModel>> response) {
                txtpenname.setText("PRODUCT PENDING");
                rptVisitList = response.body();
                pendingAdapter= new PendingAdapter(rptVisitList, getContext(), listener);
                recyclerViewVisit.getRecycledViewPool().clear();
                pendingAdapter.notifyDataSetChanged();
                recyclerViewVisit.setAdapter(pendingAdapter);

                recyclerViewVisit.stopScroll();
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

    private void getPendingsLists() {
        final String STEmp_ID1 = employeeIdValue;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Log.v("CodeIncome", "user1" + employeeIdValue);
        Call<List<TripModel>> listCall = apiInterface.getPendingListInEmp("get@AllPendingsVisit", STEmp_ID1);
        listCall.enqueue(new Callback<List<TripModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<TripModel>> call, @NonNull Response<List<TripModel>> response) {
                txtpenname.setText("VISIT PENDING");
                rptVisitList = response.body();
                pendingAdapter= new PendingAdapter(rptVisitList, getContext(), listener);
                recyclerViewVisit.getRecycledViewPool().clear();
                pendingAdapter.notifyDataSetChanged();
                recyclerViewVisit.setAdapter(pendingAdapter);

                recyclerViewVisit.stopScroll();
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
    private void getPendingsDemoImageLists() {
        final String STEmp_ID1 = employeeIdValue;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Log.v("CodeIncome", "user1" + employeeIdValue);
        Call<List<TripModel>> listCall = apiInterface.getPendingListInEmp("get@AllPendingsDemoImage", STEmp_ID1);
        listCall.enqueue(new Callback<List<TripModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<TripModel>> call, @NonNull Response<List<TripModel>> response) {
                txtpenname.setText("DEMO PHOTO PENDING");
                rptDemoImageList = response.body();
                pendingAdapter= new PendingAdapter(rptDemoImageList, getContext(), listener);
                recyclerViewVisit.getRecycledViewPool().clear();
                pendingAdapter.notifyDataSetChanged();
                recyclerViewVisit.setAdapter(pendingAdapter);

                recyclerViewVisit.stopScroll();
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
    private void getPendingsSelfieImageLists() {
        final String STEmp_ID1 = employeeIdValue;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Log.v("CodeIncome", "user1" + employeeIdValue);
        Call<List<TripModel>> listCall = apiInterface.getPendingListInEmp("get@AllPendingsSelfieImage", STEmp_ID1);
        listCall.enqueue(new Callback<List<TripModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<TripModel>> call, @NonNull Response<List<TripModel>> response) {
                txtpenname.setText("SELFIE WITH CUTOMER PENDING");
                rptSelfieImageList = response.body();
                pendingAdapter= new PendingAdapter(rptSelfieImageList, getContext(), listener);
                recyclerViewVisit.getRecycledViewPool().clear();
                pendingAdapter.notifyDataSetChanged();
                recyclerViewVisit.setAdapter(pendingAdapter);

                recyclerViewVisit.stopScroll();
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
    private void getPendingsFollowupLists() {
        final String STEmp_ID1 = employeeIdValue;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Log.v("CodeIncome", "user1" + employeeIdValue);
        Call<List<TripModel>> listCall = apiInterface.getPendingListInEmp("get@AllPendingsFollowImage", STEmp_ID1);
        listCall.enqueue(new Callback<List<TripModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<TripModel>> call, @NonNull Response<List<TripModel>> response) {
                txtpenname.setText("FOLLOW UP PENDING");
                rptfollowupList = response.body();
                pendingAdapter= new PendingAdapter(rptfollowupList, getContext(), listener);
                recyclerViewVisit.getRecycledViewPool().clear();
                pendingAdapter.notifyDataSetChanged();
                recyclerViewVisit.setAdapter(pendingAdapter);

                recyclerViewVisit.stopScroll();
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
