package com.example.manishaagro.manager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manishaagro.ConnectionDetector;
import com.example.manishaagro.EmployeePendingDataToMgrActivity;
import com.example.manishaagro.ReadingDataToMgrActivity;
import com.example.manishaagro.employee.EmployeeVisitDetailsToMgrActivity;
import com.example.manishaagro.manager.AdapterEmp;
import com.example.manishaagro.ApiClient;
import com.example.manishaagro.ApiInterface;
import com.example.manishaagro.manager.DealerDataToMgrActivity;
import com.example.manishaagro.manager.ManagerActivity;
import com.example.manishaagro.R;
import com.example.manishaagro.model.ProfileModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manishaagro.utils.Constants.REPORTS_EMPLOYEE;

public class EmployeeFragment extends Fragment {
    AdapterEmp.RecyclerViewClickListener listener;
    ConnectionDetector connectionDetector;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerView;
    private List<ProfileModel> rptEmpList;
    public AdapterEmp adapter;
    public ApiInterface apiInterface;
    public String managerId = "";
    private OnFragmentInteractionListener mListener;

    AlertDialog alertDialog1;
    CharSequence[] values = {"Employee Visit Data ","Employee Visit Pending Data","Employee KM Data ","Dealer Data "};

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
        connectionDetector=new ConnectionDetector();
        recyclerView = view.findViewById(R.id.rcyview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        ManagerActivity activity = (ManagerActivity) getActivity();
        if (activity != null) {
            Bundle results = activity.getMgrData();
            String value1 = results.getString("tempval2");
            managerId = results.getString("tempManagerIDval2");
        }

     /*   listener=new AdapterEmp.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, int position) {
                Intent intentadpEmp = new Intent(getContext(), EmployeeVisitDetailsToMgrActivity.class);
                intentadpEmp.putExtra("EmployeeId",rptEmpList.get(position).getEmpId());
                intentadpEmp.putExtra("EmployeeName",rptEmpList.get(position).getName());
                intentadpEmp.putExtra("EmpIDNAME", "EmployeeIDNamePassed");
                startActivity(intentadpEmp);
            }
        };*/
        listener=new AdapterEmp.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, final int position) {


                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.MyAlertDialogStyle);

                    builder.setTitle("Select Your Choice");

                    builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {


                        public void onClick(DialogInterface dialog, int item) {

                            switch(item)
                            {
                                case 0:

                                    if (connectionDetector.isConnected(getContext()))
                                    {
                                        Intent intentadpEmp = new Intent(getContext(), EmployeeVisitDetailsToMgrActivity.class);
                                        intentadpEmp.putExtra("EmployeeId",rptEmpList.get(position).getEmpId());
                                        intentadpEmp.putExtra("EmployeeName",rptEmpList.get(position).getName());
                                        intentadpEmp.putExtra("EmpIDNAME", "EmployeeIDNamePassed");
                                        startActivity(intentadpEmp);

                                    }
                                    else
                                    {
                                        Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
                                    }

                                    break;
                                case 1:
                                    Intent intentadpEmp = new Intent(getContext(), EmployeePendingDataToMgrActivity.class);
                                    intentadpEmp.putExtra("EmployeeId",rptEmpList.get(position).getEmpId());
                                    intentadpEmp.putExtra("EmployeeName",rptEmpList.get(position).getName());
                                    intentadpEmp.putExtra("EmpPending", "EmployeeVisitPendingToMgr");
                                    startActivity(intentadpEmp);
                                    break;

                                case 2:

                                    if (connectionDetector.isConnected(getContext()))
                                    {

                                        Intent intentadpDelaerData = new Intent(getContext(), ReadingDataToMgrActivity.class);
                                        intentadpDelaerData.putExtra("EmployeeId",rptEmpList.get(position).getEmpId());
                                        intentadpDelaerData.putExtra("EmployeeName",rptEmpList.get(position).getName());
                                        intentadpDelaerData.putExtra("EmpIDNAMERead", "EmployeeIDNamePassedRead");
                                        startActivity(intentadpDelaerData);
                                    }
                                    else
                                    {
                                        Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
                                    }

                                    break;
                                case 3:

                                    if (connectionDetector.isConnected(getContext()))
                                    {

                                        Intent intentadpDelaerData = new Intent(getContext(), DealerDataToMgrActivity.class);
                                        intentadpDelaerData.putExtra("EmployeeId",rptEmpList.get(position).getEmpId());
                                        intentadpDelaerData.putExtra("EmployeeName",rptEmpList.get(position).getName());
                                        intentadpDelaerData.putExtra("EmpIDNAME", "EmployeeIDNamePassed");
                                        startActivity(intentadpDelaerData);
                                    }
                                    else
                                    {
                                        Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
                                    }


                                    break;


                            }
                            alertDialog1.dismiss();
                        }
                    });
                    alertDialog1 = builder.create();
                    alertDialog1.show();


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
                if (connectionDetector.isConnected(getContext()))
                {
                    Toast.makeText(getContext(), "Have some error", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
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