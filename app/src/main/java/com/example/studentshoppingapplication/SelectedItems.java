package com.example.studentshoppingapplication;

public class SelectedItems {
    private String mItemName;
    private float mItemPrice;

    public SelectedItems(String itemName, float itemPrice ){
        mItemName = itemName;
        mItemPrice = itemPrice;
    }
    public String getmItemName(){
        return mItemName;
    }

    public float getmItemPrice(){
        return mItemPrice;
    }
}
