package com.fingertech.kes.Activity.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fingertech.kes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {


    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle!=null) {
            notelepon = getArguments().getString("notelepon");
            email = getArguments().getString("email");
            nofax = getArguments().getString("nofax");
            Website = getArguments().getString("website");
            Cp = getArguments().getString("cp");
        }
    }

    TextView Notelepon,Email,NoFax,website,cp;
    String notelepon,email,nofax,Website,Cp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view   = inflater.inflate(R.layout.fragment_contact, container, false);
        Notelepon   = (TextView)view.findViewById(R.id.notelepon);
        Email       = (TextView)view.findViewById(R.id.email_sekolah);
        NoFax       = (TextView)view.findViewById(R.id.nomor_fax);
        website     = (TextView)view.findViewById(R.id.website);
        cp          = (TextView)view.findViewById(R.id.cp);

        Notelepon.setText(notelepon);
        Email.setText(email);
        NoFax.setText(nofax);
        website.setText(Website);
        cp.setText(Cp);
        return view;
    }

}
