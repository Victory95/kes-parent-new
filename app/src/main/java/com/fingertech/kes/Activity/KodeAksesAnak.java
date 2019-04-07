package com.fingertech.kes.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fingertech.kes.Activity.CustomView.DialogFactory;
import com.fingertech.kes.Activity.CustomView.OneButtonDialog;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

public class KodeAksesAnak extends AppCompatActivity implements TextWatcher {
    private EditText editTextone,editTexttwo,editTextthree,editTextfour,editTextfive,editTextsix;
    private Button btn_submit;
    private ImageView iv_copy_paste,iv_close,iv_foto_profile;
    private TextView tv_val_kode_aa,tv_kode_akses_anak_sekolah,tv_kode_akses_anak_nama,mintakode;
    private ProgressDialog dialog;
    String verification_code,parent_id,student_id,student_nik,school_id,parent_nik,childrenname,school_name,email,fullname,member_id,school_code;
    Integer status;
    String code;

    Auth mApiInterface;

    SharedPreferences sharedpreferences;

    public static final String TAG_EMAIL        = "email";
    public static final String TAG_FULLNAME     = "fullname";
    public static final String TAG_TOKEN        = "token";
    public static final String TAG_MEMBER_ID    = "member_id"; /// PARENT ID
    public static final String TAG_STUDENT_ID   = "student_id";
    public static final String TAG_STUDENT_NIK  = "student_nik";
    public static final String TAG_SCHOOL_ID    = "school_id";
    public static final String TAG_NAMA_ANAK    = "childrenname";
    public static final String TAG_NAMA_SEKOLAH = "school_name";
    public static final String TAG_SCHOOL_CODE  = "school_code";
    public static final String TAG_COUNT        = "count_children";
    public static final String TAG_PARENT_NIK   = "parent_nik";
    public static final String TAG_PICTURE      = "picture";

