package com.todor.diabetes.models;

import android.os.Parcel;
import android.os.Parcelable;

public class TableProduct implements Parcelable {

    public String name;
    public int    gram;
    public float  glycemicIndex;

    public static final Creator<TableProduct> CREATOR = new Creator<TableProduct>() {
        @Override
        public TableProduct createFromParcel(Parcel in) {
            return new TableProduct(
                    in.readString(),
                    in.readInt(),
                    in.readFloat()
            );
        }

        @Override
        public TableProduct[] newArray(int size) {
            return new TableProduct[size];
        }
    };

    protected TableProduct(Parcel in) {
        name = in.readString();
        gram = in.readInt();
        glycemicIndex = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(gram);
        dest.writeFloat(glycemicIndex);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public TableProduct(String name, int gram, float glycemicIndex) {
        this.name = name;
        this.gram = gram;
        this.glycemicIndex = glycemicIndex;
    }

    @Override
    public String toString() {
        return "TableProduct{" +
                "glycemicIndex=" + glycemicIndex +
                ", name='" + name + '\'' +
                ", gram=" + gram +
                '}';
    }

}
