package com.example.myapp

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Kisiler(var kullanici_ad:String? = "",var email:String?="",var sifre:String?="") {

}
