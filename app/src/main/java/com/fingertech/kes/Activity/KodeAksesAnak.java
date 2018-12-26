package com.fingertech.kes.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;

import java.util.LinkedList;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

public class KodeAksesAnak extends AppCompatActivity implements TextWatcher {
    private EditText editTextone,editTexttwo,editTextthree,editTextfour,editTextfive,editTextsix;
    private Button btn_submit;
    private ImageView iv_copy_paste;
    private TextView tv_val_kode_aa;
    private ProgressDialog dialog;
    String verification_code,parent_id,student_id,student_nik,school_id;
    Integer status;
    String code;

    Auth mApiInterface;

    SharedPreferences sharedpreferences;
    public static final String TAG_TOKEN        = "token";
    public static final String TAG_MEMBER_ID    = "email"; /// PARENT ID
    public static final String TAG_STUDENT_ID   = "member_id";
    public static final String TAG_STUDENT_NIK  = "fullname";
    public static final String TAG_SCHOOL_ID    = "member_type";
    String authorization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kode_akses_anak);

        editTextone     =(EditText)findViewById(R.id.editTextone);
        editTexttwo     =(EditText)findViewById(R.id.editTexttwo);
        editTextthree   =(EditText)findViewById(R.id.editTextthree);
        editTextfour    =(EditText)findViewById(R.id.editTextfour);
        editTextfive    =(EditText)findViewById(R.id.editTextfive);
        editTextsix     =(EditText)findViewById(R.id.editTextsix);
        btn_submit      =(Button)findViewById(R.id.btn_submit);
        iv_copy_paste   =(ImageView)findViewById(R.id.iv_copy_paste);
        tv_val_kode_aa  =(TextView)findViewById(R.id.tv_val_kode_aa);

        editTextone.addTextChangedListener(this);
        editTexttwo.addTextChangedListener( this);
        editTextthree.addTextChangedListener(this);
        editTextfour.addTextChangedListener(this);
        editTextfive.addTextChangedListener(this);
        editTextsix.addTextChangedListener(this);

        mApiInterface = ApiClient.getClient().create(Auth.class);

        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization = sharedpreferences.getString(TAG_TOKEN,"token");
