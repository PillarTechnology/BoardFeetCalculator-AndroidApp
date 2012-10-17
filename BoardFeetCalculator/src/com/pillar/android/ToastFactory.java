package com.pillar.android;

import android.content.Context;
import android.widget.Toast;

public class ToastFactory {

	public Toast createToast(Context context, CharSequence text, int duration){
		return Toast.makeText(context, text, duration);
	}
}