    String authorization,count_student,picture,Base_anak;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kode_akses_anak);

        editTextone     = findViewById(R.id.editTextone);
        editTexttwo     = findViewById(R.id.editTexttwo);
        editTextthree   = findViewById(R.id.editTextthree);
        editTextfour    = findViewById(R.id.editTextfour);
        editTextfive    = findViewById(R.id.editTextfive);
        editTextsix     = findViewById(R.id.editTextsix);
        btn_submit      = findViewById(R.id.btn_submit);
        iv_copy_paste   = findViewById(R.id.iv_copy_paste);
        iv_close        = findViewById(R.id.iv_close);
        iv_foto_profile = findViewById(R.id.iv_foto_profile);
        tv_val_kode_aa  = findViewById(R.id.tv_val_kode_aa);
        mintakode       = findViewById(R.id.kirim_kode);
        tv_kode_akses_anak_sekolah  = findViewById(R.id.tv_kode_akses_anak_sekolah);
        tv_kode_akses_anak_nama     = findViewById(R.id.tv_kode_akses_anak_nama);
        Base_anak       = "http://www.kes.co.id/schoolc/assets/images/profile/mm_";

        editTextone.addTextChangedListener(this);
        editTexttwo.addTextChangedListener( this);
        editTextthree.addTextChangedListener(this);
        editTextfour.addTextChangedListener(this);
        editTextfive.addTextChangedListener(this);
        editTextsix.addTextChangedListener(this);

        editTextone.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangle_line2));
        editTextone.setTextColor(getResources().getColor(R.color.colorPrimary));

        mApiInterface = ApiClient.getClient().create(Auth.class);

        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization = sharedpreferences.getString(TAG_TOKEN,"token");
        parent_id     = sharedpreferences.getString(TAG_MEMBER_ID,"member_id");
        student_id    = sharedpreferences.getString(TAG_STUDENT_ID,"student_id");
        student_nik   = sharedpreferences.getString(TAG_STUDENT_NIK,"student_nik");
        school_id     = sharedpreferences.getString(TAG_SCHOOL_ID,"school_id");
        fullname      = sharedpreferences.getString(TAG_FULLNAME,"fullname");
        email         = sharedpreferences.getString(TAG_EMAIL,"email");
        childrenname  = sharedpreferences.getString(TAG_NAMA_ANAK,"childrenname");
        school_name   = sharedpreferences.getString(TAG_NAMA_SEKOLAH,"school_name");
        school_code   = sharedpreferences.getString(TAG_SCHOOL_CODE,"school_code");
        count_student = sharedpreferences.getString(TAG_COUNT,"");
        parent_nik    = sharedpreferences.getString(TAG_PARENT_NIK,"parent_nik");
        picture       = sharedpreferences.getString(TAG_PICTURE,"");
        iv_close.setOnClickListener(v -> finish());
        String imagefiles = Base_anak + picture;

        if (picture.equals("")){
            Glide.with(KodeAksesAnak.this).load("https://ui-avatars.com/api/?name="+childrenname+"&background=40bfe8&color=fff").into(iv_foto_profile);
        }
        Glide.with(KodeAksesAnak.this).load(imagefiles).into(iv_foto_profile);

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

        tv_kode_akses_anak_nama.setText(childrenname);
        tv_kode_akses_anak_sekolah.setText(school_name);

        editTextone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                editTextone.onTouchEvent(event);
                editTextone.setSelection(editTextone.getText().length());
                editTextone.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangle_line2));
                editTextone.setTextColor(getResources().getColor(R.color.colorPrimary));

                editTexttwo.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
                editTexttwo.setTextColor(Color.WHITE);
                editTextthree.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
                editTextthree.setTextColor(Color.WHITE);
                editTextfour.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
                editTextfour.setTextColor(Color.WHITE);
                editTextfive.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
                editTextfive.setTextColor(Color.WHITE);
                editTextsix.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
                editTextsix.setTextColor(Color.WHITE);
                return true;
            }
        });
        editTexttwo.setOnTouchListener((v, event) -> {
            editTexttwo.onTouchEvent(event);
            editTexttwo.setSelection(editTexttwo.getText().length());
            editTexttwo.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangle_line2));
            editTexttwo.setTextColor(getResources().getColor(R.color.colorPrimary));

            editTextone.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
            editTextone.setTextColor(Color.WHITE);
            editTextthree.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
            editTextthree.setTextColor(Color.WHITE);
            editTextfour.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
            editTextfour.setTextColor(Color.WHITE);
            editTextfive.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
            editTextfive.setTextColor(Color.WHITE);
            editTextsix.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
            editTextsix.setTextColor(Color.WHITE);
            return true;
        });
        editTextthree.setOnTouchListener((v, event) -> {
            editTextthree.onTouchEvent(event);
            editTextthree.setSelection(editTextthree.getText().length());
            editTextthree.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangle_line2));
            editTextthree.setTextColor(getResources().getColor(R.color.colorPrimary));

            editTextone.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
            editTextone.setTextColor(Color.WHITE);
            editTexttwo.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
            editTexttwo.setTextColor(Color.WHITE);
            editTextfour.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
            editTextfour.setTextColor(Color.WHITE);
            editTextfive.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
            editTextfive.setTextColor(Color.WHITE);
            editTextsix.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
            editTextsix.setTextColor(Color.WHITE);
            return true;
        });
        editTextfour.setOnTouchListener((v, event) -> {
            editTextfour.onTouchEvent(event);
            editTextfour.setSelection(editTextfour.getText().length());
            editTextfour.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangle_line2));
            editTextfour.setTextColor(getResources().getColor(R.color.colorPrimary));

            editTextone.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
            editTextone.setTextColor(Color.WHITE);
            editTexttwo.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
            editTexttwo.setTextColor(Color.WHITE);
            editTextthree.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
            editTextthree.setTextColor(Color.WHITE);
            editTextfive.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
            editTextfive.setTextColor(Color.WHITE);
            editTextsix.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
            editTextsix.setTextColor(Color.WHITE);
            return true;
        });
        editTextfive.setOnTouchListener((v, event) -> {
            editTextfive.onTouchEvent(event);
            editTextfive.setSelection(editTextfive.getText().length());
            editTextfive.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangle_line2));
            editTextfive.setTextColor(getResources().getColor(R.color.colorPrimary));

            editTextone.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
            editTextone.setTextColor(Color.WHITE);
            editTexttwo.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
            editTexttwo.setTextColor(Color.WHITE);
            editTextthree.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
            editTextthree.setTextColor(Color.WHITE);
            editTextfour.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
            editTextfour.setTextColor(Color.WHITE);
            editTextsix.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
            editTextsix.setTextColor(Color.WHITE);
            return true;
        });
        editTextsix.setOnTouchListener((v, event) -> {
            editTextsix.onTouchEvent(event);
            editTextsix.setSelection(editTextsix.getText().length());
            editTextsix.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangle_line2));
            editTextsix.setTextColor(getResources().getColor(R.color.colorPrimary));

            editTextone.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
            editTextone.setTextColor(Color.WHITE);
            editTexttwo.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
            editTexttwo.setTextColor(Color.WHITE);
            editTextthree.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
            editTextthree.setTextColor(Color.WHITE);
            editTextfour.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
            editTextfour.setTextColor(Color.WHITE);
            editTextfive.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
            editTextfive.setTextColor(Color.WHITE);
            return true;
        });

        iv_copy_paste.setOnClickListener(v -> {
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
        });

        mintakode.setOnClickListener(v -> {
            request_code_acsess_post();
            editTextone.getText().clear();
            editTexttwo.getText().clear();
            editTextthree.getText().clear();
            editTextfour.getText().clear();
            editTextfive.getText().clear();
            editTextsix.getText().clear();
            editTextone.addTextChangedListener(KodeAksesAnak.this);
            editTexttwo.addTextChangedListener( KodeAksesAnak.this);
            editTextthree.addTextChangedListener(KodeAksesAnak.this);
            editTextfour.addTextChangedListener(KodeAksesAnak.this);
            editTextfive.addTextChangedListener(KodeAksesAnak.this);
            editTextsix.addTextChangedListener(KodeAksesAnak.this);
        });
        btn_submit.setOnClickListener(v -> {
            if(editTextone.length()==0 && editTexttwo.length()==0 && editTextthree.length()==0 && editTextfour.length()==0 && editTextfive.length()==0 && editTextsix.length()==0){
                tv_val_kode_aa.setVisibility(View.VISIBLE);
            }else {
                tv_val_kode_aa.setVisibility(View.GONE);
                masuk_code_acsess_post();

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
                editTextone.setSelection(editTextone.getText().length());
                editTexttwo.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangle_line2));
                editTexttwo.setTextColor(getResources().getColor(R.color.colorPrimary));

                editTextone.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
                editTextone.setTextColor(Color.WHITE);
                editTexttwo.requestFocus();
            }
            if (editTexttwo.length() == 1) {
                editTexttwo.setSelection(editTexttwo.getText().length());
                editTextthree.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangle_line2));
                editTextthree.setTextColor(getResources().getColor(R.color.colorPrimary));

                editTexttwo.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
                editTexttwo.setTextColor(Color.WHITE);
                editTextthree.requestFocus();
            }
            if (editTextthree.length() == 1) {
                editTextthree.setSelection(editTextthree.getText().length());
                editTextfour.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangle_line2));
                editTextfour.setTextColor(getResources().getColor(R.color.colorPrimary));

                editTextthree.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
                editTextthree.setTextColor(Color.WHITE);
                editTextfour.requestFocus();
            }
            if (editTextfour.length() == 1) {
                editTextfour.setSelection(editTextfour.getText().length());
                editTextfive.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangle_line2));
                editTextfive.setTextColor(getResources().getColor(R.color.colorPrimary));

                editTextfour.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
                editTextfour.setTextColor(Color.WHITE);
                editTextfive.requestFocus();
            }
            if (editTextfive.length() == 1) {
                editTextfive.setSelection(editTextfive.getText().length());
                editTextsix.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangle_line2));
                editTextsix.setTextColor(getResources().getColor(R.color.colorPrimary));

                editTextfive.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
                editTextfive.setTextColor(Color.WHITE);
                editTextsix.requestFocus();
            }
            if (editTextsix.length() == 1) {
                editTextsix.setSelection(editTextsix.getText().length());

                editTextsix.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
                editTextsix.setTextColor(Color.WHITE);
                hideKeyboard(KodeAksesAnak.this);
            }
        }
        editTexttwo.setOnKeyListener((v, keyCode, event) -> {
            InputFilter filter = new InputFilter() {
                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                    if (end == 0 || dstart < dend && editTextone.length() == 0 && editTextone.length() == 0) {
                        editTextone.requestFocus();
                        editTextone.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangle_line2));
                        editTextone.setTextColor(getResources().getColor(R.color.colorPrimary));

                        editTexttwo.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
                        editTexttwo.setTextColor(Color.WHITE);
                    }
                    return source;
                }
            };
            editTexttwo.setFilters(new InputFilter[]{filter,new InputFilter.LengthFilter(1)});
            return false;
        });
        editTextthree.setOnKeyListener((v, keyCode, event) -> {
            InputFilter filter = new InputFilter() {
                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                    if (end == 0 || dstart < dend && editTexttwo.length() == 0) {
                        editTexttwo.requestFocus();
                        editTexttwo.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangle_line2));
                        editTexttwo.setTextColor(getResources().getColor(R.color.colorPrimary));

                        editTextthree.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
                        editTextthree.setTextColor(Color.WHITE);
                    }
                    return source;
                }
            };
            editTextthree.setFilters(new InputFilter[]{filter,new InputFilter.LengthFilter(1)});
            return false;
        });
        editTextfour.setOnKeyListener((v, keyCode, event) -> {
            InputFilter filter = new InputFilter() {
                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                    if (end == 0 || dstart < dend && editTextthree.length() == 0) {
                        editTextthree.requestFocus();
                        editTextthree.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangle_line2));
                        editTextthree.setTextColor(getResources().getColor(R.color.colorPrimary));

                        editTextfour.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
                        editTextfour.setTextColor(Color.WHITE);
                    }
                    return source;
                }
            };
            editTextfour.setFilters(new InputFilter[]{filter,new InputFilter.LengthFilter(1)});
            return false;
        });
        editTextfive.setOnKeyListener((v, keyCode, event) -> {
            InputFilter filter = new InputFilter() {
                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                    if (end == 0 || dstart < dend && editTextfour.length() == 0) {
                        editTextfour.requestFocus();
                        editTextfour.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangle_line2));
                        editTextfour.setTextColor(getResources().getColor(R.color.colorPrimary));

                        editTextfive.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
                        editTextfive.setTextColor(Color.WHITE);
                    }
                    return source;
                }
            };
            editTextfive.setFilters(new InputFilter[]{filter,new InputFilter.LengthFilter(1)});
            return false;
        });
        editTextsix.setOnKeyListener((v, keyCode, event) -> {
            InputFilter filter = new InputFilter() {
                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                    if (end == 0 || dstart < dend && editTextfive.length() == 0) {
                        editTextfive.requestFocus();
                        editTextfive.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangle_line2));
                        editTextfive.setTextColor(getResources().getColor(R.color.colorPrimary));

                        editTextsix.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangel_white));
                        editTextsix.setTextColor(Color.WHITE);
                    }
                    return source;
                }
            };
            editTextsix.setFilters(new InputFilter[]{filter,new InputFilter.LengthFilter(1)});
            return false;
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
        verification_code = editTextone.getText().toString()+editTexttwo.getText().toString()+editTextthree.getText().toString()+editTextfour.getText().toString()+editTextfive.getText().toString()+editTextsix.getText().toString();
        progressBar();
        showDialog();
        Call<JSONResponse.Masuk_code_acsess> postCall = mApiInterface.masuk_code_acsess_post(authorization.toString(), verification_code.toString(), parent_id.toString(), student_id.toString(), student_nik.toString(), school_id.toString());
        postCall.enqueue(new Callback<JSONResponse.Masuk_code_acsess>() {
            @Override
            public void onResponse(Call<JSONResponse.Masuk_code_acsess> call, Response<JSONResponse.Masuk_code_acsess> response) {
                hideDialog();
                Log.i("TAG_KODE_AKSES_ANAK",response.code()+"");

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
                    pilihan();
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

    public void request_code_acsess_post(){
        progressBar();
        showDialog();
        Call<JSONResponse> postCall = mApiInterface.request_code_acsess_post(authorization.toString(), email.toString(), fullname.toString(), parent_id.toString(), student_id.toString(), school_id.toString());
        postCall.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                hideDialog();
                Log.d("TAG",response.code()+"");

                JSONResponse resource = response.body();
                status = resource.status;
                code = resource.code;

                String RCA_SCS_0001 = getResources().getString(R.string.RCA_SCS_0001);
                String RCA_ERR_0001 = getResources().getString(R.string.RCA_ERR_0001);
                String RCA_ERR_0002 = getResources().getString(R.string.RCA_ERR_0002);
                String RCA_ERR_0003 = getResources().getString(R.string.RCA_ERR_0003);
                String RCA_ERR_0004 = getResources().getString(R.string.RCA_ERR_0004);
                String RCA_ERR_0005 = getResources().getString(R.string.RCA_ERR_0005);
                String RCA_ERR_0006 = getResources().getString(R.string.RCA_ERR_0006);
                String RCA_ERR_0007 = getResources().getString(R.string.RCA_ERR_0007);
                String RCA_ERR_0008 = getResources().getString(R.string.RCA_ERR_0008);

                if (status == 1 && code.equals("RCA_SCS_0001")) {
                    Toast.makeText(getApplicationContext(), RCA_SCS_0001, Toast.LENGTH_LONG).show();
                } else {
                    if(status == 0 && code.equals("RCA_ERR_0001")){
                        Toast.makeText(getApplicationContext(), RCA_ERR_0001, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("RCA_ERR_0002")){
                        Toast.makeText(getApplicationContext(), RCA_ERR_0002, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("RCA_ERR_0003")){
                        Toast.makeText(getApplicationContext(), RCA_ERR_0003, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("RCA_ERR_0004")){
                        Toast.makeText(getApplicationContext(), RCA_ERR_0004, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("RCA_ERR_0005")){
                        Toast.makeText(getApplicationContext(), RCA_ERR_0005, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("RCA_ERR_0006")){
                        Toast.makeText(getApplicationContext(), RCA_ERR_0006, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("RCA_ERR_0007")){
                        Toast.makeText(getApplicationContext(), RCA_ERR_0007, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("RCA_ERR_0008")){
                        Toast.makeText(getApplicationContext(), RCA_ERR_0008, Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                hideDialog();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_resp_json), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void delete_code(){
        verification_code = editTextone.getText().toString()+editTexttwo.getText().toString()+editTextthree.getText().toString()+editTextfour.getText().toString()+editTextfive.getText().toString()+editTextsix.getText().toString();
        Call<JSONResponse.DeleteCode> postCall = mApiInterface.delete_verification_post(verification_code.toString());
        postCall.enqueue(new Callback<JSONResponse.DeleteCode>() {
            @Override
            public void onResponse(Call<JSONResponse.DeleteCode> call, Response<JSONResponse.DeleteCode> response) {

                Log.d("TAG",response.code()+"");

                JSONResponse.DeleteCode resource = response.body();
                status = resource.status;
                if (status == 1){
                    Log.d("TAG",response.message()+"");
//                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<JSONResponse.DeleteCode> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_resp_json), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void pilihan() {

        OneButtonDialog oneButtonDialog =
                DialogFactory.makeSuccessDialog("Selamat! \n Anda telah berhasil mengakses anak anda yang bernama '"+childrenname+" ' yang bersekolah di '"+school_name,
                        "Demi kelancaran akses dalam memantau perkembangan pendidikan anak anda melalui KES, silahkan isi dengan sebaik-baiknya form berikut ini.",
                        "Ok",
                        new OneButtonDialog.ButtonDialogAction() {
                            @Override
                            public void onButtonClicked() {
                                Intent intent = new Intent(getApplicationContext(), ParentMain.class);
                                delete_code();
                                startActivity(intent);                            }
                        });
        oneButtonDialog.show(getSupportFragmentManager(), OneButtonDialog.TAG);
    }
}
