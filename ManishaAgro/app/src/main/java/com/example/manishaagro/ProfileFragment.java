package com.example.manishaagro;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.manishaagro.model.ProfileModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public ApiInterface apiInterface;
    private TextView userTextView;
    private TextView nameText;
    private TextView dateOfBirth;
    private TextView dateOfJoining;
    private TextView designation;
    private TextView mobileNumber;
    private TextView address;
    private TextView employeeId;
    private TextView emailId;
    private TextView useremltext;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString("params");
            Log.v("yek", "keyyy" + mParam1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile, container, false);
        userTextView = (TextView) view.findViewById(R.id.appusername);
        useremltext = view.findViewById(R.id.useremail);
        nameText = (TextView) view.findViewById(R.id.pfl_name);
        dateOfBirth = view.findViewById(R.id.pfl_dob);
        dateOfJoining = view.findViewById(R.id.pfl_doj);
        designation = view.findViewById(R.id.pfl_desig);
        employeeId = (TextView) view.findViewById(R.id.pfl_empid);
        address = view.findViewById(R.id.pfl_addr);
        mobileNumber = view.findViewById(R.id.pfl_mobile);
        emailId = view.findViewById(R.id.pfl_email);

        EmployeeActivity activity = (EmployeeActivity) getActivity();
        if (activity != null) {
            Bundle results = activity.getEmpData();
            String value1 = results.getString("tempval1");
            userTextView.setText(value1);
        }
        getProfileData("EmpProfile");
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            //     throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    private void getProfileData(final String key) {
        final String usrname = userTextView.getText().toString().trim();

        Log.v("yek", "rameshxxxxx" + usrname);


        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ProfileModel> callpro = apiInterface.getEmpProfile(key, usrname);
        callpro.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                String value = response.body().getValue();
                String message = response.body().getMassage();
                String resname = response.body().getName();
                String resempid = response.body().getEmpId();
                String resaddr = response.body().getContactDetails();
                String resmobile = response.body().getAddress();
                String resdesig = response.body().getDesignation();
                String resdob = response.body().getDob();
                String resdoj = response.body().getJoiningDate();
                String resemail = response.body().getEmail();
                Log.v("CodeIncome", "user1" + resname);
                if (value.equals("1")) {
                    nameText.setText(resname);
                    employeeId.setText(resempid);
                    address.setText(resaddr);
                    mobileNumber.setText(resmobile);
                    designation.setText(resdesig);
                    emailId.setText(resemail);
                    dateOfBirth.setText(resdob);
                    dateOfJoining.setText(resdoj);
                    useremltext.setText(resemail);
                    Log.v("CodeIncome", "user2" + resname);
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                } else if (value.equals("0")) {
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });


    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
