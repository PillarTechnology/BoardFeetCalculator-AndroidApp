package com.pillar.android;

import android.content.Context;
import android.content.Intent;

public class IntentFactory {

	public Intent createIntent(Context packageContext, Class<?> cls) {
		return new Intent(packageContext, cls);
	}
}
