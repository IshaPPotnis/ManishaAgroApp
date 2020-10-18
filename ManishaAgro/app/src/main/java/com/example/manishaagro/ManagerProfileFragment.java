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

import static com.example.manishaagro.utils.Constants.MANAGER_PROFILE;

public class ManagerProfileFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public ApiInterface apiInterface;
    private TextView nameText;
    private TextView dateOfBirth;
    private TextView dateOfJoining;
    private TextView designation;
    private TextView mobileNumber;
    private TextView address;
    private TextView employeeId;
    private TextView emailId;
    private TextView userEmailText;
    public String managerIdValue = "";
    private ProfileFragment.OnFragmentInteractionListener mListener;

    public ManagerProfileFragment() {
        // Required empty public constructor
    }

    public static ManagerProfileFragment newInstance(String param1, String param2) {
        ManagerProfileFragment fragment = new ManagerProfileFragment();
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
            getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.mgrprofile, container, false);
        TextView userTextView = view.findViewById(R.id.appusername);
        userEmailText = view.findViewById(R.id.useremails);
        nameText = view.findViewById(R.id.pfl_name);
        dateOfBirth = view.findViewById(R.id.pfl_dob);
        dateOfJoining = view.findViewById(R.id.pfl_doj);
        designation = view.findViewById(R.id.pfl_desig);
        employeeId = view.findViewById(R.id.pfl_empid);
        address = view.findViewById(R.id.pfl_addr);
        mobileNumber = view.findViewById(R.id.pfl_mobile);
        emailId = view.findViewById(R.id.pfl_email);

        ManagerActivity activity = (ManagerActivity) getActivity();
        if (activity != null) {
            Bundle results = activity.getMgrData();
            String value1 = results.getString("tempval2");
            managerIdValue = results.getString("tempManagerIDval2");
            userTextView.setText(value1);
        }
        getProfileData();
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ProfileFragment.OnFragmentInteractionListener) {
            mListener = (ProfileFragment.OnFragmentInteractionListener) context;
        }  //throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
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

    private void getProfileData() {
        final String username = managerIdValue;
        Log.v("yek", "rameshxxxxx" + username);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ProfileModel> callpro = apiInterface.getEmpProfile(MANAGER_PROFILE, username);
        callpro.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(@NonNull Call<ProfileModel> call, @NonNull Response<ProfileModel> response) {
                String value;
                if (response.body() != null) {
                    value = response.body().getValue();
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
                        userEmailText.setText(resemail);
                        Log.v("CodeIncome", "user2" + resname);
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    } else if (value.equals("0")) {
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProfileModel> call, @NonNull Throwable t) {
                Log.d("onFailure", t.toString());

                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}