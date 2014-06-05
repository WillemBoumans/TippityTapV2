package com.tippitytapv2.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Wietse on 6/5/2014.
 */
public class NewTipMapDialogFragment extends DialogFragment {

    public interface NewTipMapDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
    }

    NewTipMapDialogListener mListener;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        mListener = (NewTipMapDialogListener) activity;
    }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("You are making a new TipMap. Just tap along to the song")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mListener.onDialogPositiveClick(NewTipMapDialogFragment.this);
                                }
                            }
                    );
            return builder.create();
        }
    }