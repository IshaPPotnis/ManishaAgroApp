package com.example.manishaagro.employee;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import com.example.manishaagro.ApiClient;
import com.example.manishaagro.ApiInterface;
import com.example.manishaagro.ConnectionDetector;
import com.example.manishaagro.DBHelper;
import com.example.manishaagro.MessageDialog;
import com.example.manishaagro.MeterActivity;
import com.example.manishaagro.R;
import com.example.manishaagro.model.TripModel;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manishaagro.DBHelper.DB_NAME;
import static com.example.manishaagro.DBHelper.EMPLOYEE_TRIPS;
import static com.example.manishaagro.utils.Constants.EMPLOYEE_VISITED_CUSTOMER;
import static com.example.manishaagro.utils.Constants.STATUS_DATE_OF_RETURN;
import static com.example.manishaagro.utils.Constants.STATUS_DATE_OF_TRAVEL;
import static com.example.manishaagro.utils.Constants.STATUS_EMPLOYEE_VISITED_CUSTOMER;
import static com.example.manishaagro.utils.Constants.STATUS_VISITED_CUSTOMER_NAME;

public class EmployeeStatusFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    AdapterStatus.RecyclerViewClickListener listener;
    ConnectionDetector connectionDetector;
    Context context;

    MessageDialog messageDialog;

    DBHelper controllerdb1;
    SQLiteDatabase sqLiteDatabase1;

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
        View view = inflater.inflate(R.layout.employeestatus, container, false);
        recyclerView = view.findViewById(R.id.StatusTabrecyview);
        connectionDetector = new ConnectionDetector();
        messageDialog =new MessageDialog();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        EmployeeActivity activity = (EmployeeActivity) getActivity();
        controllerdb1=new DBHelper(activity);
        if (activity != null) {
            Bundle results = activity.getEmpData();
            STEmpNames = results.getString("tempval1");
            STEmp_ID = results.getString("tempval2EMPID");
        }
        final FloatingActionsMenu fab = ((EmployeeActivity) getActivity()).getFloatingActionMenu();
        final FloatingActionButton fab1 = ((EmployeeActivity) getActivity()).getFloatingButton1();
        final FloatingActionButton fab2 = ((EmployeeActivity) getActivity()).geFloatingButton2();
        final FloatingActionButton fab3 = ((EmployeeActivity) getActivity()).geFloatingButton3();
        final FloatingActionButton fab4 = ((EmployeeActivity) getActivity()).geFloatingButton4();
        final FloatingActionButton fab5 = ((EmployeeActivity) getActivity()).geFloatingButton5();

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectionDetector.isConnected(getContext())) {
                    Intent visitIntent = new Intent(getContext(), CustomerVisitStartActivity.class);
                    visitIntent.putExtra("visitedEmployee", STEmp_ID);
                    visitIntent.putExtra("visitedEmpID", "Emp@ID");
                    startActivity(visitIntent);
                } else {
                    Intent visitIntent = new Intent(getContext(), CustomerVisitStartActivity.class);
                    visitIntent.putExtra("visitedEmployee", STEmp_ID);
                    visitIntent.putExtra("visitedEmpID", "Emp@ID");
                    startActivity(visitIntent);
                }
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectionDetector.isConnected(getContext())) {
                    Intent visitIntent = new Intent(getContext(), CustomerVisitEndActivity.class);
                    visitIntent.putExtra("visitedEmployee", STEmp_ID);
                    startActivity(visitIntent);
                } else {
                    Intent visitIntent = new Intent(getContext(), CustomerVisitEndActivity.class);
                    visitIntent.putExtra("visitedEmployee", STEmp_ID);
                    startActivity(visitIntent);
                }
            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectionDetector.isConnected(getContext())) {
                    Intent visitIntent = new Intent(getContext(), CheckFollowUpActivity.class);
                    visitIntent.putExtra("visitedEmployeeFollowup", STEmp_ID);
                    startActivity(visitIntent);
                } else {
                    Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                    Intent visitIntent = new Intent(getContext(), CheckFollowUpActivity.class);
                    visitIntent.putExtra("visitedEmployeeFollowup", STEmp_ID);
                    startActivity(visitIntent);
                }
            }
        });
        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectionDetector.isConnected(getContext())) {
                    Intent visitIntent = new Intent(getContext(), DealerEntryActivity.class);
                    visitIntent.putExtra("visitedEmployeeDealerEntry", STEmp_ID);
                    startActivity(visitIntent);
                } else {
                    Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                    Intent visitIntent = new Intent(getContext(), DealerEntryActivity.class);
                    visitIntent.putExtra("visitedEmployeeDealerEntry", STEmp_ID);
                    startActivity(visitIntent);
                }
            }
        });
        fab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectionDetector.isConnected(getContext())) {
                    Intent visitIntent = new Intent(getContext(), MeterActivity.class);
                    visitIntent.putExtra("visitedEmployeeMeterEntry", STEmp_ID);
                    startActivity(visitIntent);
                } else {
                    Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                    Intent visitIntent = new Intent(getContext(), MeterActivity.class);
                    visitIntent.putExtra("visitedEmployeeMeterEntry", STEmp_ID);
                    startActivity(visitIntent);
                }
            }
        });
        listener = new AdapterStatus.RecyclerViewClickListener() {
            @Override
            public void onCardClick(View view, int position) {
                if (connectionDetector.isConnected(getContext())) {
                    Intent intent = new Intent(getContext(), EmployeeStatusActivity.class);
                    intent.putExtra(STATUS_VISITED_CUSTOMER_NAME, rptEmpList.get(position).getVisitedCustomerName());
                    intent.putExtra(STATUS_DATE_OF_TRAVEL, rptEmpList.get(position).getDateOfTravel());
                    intent.putExtra(STATUS_DATE_OF_RETURN, rptEmpList.get(position).getDateOfReturn());
                    intent.putExtra("EMPLOYEE_ID_STATUS", STEmp_ID);
                    intent.putExtra("EmpStsVal", STATUS_EMPLOYEE_VISITED_CUSTOMER);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                }
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
    public void onResume() {
        super.onResume();

     /* 12/25/2020  sqLiteDatabase1 = controllerdb1.getWritableDatabase();
        Cursor mcursor = sqLiteDatabase1.rawQuery("SELECT count(*) FROM " +EMPLOYEE_TRIPS,  null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        System.out.println("Call was cancelled forcefully"+STEmp_ID);
        if(icount>0)
        {
            populateRecyclerView(STEmp_ID);


        }
        else
        {
            getEmpVisit();
        }*/
        getEmpVisit();

    }

  /* 12/25/2020 private void populateRecyclerView(String empids) {

        DBHelper helperv2 = new DBHelper(getContext());
        rptEmpList=helperv2.recordsAllVisitdataList(empids);
        System.out.println("Call was list "+rptEmpList);

        adapter = new AdapterStatus(rptEmpList, getContext(), listener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.notifyDataSetChanged();
    }*/

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
                if (connectionDetector.isConnected(getContext())) {
                    //Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                   // Toast.makeText(getContext(),"Cannot Communicate to Server",Toast.LENGTH_LONG).show();
                    messageDialog.msgDialog(getContext());
                } else {
                    Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                }
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