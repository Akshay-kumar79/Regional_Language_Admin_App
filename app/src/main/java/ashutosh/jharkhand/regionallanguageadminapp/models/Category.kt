package ashutosh.jharkhand.regionallanguageadminapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    val id: String,
    val categoryName: String,
    val categoryImage: String
): Parcelable