package neo.vn.test365children.Models;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DETAILSItem implements   Comparable<DETAILSItem>{

	@SerializedName("SUBJECT_NAME")
	private String sUBJECTNAME;

	@SerializedName("SUBJECT_ID")
	private int sUBJECTID;

	@SerializedName("EXERCISE_ID")
	private int eXERCISEID;

	@SerializedName("POINT")
	private float pOINT;

	@SerializedName("WEEK_NAME")
	private String wEEKNAME;

	public void setSUBJECTNAME(String sUBJECTNAME){
		this.sUBJECTNAME = sUBJECTNAME;
	}

	public String getSUBJECTNAME(){
		return sUBJECTNAME;
	}

	public void setSUBJECTID(int sUBJECTID){
		this.sUBJECTID = sUBJECTID;
	}

	public int getSUBJECTID(){
		return sUBJECTID;
	}

	public void setEXERCISEID(int eXERCISEID){
		this.eXERCISEID = eXERCISEID;
	}

	public int getEXERCISEID(){
		return eXERCISEID;
	}

	public void setPOINT(float pOINT){
		this.pOINT = pOINT;
	}

	public float getPOINT(){
		return pOINT;
	}

	public void setWEEKNAME(String wEEKNAME){
		this.wEEKNAME = wEEKNAME;
	}

	public String getWEEKNAME(){
		return wEEKNAME;
	}

	@Override
	public int compareTo(DETAILSItem exer2) {
		try {
			int number1 = Integer.parseInt(exer2.wEEKNAME.replace("Tuần", ""));
			int number2 = Integer.parseInt(wEEKNAME.replace("Tuần", ""));
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