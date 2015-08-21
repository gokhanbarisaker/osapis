package com.gokhanbarisaker.osapissample.activity;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gokhanbarisaker.osapis.utility.DialogFragmentTransactionHelper;
import com.gokhanbarisaker.osapissample.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class DialogFragmentActivity extends AppCompatActivity {
    private static final String TAG_FRAGMENT = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_fragment);

        findViewById(R.id.dialogfragment_showbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                DialogFragmentTransactionHelper.getInstance().show(new DialogFragment(), transaction, TAG_FRAGMENT);
            }
        });

        findViewById(R.id.dialogfragment_showsingletonbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.timer(0, 2, TimeUnit.SECONDS).take(3).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        FragmentManager manager = getSupportFragmentManager();
                        DialogFragmentTransactionHelper.getInstance().showSingleton(new DialogFragment(), manager, manager.beginTransaction(), TAG_FRAGMENT);
                    }
                });
            }
        });

        findViewById(R.id.dialogfragment_bubblebutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogFragment dialogFragment = new DialogFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                DialogFragmentTransactionHelper.getInstance().show(dialogFragment, transaction, TAG_FRAGMENT);

                Observable.timer(2, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        DialogFragmentTransactionHelper.getInstance().dismiss(dialogFragment);
                    }
                });
            }
        });
    }
}
