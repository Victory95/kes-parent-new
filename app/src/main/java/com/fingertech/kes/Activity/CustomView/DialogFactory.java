package com.fingertech.kes.Activity.CustomView;

import com.fingertech.kes.R;

public class DialogFactory {

    private DialogFactory() {
    }

    public static OneButtonDialog makeSuccessDialog(String titleId,
                                                    String messageId,
                                                    String buttonTextId,
                                                    OneButtonDialog.ButtonDialogAction action) {
        return OneButtonDialog.newInstance(titleId,
                messageId,
                buttonTextId,
                R.drawable.ic_true_white,
                R.color.colorPrimary,
                action);
    }

}