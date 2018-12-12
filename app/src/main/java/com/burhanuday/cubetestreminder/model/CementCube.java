package com.burhanuday.cubetestreminder.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * Created by Burhanuddin on 12-05-2018.
 */

public class CementCube implements Parcelable {
    private String location, date, concreteGrade, after3, after7, after14, after21, after28, after56;
    private int id, three,seven,fourteen,twentyOne,twentyEight, fiftySix;
    private float sThree1, sSeven1, sFourteen1, sTwentyOne1, sTwentyEight1, sFiftySix1, sThree2, sSeven2, sFourteen2, sTwentyOne2, sTwentyEight2, sFiftySix2, sThree3, sSeven3, sFourteen3, sTwentyOne3, sTwentyEight3, sFiftySix3;
    private View.OnClickListener requestBtnClickListener;

    public CementCube(int id, String location, String date, String concreteGrade, int three, int seven, int fourteen,
                      int twentyOne, int twentyEight, int fiftySix, float sThree1, float sSeven1, float sFourteen1,
                      float sTwentyOne1, float sTwentyEight1, float sFiftySix1,float sThree2, float sSeven2, float sFourteen2,
                      float sTwentyOne2, float sTwentyEight2, float sFiftySix2,float sThree3, float sSeven3, float sFourteen3,
                      float sTwentyOne3, float sTwentyEight3, float sFiftySix3, String after3, String after7,
                      String after14, String after21, String after28, String after56){
        this.id = id;
        this.location = location;
        this.date = date;
        this.concreteGrade = concreteGrade;
        this.three = three;
        this.seven = seven;
        this.fourteen = fourteen;
        this.twentyOne = twentyOne;
        this.twentyEight = twentyEight;
        this.fiftySix = fiftySix;
        this.sThree1 = sThree1;
        this.sSeven1 = sSeven1;
        this.sFourteen1 = sFourteen1;
        this.sTwentyOne1 = sTwentyOne1;
        this.sTwentyEight1 = sTwentyEight1;
        this.sFiftySix1 = sFiftySix1;
        this.sThree2 = sThree2;
        this.sSeven2 = sSeven2;
        this.sFourteen2 = sFourteen2;
        this.sTwentyOne2 = sTwentyOne2;
        this.sTwentyEight2 = sTwentyEight2;
        this.sFiftySix2 = sFiftySix2;
        this.sThree3 = sThree3;
        this.sSeven3 = sSeven3;
        this.sFourteen3 = sFourteen3;
        this.sTwentyOne3 = sTwentyOne3;
        this.sTwentyEight3 = sTwentyEight3;
        this.sFiftySix3 = sFiftySix3;
        this.after3 = after3;
        this.after7 = after7;
        this.after14 = after14;
        this.after21 = after21;
        this.after28 = after28;
        this.after56 = after56;
    }


    protected CementCube(Parcel in) {
        location = in.readString();
        date = in.readString();
        concreteGrade = in.readString();
        after3 = in.readString();
        after7 = in.readString();
        after14 = in.readString();
        after21 = in.readString();
        after28 = in.readString();
        after56 = in.readString();
        id = in.readInt();
        three = in.readInt();
        seven = in.readInt();
        fourteen = in.readInt();
        twentyOne = in.readInt();
        twentyEight = in.readInt();
        fiftySix = in.readInt();
        sThree1 = in.readFloat();
        sSeven1 = in.readFloat();
        sFourteen1 = in.readFloat();
        sTwentyOne1 = in.readFloat();
        sTwentyEight1 = in.readFloat();
        sFiftySix1 = in.readFloat();
        sThree2 = in.readFloat();
        sSeven2 = in.readFloat();
        sFourteen2 = in.readFloat();
        sTwentyOne2 = in.readFloat();
        sTwentyEight2 = in.readFloat();
        sFiftySix2 = in.readFloat();
        sThree3 = in.readFloat();
        sSeven3 = in.readFloat();
        sFourteen3 = in.readFloat();
        sTwentyOne3 = in.readFloat();
        sTwentyEight3 = in.readFloat();
        sFiftySix3 = in.readFloat();
    }

