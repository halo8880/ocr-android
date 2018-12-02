package com.example.admin.nav.main_menu;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.example.admin.nav.utils.HintDialog;
import com.example.admin.nav.R;

public class ReleaseNoteDialog extends DialogFragment {

	public final static String TAG = ReleaseNoteDialog.class.getSimpleName();

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return HintDialog.createDialog(getActivity(), R.string.nav_whats_new, R.raw.release_notes);
	}

}
