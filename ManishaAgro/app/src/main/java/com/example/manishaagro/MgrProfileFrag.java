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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MgrProfileFrag extends Fragment{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    public ApiInterface apiInterface;


    private TextView userTextview,txtname,txtdob,txtdoj,txtdesig,txtmobile,txtaddr,txtempid,txtemail,useremltext;

    private ProfileFrag.OnFragmentInteractionListener mListener;

    public MgrProfileFrag() {
        // Required empty public constructor
    }


    public static MgrProfileFrag newInstance(String param1, String param2) {
        MgrProfileFrag fragment = new MgrProfileFrag();
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
            mParam1 = getArguments().getString("params");
            Log.v("yek", "keyyy" + mParam1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.mgrprofile, container, false);
        userTextview=view.findViewById(R.id.appusername);

        useremltext=view.findViewById(R.id.useremails);
        txtname=(TextView)view.findViewById(R.id.pfl_name);
        txtdob=view.findViewById(R.id.pfl_dob);
        txtdoj=view.findViewById(R.id.pfl_doj);
        txtdesig=view.findViewById(R.id.pfl_desig);
        txtempid=(TextView)view.findViewById(R.id.pfl_empid);
        txtaddr=view.findViewById(R.id.pfl_addr);
        txtmobile=view.findViewById(R.id.pfl_mobile);
        txtemail=view.findViewById(R.id.pfl_email);



        MngrActivity activity = (MngrActivity) getActivity();

        Bundle results = activity.getMgrData();
        String value1 = results.getString("tempval2");

        userTextview.setText(value1);

        getProfileData("EmpProfile");

        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ProfileFrag.OnFragmentInteractionListener) {
            mListener = (ProfileFrag.OnFragmentInteractionListener) context;
        } else {
            //     throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener=null;
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    private void getProfileData(final String key)
    {
        final String usrname=userTextview.getText().toString().trim();

        Log.v("yek", "rameshxxxxx" + usrname);


        apiInterface=ApiClient.getApiClient().create(ApiInterface.class);
        Call<ProfileModel> callpro=apiInterface.getEmpProfile(key,usrname);
        callpro.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                String value=response.body().getValue();
                String message=response.body().getMassage();
                String resname=response.body().getName();
                String resempid=response.body().getEmpid();
                String resaddr=response.body().getContactdtl();
                String resmobile=response.body().getMobile();
                String resdesig=response.body().getDesignation();
                String resdob=response.body().getDob();
                String resdoj=response.body().getJoiningdate();
                String resemail=response.body().getEmail();
                Log.v("CodeIncome", "user1" + resname);
                if (value.equals("1"))
                {   txtname.setText(resname);
                    txtempid.setText(resempid);
                    txtaddr.setText(resaddr);
                    txtmobile.setText(resmobile);
                    txtdesig.setText(resdesig);
                    txtemail.setText(resemail);
                    txtdob.setText(resdob);
                    txtdoj.setText(resdoj);
                    useremltext.setText(resemail);
                    Log.v("CodeIncome", "user2" +resname);
                    Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
                }
                else if(value.equals("0"))
                {
                    Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
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
