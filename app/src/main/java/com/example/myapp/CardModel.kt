package com.example.myapp


import java.util.*


data class Card(
    var cardName:String?="",
    var cardHouse:String?="",
    var cardPoint:Int?=0,
    var cardImage:String?="",
    var isVisible: Boolean = true,
    var isSelect: Boolean = false,
    var id: String = UUID.randomUUID().toString(),
){

}