package neo.vn.test365children.Untils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by Envy 15T on 6/5/2015.
 */
public class StringUtil {
    public static void start_facebook(Activity activity,String sIdPage) {
        final String urlFb = "fb://page/" + sIdPage;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(urlFb));

        // If a Facebook app is installed, use it. Otherwise, launch
        // a browser
        final PackageManager packageManager = activity.getPackageManager();
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() == 0) {
            final String urlBrowser = "https://www.facebook.com/pages/" + sIdPage;
            intent.setData(Uri.parse(urlBrowser));
        }
        activity.startActivity(intent);
    }
    public static String ConvertFraction(String a, String b, String c) {
        return "\\(" + a + "\\dfrac{" + b + "} {" + c + "} \\)";
    }

    public static void sendSMS(Activity activity, String sContent, String sPhone) {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        smsIntent.setData(Uri.parse("smsto:" + sPhone)); // This ensures only SMS apps respond
        smsIntent.putExtra("sms_body", sContent);
        activity.startActivity(smsIntent);
    }

    public static void onLunchAnotherApp(Context context) {

        final String appPackageName = "neo.vn.test365home";

        Intent intent = context.getPackageManager().getLaunchIntentForPackage(appPackageName);
        if (intent != null) {

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        } else {

            onGoToAnotherInAppStore(intent, appPackageName, context);

        }

    }

    public static void onGoToAnotherInAppStore(Intent intent, String appPackageName, Context context) {
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://details?id=" + appPackageName));
            context.startActivity(intent);

        } catch (android.content.ActivityNotFoundException anfe) {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName));
            context.startActivity(intent);

        }

    }

    public static void share_app(Activity activity, String content) {
        final String my_package_name = "neo.vn.test365children";  // <- HERE YOUR PACKAGE NAME!!
        String url = "";
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Home365");
            // String sAux = "Tải Home365 tại https://home365.online để con làm bài tập về nhà, rất tuyệt vời.";
            url = "https://play.google.com/store/apps/details?id=" + my_package_name;
            // sAux = sAux + url + "\n\n";
            i.putExtra(Intent.EXTRA_TEXT, content);
            activity.startActivity(Intent.createChooser(i, "Home365"));
        } catch (Exception e) {
            e.toString();
        }
    }

    public static List<Integer> check_random(List<Integer> mLisInput) {
        List<Integer> mLisOutput = new ArrayList<>();
        boolean iCheck = false;
        int iOne, iTwo;
        if (mLisInput.size() > 0) {
            do {
                iCheck = false;
                iOne = new Random().nextInt(24);
                for (int i = 0; i < mLisInput.size(); i++) {
                    if (iOne == mLisInput.get(i)) {
                        iCheck = true;
                    }
                }
            } while (iCheck);
        } else {
            iOne = new Random().nextInt(24);
        }
        mLisInput.add(iOne);
        mLisOutput.add(iOne);
        if (mLisInput.size() > 0) {
            do {
                iCheck = false;
                iTwo = new Random().nextInt(24);
                for (int i = 0; i < mLisInput.size(); i++) {
                    if (iTwo == mLisInput.get(i)) {
                        iCheck = true;
                    }
                }
            } while (iCheck);
        } else {
            iTwo = new Random().nextInt(24);
        }
        mLisOutput.add(iTwo);
        return mLisOutput;
    }

    public static int check_random_one(List<Integer> mLisInput) {
        int iOne;
        boolean iCheck = false;
        if (mLisInput.size() > 0) {
            do {
                iCheck = false;
                iOne = new Random().nextInt(24);
                for (int i = 0; i < mLisInput.size(); i++) {
                    if (iOne == mLisInput.get(i)) {
                        iCheck = true;
                    }
                }
            } while (iCheck);
        } else {
            iOne = new Random().nextInt(24);
        }
        mLisInput.add(iOne);
        return iOne;
    }

    public static boolean check_list_true(int iValue, List<Integer> mList) {
        boolean iCheck = false;
        for (int i : mList) {
            if (i == iValue) {
                return true;
            }
        }
        return iCheck;

    }

    public static List<Integer> remove_two_list(List<Integer> listChuan, List<Integer> lisCanxoa) {
        List<Integer> mLisResult = new ArrayList<>();
        lisCanxoa.removeAll(listChuan);

        return lisCanxoa;
    }

    public static String format_point(float fPoint) {
        String sPoint = "" + fPoint;
        String[] sTest = sPoint.split("\\.");
        if (sTest[1].equals("0")) {
            int iPoint = (int) Math.round(fPoint);
            return "" + iPoint;
        } else {
            double f = Double.parseDouble("0." + sTest[1]);
            if (f > 0 && f < 0.25) {
                return sTest[0];
            } else if (f >= 0.25 && f < 0.5) {
                return sTest[0] + ".25";
            } else if (f >= 0.5 && f < 0.75) {
                return sTest[0] + ".5";
            } else if (f >= 0.75 && f < 1) {
                return sTest[0] + ".75";
            }
            return "";
        }
    }

    public static String StringFraction(String sInput) {
        String sOutput = "";
        List<String> lisInput = Arrays.asList(sInput.split(" "));
        for (String s : lisInput) {
            if (s.indexOf("//") > 0) {
                String[] s_phanso = s.split("//");
                if (s_phanso.length > 1) {
                    if (s_phanso[0].indexOf("||") > 0) {
                        String s_honso = s_phanso[0].replace("||", "<<");
                        String[] sHonso = s_honso.split("<<");
                        if (sHonso.length == 2)
                            s = StringUtil.ConvertFraction(sHonso[0], sHonso[1], s_phanso[1]);
                    } else {
                        s = StringUtil.ConvertFraction("", s_phanso[0], s_phanso[1]);
                    }
                }
                sOutput = sOutput + s + " ";
            } else {
                sOutput = sOutput + s + " ";
            }
        }
        return sOutput;
    }

    public static String ConvertFraction_Dapan(String a, String b, String c) {
        return "$$" + a + "\\dfrac{" + b + "} {" + c + "} $$";
    }

    public static String get_current_time() {
        Calendar cal;
        SimpleDateFormat dft = null;
        String date = "";
        //Set ngày giờ hiện tại khi mới chạy lần đầu
        cal = Calendar.getInstance();
        //Định dạng kiểu ngày / tháng /năm
        dft = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        date = dft.format(cal.getTime());
        //hiển thị lên giao diện
        return date;
    }

    public static void initWebview(WebView webview_debai, String link_web) {

        webview_debai.getSettings().setJavaScriptEnabled(true);
        webview_debai.getSettings();
        webview_debai.setBackgroundColor(Color.TRANSPARENT);
        WebSettings webSettings = webview_debai.getSettings();
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        webSettings.setDefaultFontSize(18);
        webSettings.setTextZoom((int) (webSettings.getTextZoom() * 1.2));
        //  settings_dapan.setTextZoom((int) (settings.getTextZoom() * 1.1));
        /* <html><body  align='center'>You scored <b>192</b> points.</body></html>*/
     //   String pish = "<html><body  align='center'>";
        String pish = "<html><head><style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/utm_avo.ttf\")}body {font-family: MyFont;font-size: medium;text-align: center;}</style></head><body>";
        String pas = "</body></html>";

        webview_debai.loadDataWithBaseURL("", pish + convert_html(link_web) + pas,
                "text/html", "UTF-8", "");
    }

    public static void initWebview_Batsau(WebView webview_debai, String link_web) {

        webview_debai.getSettings().setJavaScriptEnabled(true);
        webview_debai.getSettings();
        webview_debai.setBackgroundColor(Color.TRANSPARENT);
        WebSettings webSettings = webview_debai.getSettings();
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        webSettings.setDefaultFontSize(18);
        webSettings.setTextZoom((int) (webSettings.getTextZoom() * 1.2));
        /* <html><body  align='center'>You scored <b>192</b> points.</body></html>*/
       // String pish = "<html><body>";
        String pish = "<html><head><style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/utm_avo.ttf\")}body {font-family: MyFont;font-size: medium;text-align: center;}</style></head><body>";
        String pas = "</body></html>";

        webview_debai.loadDataWithBaseURL("", pish + convert_html(link_web.trim()) + pas,
                "text/html", "UTF-8", "");
    }

    public static void initWebview_Whitetext(WebView webview_debai, String link_web) {
        // webview_debai.setInitialScale(220);
        webview_debai.getSettings().setJavaScriptEnabled(true);
        webview_debai.getSettings();
        webview_debai.clearCache(true);
        webview_debai.clearFormData();
        webview_debai.clearHistory();
        webview_debai.setBackgroundColor(Color.TRANSPARENT);
        WebSettings webSettings = webview_debai.getSettings();
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        webSettings.setDefaultFontSize(18);
        webSettings.setTextZoom((int) (webSettings.getTextZoom() * 1.2));
        /* <html><body  align='center'>You scored <b>192</b> points.</body></html>*/
        String pish = "<html><body  align='center'>";
        String pas = "</body></html>";
        String text = "<html><head>"
                + "<style type=\"text/css\">body{color: #fff;}"
                + "</style></head>"
                + "<body>"
                + convert_html(link_web)
                + "</body></html>";
        webview_debai.loadDataWithBaseURL("", pish + text + pas,
                "text/html", "UTF-8", "");
    }

    public static void initWebview_Whitetext_notcenter(WebView webview_debai, String link_web) {
        //webview_debai.setInitialScale(220);
        webview_debai.getSettings().setJavaScriptEnabled(true);
        webview_debai.getSettings();
        webview_debai.setBackgroundColor(Color.TRANSPARENT);
        WebSettings webSettings = webview_debai.getSettings();
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        webSettings.setDefaultFontSize(18);
        webSettings.setTextZoom((int) (webSettings.getTextZoom() * 1.2));
        /* <html><body  align='center'>You scored <b>192</b> points.</body></html>*/
        String pish = "<html><body  align='center'>";
        String pas = "</body></html>";
        String text = "<html><head>"
                + "<style type=\"text/css\">body{color: #fff;}"
                + "</style></head>"
                + "<body>"
                + convert_html(link_web)
                + "</body></html>";
        webview_debai.loadDataWithBaseURL("", text, "text/html", "UTF-8", "");
    }

    public static void initWebview_Whitetext_game(WebView webview_debai, String link_web) {
        //  webview_debai.setInitialScale(250);
        webview_debai.getSettings().setJavaScriptEnabled(true);
        webview_debai.getSettings();
        webview_debai.setBackgroundColor(Color.TRANSPARENT);
        WebSettings webSettings = webview_debai.getSettings();
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        webSettings.setDefaultFontSize(18);
        webSettings.setTextZoom((int) (webSettings.getTextZoom() * 1.2));
        /* <html><body  align='center'>You scored <b>192</b> points.</body></html>*/
        String pish = "<html><body  align='center'>";
        String pas = "</body></html>";
        String text = "<html><head>"
                + "<style type=\"text/css\">body{color: #fff;}"
                + "</style></head>"
                + "<body>"
                + link_web.replaceAll("#", "\"")
                + "</body></html>";
        webview_debai.loadDataWithBaseURL("", pish + text + pas,
                "text/html", "UTF-8", "");
    }

    public static String convert_html(String content) {
        String s_resutl = "";
        if (content != null && content.length() > 0) {
            s_resutl = content.replaceAll("&#34;", "\"");
            s_resutl = s_resutl.replaceAll("&#92;", "\\\\");
        }
        return s_resutl;
    }

    public static String StringFraction_Dapan(String sInput) {
        String sOutput = "";
        List<String> lisInput = Arrays.asList(sInput.split(" "));
        for (String s : lisInput) {
            if (s.indexOf("//") > 0) {
                String[] s_phanso = s.split("//");
                if (s_phanso.length > 1) {
                    if (s_phanso[0].indexOf("||") > 0) {
                        String s_honso = s_phanso[0].replace("||", "<<");
                        String[] sHonso = s_honso.split("<<");
                        if (sHonso.length == 2)
                            s = StringUtil.ConvertFraction_Dapan(sHonso[0], sHonso[1], s_phanso[1]);
                    } else {
                        s = StringUtil.ConvertFraction_Dapan("", s_phanso[0], s_phanso[1]);
                    }
                }
                sOutput = sOutput + s + " ";
            } else {
                sOutput = sOutput + s + " ";
            }
        }
        return sOutput;
    }

    /**
     * Upper case first letter in string
     *
     * @param str
     * @return
     */
    public static String UppercaseFirstLetters(String str) {
        boolean prevWasWhiteSp = true;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isLetter(chars[i])) {
                if (prevWasWhiteSp) {
                    chars[i] = Character.toUpperCase(chars[i]);
                }
                prevWasWhiteSp = false;
            } else {
                prevWasWhiteSp = Character.isWhitespace(chars[i]);
            }
        }
        return new String(chars);
    }

    public static String formatNumber(String number) {
        if (number == null)
            return "";
        number = number.replaceAll(" ", "");
        number = number.replaceAll(",", "");
        int iNumber = Integer.parseInt(number);
        DecimalFormat formatter = new DecimalFormat("###,###,###");

        String sMonney = (formatter.format(iNumber) + " đ");

        return sMonney;
    }

    public static void call_phone(Context mContext, String phone) {
        sPhone = phone;
        if (Build.VERSION.SDK_INT < 23) {
            phoneCall(mContext, phone);
        } else {
            if (ActivityCompat.checkSelfPermission(mContext,
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                phoneCall(mContext, phone);
            } else {
                final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                //Asking request Permissions
                ActivityCompat.requestPermissions((Activity) mContext, PERMISSIONS_STORAGE, 9);
            }
        }
    }

    public static String sPhone;

    private static void phoneCall(Context mContext, String phone) {
        if (ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phone));
            mContext.startActivity(callIntent);
        } else {
            Toast.makeText(mContext, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }

    public static String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replaceAll("đ", "d");
    }

    public static String formatDate(int year, int monthOfYear, int dayOfMonth) {
        return year + "/" + monthOfYear + "/" + dayOfMonth;
    }

    public static void open_browser_android(Activity activity, String sUrl) {
        if (sUrl != null) {
            String url = sUrl;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            activity.startActivity(i);
        } else return;
    }

    public static String formatDateJP(int year, int monthOfYear, int dayOfMonth) {
        return year + "年" + monthOfYear + "月" + dayOfMonth + "日";
    }


    public static String formatDate(Calendar calendar) {
        return formatDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static String formatDateJP(Calendar calendar) {
        return formatDateJP(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static boolean checkStringValid(String data) {
        if (data != null && !data.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkStringNull(String data) {
        if (data != null) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkZero(String data) {
        if (checkStringValid(data) && !data.equals("0"))
            return true;
        else
            return false;
    }

    public static void displayText(String text, TextView textView) {
        if (textView == null)
            return;
        if (text != null && !text.isEmpty() && !text.equals("null")) {
            textView.setText(text);
        } else {
            textView.setText("");
        }

    }

    public static boolean isPhoneNumber(String receiver) {
        boolean ok = true;
        if (receiver.length() > 12
                || receiver.length() < 9
                || (receiver.length() == 9 && !(receiver.startsWith("9") || receiver.startsWith("89") || receiver.startsWith("88") || receiver.startsWith("86")))
                || (receiver.length() == 10 && !(receiver.startsWith("09") || receiver.startsWith("089") || receiver.startsWith("088") || receiver.startsWith("086") || receiver.startsWith("1")))
                || (receiver.length() == 11 && !(receiver.startsWith("849") || receiver.startsWith("8489") || receiver.startsWith("8488") || receiver.startsWith("8486") || receiver.startsWith("01")))
                || (receiver.length() == 12 && !receiver.startsWith("841"))) {
            ok = false;
        } else {
            for (int i = 0; i < receiver.length(); i++) {
                char c = receiver.charAt(i);
                if ((c > '9') || (c < '0')) {
                    ok = false;
                    break;
                }
            }
        }
        return ok;
    }

    public static void displayText(String text, TextView textView, String prefix) {
        if (textView == null)
            return;
        if (text != null && !text.isEmpty() && !text.equals("null")) {
            textView.setText(text + prefix);
        } else {
            textView.setText("0" + prefix);
        }

    }

    public static void displayText(Float text, TextView textView) {
        if (text != null) {
            String.format("%.0f", text);
        } else {
            textView.setText("");
        }
    }

    public static void displayText(int text, TextView textView) {
        if (text > 0) {
            textView.setText(text + "");
        } else {
            textView.setText("");
        }
    }

    /**
     * Check valid email
     *
     * @param target
     * @return true if valid email, false if invalid email
     */
    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static boolean validatePhoneNumber(String s) {
        if (s == null)
            return false;
        for (char c : s.toCharArray())
            if (c < '0' || c > '9')
                return false;
        return true;
    }

    public static DecimalFormat setDecimalFormat() {
        Locale locale = new Locale("en", "UK");
        String pattern = "#,###";
        DecimalFormat decimalFormat = (DecimalFormat)
                NumberFormat.getNumberInstance(locale);
        decimalFormat.applyPattern(pattern);
        return decimalFormat;
    }

    public static void fillPrefix(TextView tv, float value, String prefix) {
        if (value == 0) {
            tv.setText(prefix + "0.00");
        } else if (value > 0) {
            if (!convertTextFloattoInt(String.valueOf(value))) {
                tv.setText(prefix + setDecimalFormat().format(value));
            } else {
                tv.setText(prefix + setDecimalFormat().format(value));
            }
        } else {
            tv.setText("");
        }
    }

    public static void fillSuffixes(TextView tv, float value, String suffixes) {
        if (value == 0) {
            tv.setText("0.00" + suffixes);
        } else if (value > 0) {
            if (!convertTextFloattoInt(String.valueOf(value))) {
                tv.setText(setDecimalFormat().format(value) + suffixes);
            } else {
                tv.setText(setDecimalFormat().format(value) + suffixes);
            }
        } else {
            tv.setText("");
        }
    }

    /**
     * Ellipsize string with maxCharacter
     *
     * @param input
     * @param maxCharacters
     * @return
     */
    public static String ellipsize(String input, int maxCharacters) {
        if (maxCharacters < 3) {
            throw new IllegalArgumentException("maxCharacters must be at least 3 because the ellipsis already take up 3 characters");
        }
        if (input == null || input.length() < maxCharacters) {
            return input;
        }
        return input.substring(0, maxCharacters - 3) + "...";
    }

    public static boolean checkTypeFloat(String value) {
        if (value.contains(".")) {
            return true;
        }
        return false;
    }

    public static boolean convertTextFloattoInt(String value) {
        String result5[] = value.split("[.]");
        int b = Integer.parseInt(result5[1]);
        if (b > 0) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(String input) {
        if (input == null || input.length() == 0) {
            return true;
        }
        return false;
    }

    public static String addString(String text) {
        return String.format("     %s     %s     %s", text, text, text);
    }

    public static String messageRegister(String text1, String text2) {
        return String.format("%s <font color='#ff5000'>%s</font>", text1, text2);

    }

    public static boolean checkFormatDate(String input) {
        return input.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})");
    }

    public static <T> T[] appendArrString(T[] arr, T element) {
        final int N = arr.length;
        arr = Arrays.copyOf(arr, N + 1);
        arr[N] = element;
        return arr;
    }

}
