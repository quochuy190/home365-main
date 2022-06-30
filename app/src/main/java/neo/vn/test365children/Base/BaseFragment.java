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
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import neo.vn.test365children.Listener.ClickDialog;
import neo.vn.test365children.R;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class BaseFragment extends Fragment {

    protected static String TAG = BaseFragment.class.getSimpleName();
    /**
     * when activity is recycled by system, isFirstTimeStartFlag will be reset to default true,
     * when activity is recreated because a configuration change for example screen rotate, isFirstTimeStartFlag will stay false
     */
    private boolean isFirstTimeStartFlag = true;
    protected AlertDialog.Builder builder;
    protected final static int FIRST_TIME_START = 0; //when activity is first time start
    protected final static int SCREEN_ROTATE = 1;    //when activity is destroyed and recreated because a configuration change, see setRetainInstance(boolean retain)
    protected final static int ACTIVITY_DESTROY_AND_CREATE = 2;  //when activity is destroyed because memory is too low, recycled by android system


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");
        setRetainInstance(false);
    }


    @Override
    public void onStart() {
        Log.v(TAG, "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume");
    }

    @Override
    public void onPause() {
        Log.v(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.v(TAG, "onStop");
        super.onStop();
    }


    @Override
    public void onDestroy() {
        Log.v(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        Log.v(TAG, "onDestroyView");
        super.onDestroyView();
    }

    protected ProgressDialog dialog;
    private Handler StopDialogLoadingHandler = new Handler();

    public void hideDialogLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    public void showDialogLoading() {
        StopDialogLoadingHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, 35000);
        if (!getActivity().isFinishing()) {
            dialog = new ProgressDialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
            //     dialog = new ProgressDialog(getActivity());
            dialog.setCancelable(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("Loading. Please wait...");
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
            dialog = new ProgressDialog(getActivity());
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            dialog.setMessage(getString(R.string.txt_loading_dialog));
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
            dialog = new ProgressDialog(getActivity());
            dialog.setCancelable(false);
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
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        //For 3G check
        is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .isConnectedOrConnecting();
        //For WiFi Check
        isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnectedOrConnecting();
        if (!is3g && !isWifi) {
            showAlertDialog(getString(R.string.error_network), getString(R.string.error_network_message));
            return false;
        } else return true;
    }

    public void showAlertDialog(String title, String content) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getContext(),
                    R.style.MyStyleDialogAlert);
        } else
            builder = new AlertDialog.Builder(getContext(), R.style.MyStyleDialogAlert);

        builder.setTitle(title)
                .setCancelable(false)
                .setMessage(content)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete

                    }
                });
        builder.show();
        AlertDialog dialog = builder.show();
        TextView textView = (TextView)  dialog.findViewById(android.R.id.message);
        Typeface face=Typeface.createFromAsset(getContext().getAssets(),"utm_avo.ttf");
        textView.setTypeface(face);

    }

    public void showAlertErrorNetwork() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getContext(), R.style.MyStyleDialogAlert);
        } else
            builder = new AlertDialog.Builder(getContext(), R.style.MyStyleDialogAlert);


        builder.setTitle(getString(R.string.error_network))
                .setCancelable(false)
                .setMessage(getString(R.string.error_network_message))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .show();
        AlertDialog dialog = builder.show();
        TextView textView = (TextView)  dialog.findViewById(android.R.id.message);
        Typeface face=Typeface.createFromAsset(getContext().getAssets(),"utm_avo.ttf");
        textView.setTypeface(face);
    }


    public void showDialogComfirm(String title, String message, boolean is_hide_cancel,
                                  final ClickDialog clickDialog) {
        final Dialog dialog_yes = new Dialog(getContext(), R.style.MyStyleDialogAlert);
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
        // txt_buysongs.setText(Html.fromHtml("Để hoàn tất đăng ký dịch vụ RingTunes, Quý khách vui lòng thực hiện thao tác soạn tin nhắn <font color='#060606'>\"Y2 gửi 9194\"</font> từ số điện thoại giá cước: 3.000Đ/7 ngày. Cảm ơn Quý khách!"));
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_yes.dismiss();
                clickDialog.onClickYesDialog();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_yes.dismiss();
                clickDialog.onClickNoDialog();
            }
        });
        if (is_hide_cancel) {
            view_warning.setVisibility(View.GONE);
            btn_cancel.setVisibility(View.GONE);
        } else {
            view_warning.setVisibility(View.VISIBLE);
            btn_cancel.setVisibility(View.VISIBLE);
        }
        dialog_yes.show();

    }

    public void showDialogNotify(String title, String message) {
        final Dialog dialog_yes = new Dialog(getContext(), R.style.MyStyleDialogAlert);
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
        dialog_yes.show();
    }

}
