package com.example.administrator.test.bean.aidl

import android.os.Parcel
import android.os.Parcelable

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 9:31 2020/8/25
 */

data class ExData @JvmOverloads constructor(var name:String = "" ,var num:Int = 0):Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(num)
    }

    fun readFromParcel(parcel: Parcel){
        name = parcel.readString()
        num = parcel.readInt()
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ExData> {
        override fun createFromParcel(parcel: Parcel): ExData {
            return ExData(parcel)
        }

        override fun newArray(size: Int): Array<ExData?> {
            return arrayOfNulls(size)
        }
    }
}