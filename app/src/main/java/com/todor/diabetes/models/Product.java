package com.todor.diabetes.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(
                    in.readString(),
                    in.readFloat(),
                    in.readString()
            );
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
    public String name;
    public float  carbohydrates;
    public String group;

    public Product(String productName, float productCarbohydrates, String productGroup) {
        this.name = productName;
        this.carbohydrates = productCarbohydrates;
        this.group = productGroup;
    }

    protected Product(Parcel in) {
        name = in.readString();
        carbohydrates = in.readFloat();
        group = in.readString();
    }

    @Override
    public String toString() {
        return "Product{" +
                "carbohydrates=" + carbohydrates +
                ", name='" + name + '\'' +
                ", group='" + group + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeFloat(carbohydrates);
        dest.writeString(group);
    }


    @Override
    public int describeContents() {
        return 0;
    }
}
