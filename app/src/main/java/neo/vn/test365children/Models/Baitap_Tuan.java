package neo.vn.test365children.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Baitap_Tuan implements Parcelable,  Comparable<Baitap_Tuan> {
    @SerializedName("ERROR")
    String sERROR;
    @SerializedName("MESSAGE")
    String sMESSAGE;
    @SerializedName("RESULT")
    String sRESULT;
    @SerializedName("ID")
    String sID;
    @SerializedName("YEAR_ID")
    String sYEAR_ID;
    @SerializedName("YEAR_NAME")
    String sYEAR_NAME;
    @SerializedName("LEVEL_ID")
    String sLEVEL_ID;
    @SerializedName("LEVEL_NAME")
    String sLEVEL_NAME;
    @SerializedName("SUBJECT_ID")
    String sSUBJECT_ID;
    @SerializedName("SUBJECT_NAME")
    String sSUBJECT_NAME;
    @SerializedName("WEEK_ID")
    String sWEEK_ID;
    @SerializedName("WEEK_NAME")
    String sWEEK_NAME;
    @SerializedName("NAME")
    String sNAME;
    @SerializedName("REQUIREMENT")
    String sREQUIREMENT;
    @SerializedName("OBJECTIVE")
    String sOBJECTIVE;
    @SerializedName("PRICE")
    String sPRICE;
    @SerializedName("STARTTIME")
    String sSTARTTIME;
    @SerializedName("ENDTIME")
    String sENDTIME;
    @SerializedName("CHILD_ID")
    String sCHILD_ID;
    @SerializedName("STATE_BUY")
    String sSTATE_BUY;
    @SerializedName("PARENT_ID")
    String sPARENT_ID;
    @SerializedName("MATH_TAKEN_TIME")
    String sMATH_TAKEN_TIME;
    @SerializedName("VIETNAMESE_TAKEN_TIME")
    String sVIETNAMESE_TAKEN_TIME;
    @SerializedName("ENGLISH_TAKEN_TIME")
    String sENGLISH_TAKEN_TIME;
    @SerializedName("MATH_TAKEN_DURATION")
    String sMATH_TAKEN_DURATION;
    @SerializedName("VIETNAMESE_TAKEN_DURATION")
    String sVIETNAMESE_TAKEN_DURATION;
    @SerializedName("ENGLISH_TAKEN_DURATION")
    String sENGLISH_TAKEN_DURATION;
    @SerializedName("STATUS_TAKEN")
    String sSTATUS_TAKEN;
    @SerializedName("BOOK_ID")
    String BOOK_ID;

    public Baitap_Tuan() {
    }

    protected Baitap_Tuan(Parcel in) {
        sERROR = in.readString();
        sMESSAGE = in.readString();
        sRESULT = in.readString();
        sID = in.readString();
        sYEAR_ID = in.readString();
        sYEAR_NAME = in.readString();
        sLEVEL_ID = in.readString();
        sLEVEL_NAME = in.readString();
        sSUBJECT_ID = in.readString();
        sSUBJECT_NAME = in.readString();
        sWEEK_ID = in.readString();
        sWEEK_NAME = in.readString();
        sNAME = in.readString();
        sREQUIREMENT = in.readString();
        sOBJECTIVE = in.readString();
        sPRICE = in.readString();
        sSTARTTIME = in.readString();
        sENDTIME = in.readString();
        sCHILD_ID = in.readString();
        sSTATE_BUY = in.readString();
        sPARENT_ID = in.readString();
        sMATH_TAKEN_TIME = in.readString();
        sVIETNAMESE_TAKEN_TIME = in.readString();
        sENGLISH_TAKEN_TIME = in.readString();
        sMATH_TAKEN_DURATION = in.readString();
        sVIETNAMESE_TAKEN_DURATION = in.readString();
        sENGLISH_TAKEN_DURATION = in.readString();
    }

    public static final Creator<Baitap_Tuan> CREATOR = new Creator<Baitap_Tuan>() {
        @Override
        public Baitap_Tuan createFromParcel(Parcel in) {
            return new Baitap_Tuan(in);
        }

        @Override
        public Baitap_Tuan[] newArray(int size) {
            return new Baitap_Tuan[size];
        }
    };

    public String getBOOK_ID() {
        return BOOK_ID;
    }

    public void setBOOK_ID(String BOOK_ID) {
        this.BOOK_ID = BOOK_ID;
    }

    public String getsYEAR_NAME() {
        return sYEAR_NAME;
    }

    public void setsYEAR_NAME(String sYEAR_NAME) {
        this.sYEAR_NAME = sYEAR_NAME;
    }

    public String getsSTARTTIME() {
        return sSTARTTIME;
    }

    public void setsSTARTTIME(String sSTARTTIME) {
        this.sSTARTTIME = sSTARTTIME;
    }

    public String getsENDTIME() {
        return sENDTIME;
    }

    public void setsENDTIME(String sENDTIME) {
        this.sENDTIME = sENDTIME;
    }

    public String getsCHILD_ID() {
        return sCHILD_ID;
    }

    public void setsCHILD_ID(String sCHILD_ID) {
        this.sCHILD_ID = sCHILD_ID;
    }

    public String getsSTATE_BUY() {
        return sSTATE_BUY;
    }

    public void setsSTATE_BUY(String sSTATE_BUY) {
        this.sSTATE_BUY = sSTATE_BUY;
    }

    public String getsPARENT_ID() {
        return sPARENT_ID;
    }

    public void setsPARENT_ID(String sPARENT_ID) {
        this.sPARENT_ID = sPARENT_ID;
    }

    public String getsMATH_TAKEN_TIME() {
        return sMATH_TAKEN_TIME;
    }

    public void setsMATH_TAKEN_TIME(String sMATH_TAKEN_TIME) {
        this.sMATH_TAKEN_TIME = sMATH_TAKEN_TIME;
    }

    public String getsVIETNAMESE_TAKEN_TIME() {
        return sVIETNAMESE_TAKEN_TIME;
    }

    public void setsVIETNAMESE_TAKEN_TIME(String sVIETNAMESE_TAKEN_TIME) {
        this.sVIETNAMESE_TAKEN_TIME = sVIETNAMESE_TAKEN_TIME;
    }

    public String getsENGLISH_TAKEN_TIME() {
        return sENGLISH_TAKEN_TIME;
    }

    public void setsENGLISH_TAKEN_TIME(String sENGLISH_TAKEN_TIME) {
        this.sENGLISH_TAKEN_TIME = sENGLISH_TAKEN_TIME;
    }

    public String getsMATH_TAKEN_DURATION() {
        return sMATH_TAKEN_DURATION;
    }

    public void setsMATH_TAKEN_DURATION(String sMATH_TAKEN_DURATION) {
        this.sMATH_TAKEN_DURATION = sMATH_TAKEN_DURATION;
    }

    public String getsVIETNAMESE_TAKEN_DURATION() {
        return sVIETNAMESE_TAKEN_DURATION;
    }

    public void setsVIETNAMESE_TAKEN_DURATION(String sVIETNAMESE_TAKEN_DURATION) {
        this.sVIETNAMESE_TAKEN_DURATION = sVIETNAMESE_TAKEN_DURATION;
    }

    public String getsENGLISH_TAKEN_DURATION() {
        return sENGLISH_TAKEN_DURATION;
    }

    public void setsENGLISH_TAKEN_DURATION(String sENGLISH_TAKEN_DURATION) {
        this.sENGLISH_TAKEN_DURATION = sENGLISH_TAKEN_DURATION;
    }


    private static Baitap_Tuan getObject(JSONObject jsonObject) {
        return new Gson().fromJson(jsonObject.toString(), Baitap_Tuan.class);
    }

    public static ArrayList<Baitap_Tuan> getList(String jsonArray)
            throws JSONException {
        ArrayList<Baitap_Tuan> arrayList = new ArrayList<>();
        Type type = new TypeToken<List<Baitap_Tuan>>() {
        }.getType();
        Gson gson = new Gson();
        arrayList = gson.fromJson(jsonArray, type);
        return arrayList;
    }

    public String getsSTATUS_TAKEN() {
        return sSTATUS_TAKEN;
    }

    public void setsSTATUS_TAKEN(String sSTATUS_TAKEN) {
        this.sSTATUS_TAKEN = sSTATUS_TAKEN;
    }

    public String getsERROR() {
        return sERROR;
    }

    public void setsERROR(String sERROR) {
        this.sERROR = sERROR;
    }

    public String getsMESSAGE() {
        return sMESSAGE;
    }

    public void setsMESSAGE(String sMESSAGE) {
        this.sMESSAGE = sMESSAGE;
    }

    public String getsRESULT() {
        return sRESULT;
    }

    public void setsRESULT(String sRESULT) {
        this.sRESULT = sRESULT;
    }

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }

    public String getsYEAR_ID() {
        return sYEAR_ID;
    }

    public void setsYEAR_ID(String sYEAR_ID) {
        this.sYEAR_ID = sYEAR_ID;
    }

    public String getsLEVEL_ID() {
        return sLEVEL_ID;
    }

    public void setsLEVEL_ID(String sLEVEL_ID) {
        this.sLEVEL_ID = sLEVEL_ID;
    }

    public String getsLEVEL_NAME() {
        return sLEVEL_NAME;
    }

    public void setsLEVEL_NAME(String sLEVEL_NAME) {
        this.sLEVEL_NAME = sLEVEL_NAME;
    }

    public String getsSUBJECT_ID() {
        return sSUBJECT_ID;
    }

    public void setsSUBJECT_ID(String sSUBJECT_ID) {
        this.sSUBJECT_ID = sSUBJECT_ID;
    }

    public String getsSUBJECT_NAME() {
        return sSUBJECT_NAME;
    }

    public void setsSUBJECT_NAME(String sSUBJECT_NAME) {
        this.sSUBJECT_NAME = sSUBJECT_NAME;
    }

    public String getsWEEK_ID() {
        return sWEEK_ID;
    }

    public void setsWEEK_ID(String sWEEK_ID) {
        this.sWEEK_ID = sWEEK_ID;
    }

    public String getsWEEK_NAME() {
        return sWEEK_NAME;
    }

    public void setsWEEK_NAME(String sWEEK_NAME) {
        this.sWEEK_NAME = sWEEK_NAME;
    }

    public String getsNAME() {
        return sNAME;
    }

    public void setsNAME(String sNAME) {
        this.sNAME = sNAME;
    }

    public String getsREQUIREMENT() {
        return sREQUIREMENT;
    }

    public void setsREQUIREMENT(String sREQUIREMENT) {
        this.sREQUIREMENT = sREQUIREMENT;
    }

    public String getsOBJECTIVE() {
        return sOBJECTIVE;
    }

    public void setsOBJECTIVE(String sOBJECTIVE) {
        this.sOBJECTIVE = sOBJECTIVE;
    }

    public String getsPRICE() {
        return sPRICE;
    }

    public void setsPRICE(String sPRICE) {
        this.sPRICE = sPRICE;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(sERROR);
        dest.writeString(sMESSAGE);
        dest.writeString(sRESULT);
        dest.writeString(sID);
        dest.writeString(sYEAR_ID);
        dest.writeString(sYEAR_NAME);
        dest.writeString(sLEVEL_ID);
        dest.writeString(sLEVEL_NAME);
        dest.writeString(sSUBJECT_ID);
        dest.writeString(sSUBJECT_NAME);
        dest.writeString(sWEEK_ID);
        dest.writeString(sWEEK_NAME);
        dest.writeString(sNAME);
        dest.writeString(sREQUIREMENT);
        dest.writeString(sOBJECTIVE);
        dest.writeString(sPRICE);
        dest.writeString(sSTARTTIME);
        dest.writeString(sENDTIME);
        dest.writeString(sCHILD_ID);
        dest.writeString(sSTATE_BUY);
        dest.writeString(sPARENT_ID);
        dest.writeString(sMATH_TAKEN_TIME);
        dest.writeString(sVIETNAMESE_TAKEN_TIME);
        dest.writeString(sENGLISH_TAKEN_TIME);
        dest.writeString(sMATH_TAKEN_DURATION);
        dest.writeString(sVIETNAMESE_TAKEN_DURATION);
        dest.writeString(sENGLISH_TAKEN_DURATION);
    }


    @Override
    public int compareTo(Baitap_Tuan exer2) {
        try {
            int number1 = Integer.parseInt(exer2.sWEEK_ID);
            int number2 = Integer.parseInt(sWEEK_ID);
            if (number1 == number2)
                return 0;
            else if (number1 > number2)
                return 1;
            else
                return -1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}

