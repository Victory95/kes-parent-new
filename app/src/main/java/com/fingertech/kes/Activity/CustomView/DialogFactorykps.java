package com.fingertech.kes.Activity.CustomView;


import com.fingertech.kes.R;

public class DialogFactorykps {

    private DialogFactorykps() {
    }

    public static DialogKps makeSuccessDialog(String titleId,
                                                    String messageId,
                                                    String buttonTextId,
                                              DialogKps.ButtonDialogAction action) {
        return DialogKps.newInstance(titleId,
                messageId,
                buttonTextId,
                R.drawable.nomorkps,
                R.drawable.contoh_kps,
                R.color.colorPrimary,
                action);
    }

}
