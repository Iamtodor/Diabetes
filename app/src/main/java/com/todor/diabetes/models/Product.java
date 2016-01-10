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
                    in.readString(),
                    Boolean.parseBoolean(in.readString())
            );
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
    public String name;
    public float carbohydrates;
    public String group;
    public boolean isFavorite;

    public Product(String productName, float productCarbohydrates, String productGroup, boolean isFavorite) {
        this.name = productName;
        this.carbohydrates = productCarbohydrates;
        this.group = productGroup;
        this.isFavorite = isFavorite;
    }

    protected Product(Parcel in) {
        name = in.readString();
        carbohydrates = in.readFloat();
        group = in.readString();
        isFavorite = Boolean.parseBoolean(in.readString());
    }

    @Override
    public String toString() {
        return "Product{" +
                "carbohydrates=" + carbohydrates +
                ", name='" + name + '\'' +
                ", group='" + group + '\'' +
                ", isFavorite=" + isFavorite +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeFloat(carbohydrates);
        dest.writeString(group);
        dest.writeString(String.valueOf(isFavorite));
    }


    @Override
    public int describeContents() {
        return 0;
    }
}
