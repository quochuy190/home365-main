package neo.vn.test365children.Base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import neo.vn.test365children.Listener.ClickDialog;
import neo.vn.test365children.R;


public abstract class BaseActivity extends AppCompatActivity {

    protected AlertDialog.Builder builder;
    boolean layout = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        onPostSetContentView(savedInstanceState);
        setContentView(setContentViewId());
        ButterKnife.bind(this);
    }

    public void setLayout(boolean layout) {
        this.layout = layout;
    }

    protected void onPostSetContentView(Bundle savedInstanceState) {

    }

    public abstract int setContentViewId();

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void showAlertDialog(String title, String content) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, R.style.MyStyleDialogAlert);
        } else
            builder = new AlertDialog.Builder(this, R.style.MyStyleDialogAlert);

        builder.setTitle(title)
                .setCancelable(false)
                .setMessage(content)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                });
        TextView textView = (TextView)  dialog.findViewById(android.R.id.message);
        Typeface face=Typeface.createFromAsset(getAssets(),"utm_avo");
        textView.setTypeface(face);
        builder.show();
    }

    public void showAlertErrorNetwork() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, R.style.MyStyleDialogAlert);
        } else
            builder = new AlertDialog.Builder(this, R.style.MyStyleDialogAlert);

        builder.setTitle(getString(R.string.error_network))
                .setCancelable(false)
                .setMessage(getString(R.string.error_network_message))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .show();
    }

    protected ProgressDialog dialog;
    private Handler StopDialogLoadingHandler = new Handler();

    public void hideDialogLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    public void showDialogLoading() {
        StopDialogLoadingHandler.postDelayed(() -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }, 30000);
        if (!isFinishing()) {
            dialog = new ProgressDialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
           // dialog = new ProgressDialog(this);
            dialog.setCancelable(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage(getString(R.string.txt_loading_dialog));
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
        }
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public void showDialogLoadingtime(int time) {
        StopDialogLoadingHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, time);
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("Loading. Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
        }
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public void showDialogLoading(String message) {
        StopDialogLoadingHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, 35000);
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage(message);
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
        }
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public boolean isNetwork() {
        boolean is3g, isWifi;
        ConnectivityManager manager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        //For 3G check
        is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .isConnectedOrConnecting();
        //For WiFi Check
        isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnectedOrConnecting();
        if (!is3g && !isWifi) {
            showAlertErrorNetwork();
            return false;
        } else return true;
    }


    public void showDialogComfirm(String title, String message, boolean is_hide_cancel,
                                  final ClickDialog clickDialog) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, R.style.MyStyleDialogAlert);
        } else
            builder = new AlertDialog.Builder(this, R.style.MyStyleDialogAlert);
        if (is_hide_cancel) {
            builder.setTitle(title)
                    .setCancelable(false)
                    .setMessage(Html.fromHtml(message))
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            clickDialog.onClickYesDialog();
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            clickDialog.onClickNoDialog();
                        }
                    });
        } else {
            builder.setTitle(title)
                    .setCancelable(false)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            clickDialog.onClickYesDialog();
                        }
                    });
                    /*  .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {

                          }
                      })*/

        }
        AlertDialog dialog = builder.show();
        TextView textView = (TextView)  dialog.findViewById(android.R.id.message);
        Typeface face=Typeface.createFromAsset(getAssets(),"utm_avo.ttf");
        textView.setTypeface(face);
    }

    public void showDialogNotify(String title, String message) {
        /*final Dialog dialog_yes = new Dialog(this);
        dialog_yes.setCancelable(false);
        dialog_yes.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_yes.setContentView(R.layout.dialog_warning);
        dialog_yes.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView txt_title = (TextView) dialog_yes.findViewById(R.id.txt_warning_title);
        TextView txt_message = (TextView) dialog_yes.findViewById(R.id.txt_warning_message);
        TextView btn_ok = (TextView) dialog_yes.findViewById(R.id.btn_warning_ok);
        TextView btn_cancel = (TextView) dialog_yes.findViewById(R.id.btn_warning_cancel);
        View view_warning = (View) dialog_yes.findViewById(R.id.view_warning);

        txt_title.setText(title);
        txt_message.setText(message);
        view_warning.setVisibility(View.GONE);
        btn_cancel.setVisibility(View.GONE);
        // txt_buysongs.setText(Html.fromHtml("Để hoàn tất đăng ký dịch vụ RingTunes, Quý khách vui lòng thực hiện thao tác soạn tin nhắn <font color='#060606'>\"Y2 gửi 9194\"</font> từ số điện thoại giá cước: 3.000Đ/7 ngày. Cảm ơn Quý khách!"));
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_yes.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_yes.dismiss();

            }
        });
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        Window windowAlDl = dialog_yes.getWindow();

        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        windowAlDl.setAttributes(layoutParams);
        dialog_yes.show();*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, R.style.MyStyleDialogAlert);
        } else
            builder = new AlertDialog.Builder(this, R.style.MyStyleDialogAlert);
        builder.setTitle(title)
                .setCancelable(false)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = builder.show();
        TextView textView = (TextView)  dialog.findViewById(android.R.id.message);
        Typeface face=Typeface.createFromAsset(getAssets(),"utm_avo.ttf");
        textView.setTypeface(face);

    }

    public void showDialogComfirm_two_button(String title, String message, boolean is_hide_cancel,
                                             final ClickDialog clickDialog, String btn_yes, String btn_cancels) {
        final Dialog dialog_yes = new Dialog(this, R.style.MyStyleDialogAlert);
        dialog_yes.setCancelable(false);
        dialog_yes.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_yes.setContentView(R.layout.dialog_warning);
        dialog_yes.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView txt_title = (TextView) dialog_yes.findViewById(R.id.txt_warning_title);
        TextView txt_message = (TextView) dialog_yes.findViewById(R.id.txt_warning_message);
        TextView btn_ok = (TextView) dialog_yes.findViewById(R.id.btn_warning_ok);
        TextView btn_cancel = (TextView) dialog_yes.findViewById(R.id.btn_warning_cancel);
        View view_warning = (View) dialog_yes.findViewById(R.id.view_warning);
        txt_title.setText(title);
        txt_message.setText(message);
        btn_ok.setText(btn_yes);
        btn_cancel.setText(btn_cancels);
        // txt_buysongs.setText(Html.fromHtml("Để hoàn tất đăng ký dịch vụ RingTunes, Quý khách vui lòng thực hiện thao tác soạn tin nhắn <font color='#060606'>\"Y2 gửi 9194\"</font> từ số điện thoại giá cước: 3.000Đ/7 ngày. Cảm ơn Quý khách!"));
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDialog.onClickYesDialog();
                dialog_yes.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDialog.onClickNoDialog();
                dialog_yes.dismiss();
            }
        });
        view_warning.setVisibility(View.VISIBLE);
        btn_cancel.setVisibility(View.VISIBLE);
        dialog_yes.show();

    }
}