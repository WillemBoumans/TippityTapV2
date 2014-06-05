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
public class TipMapDialogFragment extends DialogFragment {

    public interface TipMapDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
    }

    TipMapDialogListener mListener;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        mListener = (TipMapDialogListener) activity;
    }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("The game will start now. Tap along to the song.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mListener.onDialogPositiveClick(TipMapDialogFragment.this);
                                }
                            }
                    );
            return builder.create();
        }
    }