//        parent_id     = sharedpreferences.getString(TAG_MEMBER_ID,"member_id");
//        student_id    = sharedpreferences.getString(TAG_STUDENT_ID,"student_id");
//        student_nik   = sharedpreferences.getString(TAG_STUDENT_NIK,"student_nik");
//        school_id     = sharedpreferences.getString(TAG_SCHOOL_ID,"school_id");

        editTextone.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }
            public void onDestroyActionMode(ActionMode mode) {
            }
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });
        editTexttwo.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }
            public void onDestroyActionMode(ActionMode mode) {
            }
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });
        editTextthree.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }
            public void onDestroyActionMode(ActionMode mode) {
            }
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });
        editTextfour.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }
            public void onDestroyActionMode(ActionMode mode) {
            }
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });
        editTextfive.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }
            public void onDestroyActionMode(ActionMode mode) {
            }
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });
        editTextsix.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }
            public void onDestroyActionMode(ActionMode mode) {
            }
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });
        iv_copy_paste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                String pasteData = "";
                if (!(clipboard.hasPrimaryClip())) {
                } else if (!(clipboard.getPrimaryClipDescription().hasMimeType(MIMETYPE_TEXT_PLAIN))) {
                } else {
                    ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                    pasteData = item.getText().toString();
                }
                String val=pasteData;
                String[] arr=String.valueOf(val).split("(?<=.)");
                int[] intArr=new int[6];
                intArr[0]=Integer.parseInt(arr[0]);
                intArr[1]=Integer.parseInt(arr[1]);
                intArr[2]=Integer.parseInt(arr[2]);
                intArr[3]=Integer.parseInt(arr[3]);
                intArr[4]=Integer.parseInt(arr[4]);
                intArr[5]=Integer.parseInt(arr[5]);
                editTextone.setText(String.valueOf(intArr[0]));
                editTexttwo.setText(String.valueOf(intArr[1]));
                editTextthree.setText(String.valueOf(intArr[2]));
                editTextfour.setText(String.valueOf(intArr[3]));
                editTextfive.setText(String.valueOf(intArr[4]));
                editTextsix.setText(String.valueOf(intArr[5]));
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextone.length()==0 && editTexttwo.length()==0 && editTextthree.length()==0 && editTextfour.length()==0 && editTextfive.length()==0 && editTextsix.length()==0){
                    tv_val_kode_aa.setVisibility(View.VISIBLE);
                }else {
                    tv_val_kode_aa.setVisibility(View.GONE);
                   masuk_code_acsess_post();
                }
            }
        });

    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length() == 1) {
            if (editTextone.length() == 1) {
                editTexttwo.requestFocus();
            }
            if (editTexttwo.length() == 1) {
                editTextthree.requestFocus();
            }
            if (editTextthree.length() == 1) {
                editTextfour.requestFocus();
            }
            if (editTextfour.length() == 1) {
                editTextfive.requestFocus();
            }
            if (editTextfive.length() == 1) {
                editTextsix.requestFocus();
            }
            if (editTextsix.length() == 1) {
                hideKeyboard(KodeAksesAnak.this);
            }
        }
        editTexttwo.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                InputFilter filter = new InputFilter() {
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        if (end == 0 || dstart < dend && editTextone.length() == 0 && editTextone.length() == 0) {
                            editTextone.requestFocus();
                        }
                        return source;
                    }
                };
                editTexttwo.setFilters(new InputFilter[]{filter,new InputFilter.LengthFilter(1)});
                return false;
            }
        });
        editTextthree.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                InputFilter filter = new InputFilter() {
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        if (end == 0 || dstart < dend && editTexttwo.length() == 0) {
                            editTexttwo.requestFocus();
                        }
                        return source;
                    }
                };
                editTextthree.setFilters(new InputFilter[]{filter,new InputFilter.LengthFilter(1)});
                return false;
            }
        });
        editTextfour.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                InputFilter filter = new InputFilter() {
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        if (end == 0 || dstart < dend && editTextthree.length() == 0) {
                            editTextthree.requestFocus();
                        }
                        return source;
                    }
                };
                editTextfour.setFilters(new InputFilter[]{filter,new InputFilter.LengthFilter(1)});
                return false;
            }
        });
        editTextfive.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                InputFilter filter = new InputFilter() {
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        if (end == 0 || dstart < dend && editTextfour.length() == 0) {
                            editTextfour.requestFocus();
                        }
                        return source;
                    }
                };
                editTextfive.setFilters(new InputFilter[]{filter,new InputFilter.LengthFilter(1)});
                return false;
            }
        });
        editTextsix.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                InputFilter filter = new InputFilter() {
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        if (end == 0 || dstart < dend && editTextfive.length() == 0) {
                            editTextfive.requestFocus();
                        }
                        return source;
                    }
                };
                editTextsix.setFilters(new InputFilter[]{filter,new InputFilter.LengthFilter(1)});
                return false;
            }
        });
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void showDialog() {
        if (!dialog.isShowing())
            dialog.show();
        dialog.setContentView(R.layout.progressbar);
    }
    private void hideDialog() {
        if (dialog.isShowing())
            dialog.dismiss();
        dialog.setContentView(R.layout.progressbar);
    }
    public void progressBar(){
        dialog = new ProgressDialog(KodeAksesAnak.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }

    public void masuk_code_acsess_post(){
        verification_code = "139478";
        parent_id   = "232";
        student_id  = "415";
        student_nik = "65671298521";
        school_id   = "1";
        progressBar();
        showDialog();
        Call<JSONResponse.Masuk_code_acsess> postCall = mApiInterface.masuk_code_acsess_post(authorization.toString(), verification_code.toString(), parent_id.toString(), student_id.toString(), student_nik.toString(), school_id.toString());
        postCall.enqueue(new Callback<JSONResponse.Masuk_code_acsess>() {
            @Override
            public void onResponse(Call<JSONResponse.Masuk_code_acsess> call, Response<JSONResponse.Masuk_code_acsess> response) {
                hideDialog();
                Log.d("TAG",response.code()+"");

                JSONResponse.Masuk_code_acsess resource = response.body();
                status = resource.status;
                code = resource.code;

                String MCA_SCS_0001 = getResources().getString(R.string.MCA_SCS_0001);
                String MCA_ERR_0001 = getResources().getString(R.string.MCA_ERR_0001);
                String MCA_ERR_0002 = getResources().getString(R.string.MCA_ERR_0002);
                String MCA_ERR_0003 = getResources().getString(R.string.MCA_ERR_0003);
                String MCA_ERR_0004 = getResources().getString(R.string.MCA_ERR_0004);
                String MCA_ERR_0005 = getResources().getString(R.string.MCA_ERR_0005);
                String MCA_ERR_0006 = getResources().getString(R.string.MCA_ERR_0006);
                String MCA_ERR_0007 = getResources().getString(R.string.MCA_ERR_0007);
                String MCA_ERR_0008 = getResources().getString(R.string.MCA_ERR_0008);

                if (status == 1 && code.equals("MCA_SCS_0001")) {
                    Toast.makeText(getApplicationContext(), MCA_SCS_0001, Toast.LENGTH_LONG).show();
                } else {
                    if(status == 0 && code.equals("MCA_ERR_0001")){
                        Toast.makeText(getApplicationContext(), MCA_ERR_0001, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("MCA_ERR_0002")){
                        Toast.makeText(getApplicationContext(), MCA_ERR_0002, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("MCA_ERR_0003")){
                        Toast.makeText(getApplicationContext(), MCA_ERR_0003, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("MCA_ERR_0004")){
                        Toast.makeText(getApplicationContext(), MCA_ERR_0004, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("MCA_ERR_0005")){
                        Toast.makeText(getApplicationContext(), MCA_ERR_0005, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("MCA_ERR_0006")){
                        Toast.makeText(getApplicationContext(), MCA_ERR_0006, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("MCA_ERR_0007")){
                        Toast.makeText(getApplicationContext(), MCA_ERR_0007, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("MCA_ERR_0008")){
                        Toast.makeText(getApplicationContext(), MCA_ERR_0008, Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<JSONResponse.Masuk_code_acsess> call, Throwable t) {
                hideDialog();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_resp_json), Toast.LENGTH_LONG).show();
            }
        });
    }

}