    public static final Creator<CementCube> CREATOR = new Creator<CementCube>() {
        @Override
        public CementCube createFromParcel(Parcel in) {
            return new CementCube(in);
        }

        @Override
        public CementCube[] newArray(int size) {
            return new CementCube[size];
        }
    };

    public int getId(){
        return id;
    }

    public float getsFiftySix1() {
        return sFiftySix1;
    }

    public float getsFourteen1() {
        return sFourteen1;
    }

    public float getsSeven1() {
        return sSeven1;
    }

    public float getsThree1() {
        return sThree1;
    }

    public float getsTwentyEight1() {
        return sTwentyEight1;
    }

    public float getsTwentyOne1() {
        return sTwentyOne1;
    }

    public float getsFiftySix2() {
        return sFiftySix2;
    }

    public float getsFourteen2() {
        return sFourteen2;
    }

    public float getsSeven2() {
        return sSeven2;
    }

    public float getsThree2() {
        return sThree2;
    }

    public float getsTwentyEight2() {
        return sTwentyEight2;
    }

    public float getsTwentyOne2() {
        return sTwentyOne2;
    }

    public float getsFiftySix3() {
        return sFiftySix3;
    }

    public float getsFourteen3() {
        return sFourteen3;
    }

    public float getsSeven3() {
        return sSeven3;
    }

    public float getsThree3() {
        return sThree3;
    }

    public float getsTwentyEight3() {
        return sTwentyEight3;
    }

    public float getsTwentyOne3() {
        return sTwentyOne3;
    }

    public String getConcreteGrade() {
        return concreteGrade;
    }

    public String getDate1() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public int getSeven(){
        return seven;
    }

    public int getThree(){
        return three;
    }

    public int getFourteen(){
        return fourteen;
    }

    public int getTwentyOne(){
        return twentyOne;
    }

    public int getTwentyEight(){
        return twentyEight;
    }

    public int getFiftySix() {
        return fiftySix;
    }

    public void setConcreteGrade(String concreteGrade) {
        this.concreteGrade = concreteGrade;
    }

    public String getAfter3() {
        return after3;
    }

    public String getAfter7() {
        return after7;
    }

    public String getAfter14() {
        return after14;
    }

    public String getAfter21() {
        return after21;
    }

    public String getAfter28() {
        return after28;
    }

    public String getAfter56() {
        return after56;
    }

    public View.OnClickListener getRequestBtnClickListener() {
        return requestBtnClickListener;
    }

    public void setRequestBtnClickListener(View.OnClickListener requestBtnClickListener) {
        this.requestBtnClickListener = requestBtnClickListener;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(location);
        parcel.writeString(date);
        parcel.writeString(concreteGrade);
        parcel.writeString(after3);
        parcel.writeString(after7);
        parcel.writeString(after14);
        parcel.writeString(after21);
        parcel.writeString(after28);
        parcel.writeString(after56);
        parcel.writeInt(id);
        parcel.writeInt(three);
        parcel.writeInt(seven);
        parcel.writeInt(fourteen);
        parcel.writeInt(twentyOne);
        parcel.writeInt(twentyEight);
        parcel.writeInt(fiftySix);
        parcel.writeFloat(sThree1);
        parcel.writeFloat(sSeven1);
        parcel.writeFloat(sFourteen1);
        parcel.writeFloat(sTwentyOne1);
        parcel.writeFloat(sTwentyEight1);
        parcel.writeFloat(sFiftySix1);
        parcel.writeFloat(sThree2);
        parcel.writeFloat(sSeven2);
        parcel.writeFloat(sFourteen2);
        parcel.writeFloat(sTwentyOne2);
        parcel.writeFloat(sTwentyEight2);
        parcel.writeFloat(sFiftySix2);
        parcel.writeFloat(sThree3);
        parcel.writeFloat(sSeven3);
        parcel.writeFloat(sFourteen3);
        parcel.writeFloat(sTwentyOne3);
        parcel.writeFloat(sTwentyEight3);
        parcel.writeFloat(sFiftySix3);
    }
}
