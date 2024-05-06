package com.example.myapp

import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import java.io.*
import java.lang.Error

val liste = mutableListOf<Card>()
var kartList = MutableLiveData<List<Card>>()

var column = 2
var count = 4
var oyuncu = ""
var zorluk = ""
var oyunBittiMi = mutableStateOf(false)
var oyunSkor = 0.0
var cardSize = 125
var tiklanilanIsım= mutableStateOf("")
var tiklanilanPuan = mutableStateOf(0)
var oyunSkor1 = 0.0
var oyunSkor2 = 0.0
var sira = mutableStateOf("1. Oyuncunun sırası")
var player = mutableStateOf(1)


class EmojiViewModel : ViewModel() {
    private val cards: MutableLiveData<MutableList<Card>> by lazy {
        MutableLiveData<MutableList<Card>>()
    }

    fun getCards(): LiveData<MutableList<Card>> {
        return cards
    }

    val db = FirebaseDatabase.getInstance()
    var ref = db.getReference("kartlar")

    fun kartAl(){                                                    // veri atbanı  Al

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (d in snapshot.children) {
                    val kart = d.getValue(Card::class.java)

                    if (kart != null) {

                        val name = kart.cardName!!
                        val house = kart.cardHouse!!
                        val image = kart.cardImage!!
                        val point = kart.cardPoint!!

                        liste.add(Card(name,house,point,image))

                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        kartList.value=liste

    }


    @RequiresApi(33)
    fun loadCards() {

        val randomList = mutableListOf<Card>()
        var randomList0 = MutableLiveData<List<Card>>()

       // Log.e("zorluk $zorlukSeviyesi","girmedi")


        if(zorlukSeviyesi==1){                         //  2*2 ZORLUK SEVİYESİ
            count = 4
            column = 2
            zorluk = "2*2"
            cardSize = 150

            //Log.e("zorluk ","$zorlukSeviyesi")

            var aile = ""
            var g=0
            var s=0
            var h=0
            var r=0

            while(true){

                val rnd = (0..21).shuffled().last()

                //Log.e("rndom","$rnd")
                var kart = kartList.value!![rnd]



                aile = kart.cardHouse!!
                //Log.e("aile","${kart.cardHouse}")

                if(aile=="Gryffindor" && g==0){
                    randomList.add(kartList.value!![rnd])
                    val isim = kartList.value!![rnd].cardName
                    val house = kartList.value!![rnd].cardHouse
                    val point = kartList.value!![rnd].cardPoint
                    val image = kartList.value!![rnd].cardImage

                    val crd = Card(isim,house,point,image)
                    crd.id= "${kartList.value!![rnd].id}+1"

                    randomList.add(crd)
                    g++
                }else if(aile=="Slytherin" && s==0){
                    randomList.add(kartList.value!![rnd])

                    val isim = kartList.value!![rnd].cardName
                    val house = kartList.value!![rnd].cardHouse
                    val point = kartList.value!![rnd].cardPoint
                    val image = kartList.value!![rnd].cardImage

                    val crd = Card(isim,house,point,image)
                    crd.id= "${kartList.value!![rnd].id}+1"

                    randomList.add(crd)

                    //randomList.add(kartList.value!![rnd])
                    s++
                }else if(g==1 && s==1){
                    break
                }
                /*else if(aile=="Hufflepuff" && h==0){
                    randomList.add(kartList.value!![rnd])

                    val isim = kartList.value!![rnd].cardName
                    val house = kartList.value!![rnd].cardHouse
                    val point = kartList.value!![rnd].cardPoint
                    val image = kartList.value!![rnd].cardImage

                    val crd = Card(isim,house,point,image)
                    crd.id= "${kartList.value!![rnd].id}+1"

                    randomList.add(crd)

                    //randomList.add(kartList.value!![rnd])
                    h++
                }else if(aile=="Ravenclaw" && r==0){
                    randomList.add(kartList.value!![rnd])

                    val isim = kartList.value!![rnd].cardName
                    val house = kartList.value!![rnd].cardHouse
                    val point = kartList.value!![rnd].cardPoint
                    val image = kartList.value!![rnd].cardImage

                    val crd = Card(isim,house,point,image)
                    crd.id= "${kartList.value!![rnd].id}+1"

                    randomList.add(crd)

                   // randomList.add(kartList.value!![rnd])
                    r++
                }*/

            }


        }else if(zorlukSeviyesi==2){                      //  4*4 ZORLUK SEVİYESİ
            count = 16
            column = 4
            zorluk = "4*4"
            cardSize = 100

           // Log.e("zorluk ","$zorlukSeviyesi")

            var aile = ""
            var g=0
            var s=0
            var h=0
            var r=0


            val dizi = mutableListOf<Int>()
            for (i in 0..43){
                dizi.add(i)
                println(dizi[i])
            }

            for(i in 0..15){

                var rnd = dizi.shuffled().last()
                dizi.remove(rnd)

                var kart = kartList.value!![rnd]

                aile = kart.cardHouse!!

                if(aile=="Gryffindor" && g!=2){
                    randomList.add(kartList.value!![rnd])
                    val isim = kartList.value!![rnd].cardName
                    val house = kartList.value!![rnd].cardHouse
                    val point = kartList.value!![rnd].cardPoint
                    val image = kartList.value!![rnd].cardImage

                    val crd = Card(isim,house,point,image)
                    crd.id= "${kartList.value!![rnd].id}+1"

                    randomList.add(crd)
                    g++
                }else if(aile=="Slytherin" && s!=2){
                    randomList.add(kartList.value!![rnd])
                    val isim = kartList.value!![rnd].cardName
                    val house = kartList.value!![rnd].cardHouse
                    val point = kartList.value!![rnd].cardPoint
                    val image = kartList.value!![rnd].cardImage

                    val crd = Card(isim,house,point,image)
                    crd.id= "${kartList.value!![rnd].id}+1"

                    randomList.add(crd)
                    s++
                }else if(aile=="Hufflepuff" && h!=2){
                    randomList.add(kartList.value!![rnd])
                    val isim = kartList.value!![rnd].cardName
                    val house = kartList.value!![rnd].cardHouse
                    val point = kartList.value!![rnd].cardPoint
                    val image = kartList.value!![rnd].cardImage

                    val crd = Card(isim,house,point,image)
                    crd.id= "${kartList.value!![rnd].id}+1"

                    randomList.add(crd)
                    h++
                }else if(aile=="Ravenclaw" && r!=2){
                    randomList.add(kartList.value!![rnd])
                    val isim = kartList.value!![rnd].cardName
                    val house = kartList.value!![rnd].cardHouse
                    val point = kartList.value!![rnd].cardPoint
                    val image = kartList.value!![rnd].cardImage

                    val crd = Card(isim,house,point,image)
                    crd.id= "${kartList.value!![rnd].id}+1"

                    randomList.add(crd)
                    r++
                }else if(g==2 && r==2 && s==2 && h==2){
                    break
                }
            }

        }else if (zorlukSeviyesi==3){                           //  6*6 ZORLUK SEVİYESİ
            count = 36
            column = 6
            zorluk = "6*6"
            cardSize = 75

            //Log.e("zorluk ","$zorlukSeviyesi")

            var aile = ""
            var g=0
            var s=0
            var h=0
            var r=0

            val dizi = mutableListOf<Int>()
            for (i in 0..43){
                dizi.add(i)
                println(dizi[i])
            }


            for(i in 0..35){

                val rsnd = (0..43).random()


                var rnd = dizi.shuffled().last()

                dizi.remove(rnd)

               // Log.e("Dizi gelen","$rnd")
                //Log.e("Random","$rnd")


                var kart = kartList.value!![rnd]

                aile = kart.cardHouse!!

                if(aile=="Gryffindor" && g!=5){
                    randomList.add(kartList.value!![rnd])
                    val isim = kartList.value!![rnd].cardName
                    val house = kartList.value!![rnd].cardHouse
                    val point = kartList.value!![rnd].cardPoint
                    val image = kartList.value!![rnd].cardImage

                    val crd = Card(isim,house,point,image)
                    crd.id= "${kartList.value!![rnd].id}+1"

                    randomList.add(crd)
                    g++
                }else if(aile=="Slytherin" && s!=5){
                    randomList.add(kartList.value!![rnd])
                    val isim = kartList.value!![rnd].cardName
                    val house = kartList.value!![rnd].cardHouse
                    val point = kartList.value!![rnd].cardPoint
                    val image = kartList.value!![rnd].cardImage

                    val crd = Card(isim,house,point,image)
                    crd.id= "${kartList.value!![rnd].id}+1"

                    randomList.add(crd)
                    s++
                }else if(aile=="Hufflepuff" && h!=4){
                    randomList.add(kartList.value!![rnd])
                    val isim = kartList.value!![rnd].cardName
                    val house = kartList.value!![rnd].cardHouse
                    val point = kartList.value!![rnd].cardPoint
                    val image = kartList.value!![rnd].cardImage

                    val crd = Card(isim,house,point,image)
                    crd.id= "${kartList.value!![rnd].id}+1"

                    randomList.add(crd)
                    h++
                }else if(aile=="Ravenclaw" && r!=4){
                    randomList.add(kartList.value!![rnd])
                    val isim = kartList.value!![rnd].cardName
                    val house = kartList.value!![rnd].cardHouse
                    val point = kartList.value!![rnd].cardPoint
                    val image = kartList.value!![rnd].cardImage

                    val crd = Card(isim,house,point,image)
                    crd.id= "${kartList.value!![rnd].id}+1"

                    randomList.add(crd)
                    r++
                }else if(g==5 && r==5 && s==4 && h==4){
                    break
                }
            }


        }

        //Log.e("kartlist","${randomList.count()}")

        cards.value=randomList.apply { shuffle() }

        val file = "cardGrid.text"
        val fileOutputStream:FileOutputStream
        val text = "aaasdffögldö"

        //val fout = FileOutputStream("cardGrd.txt", true)

        val str = "abcd"

        val dosya = File("cardGrid.txt")
        try {
            dosya.createNewFile()
            val fos = FileOutputStream(dosya)
            val osw = OutputStreamWriter(fos)


            osw.append(cards.value!!.toString())


            osw.close()
            fos.close()

        }catch(hata:Exception){
            Log.e("hata","${hata.message}")
        }






        /*
        val fileName = "src/cardGrid.txt"
        val myfile = File(fileName)

        myfile.printWriter().use { out ->

            out.println("First line")
            out.println("Second line")
        }


        val fileName = "cardGrid.txt"
        val myfile = File(fileName)
        for(i in 0..kartList_boyut!!){
            myfile.bufferedWriter().use { out ->
                out.write(cards!!.value.toString())
            }
        }*/


       /*
        Log.e("kartlist 1","${cards.value!![0].id}")
        Log.e("kartlist 2","${cards.value!![1].id}")
        Log.e("kartlist 3","${cards.value!![2].id}")
        Log.e("kartlist 4","${cards.value!![3].id}")


        cards.value = mutableListOf(
            Card("harry","gry",10,"abcd"),
            Card(" peter","gry",15,"aaa"),
            Card("aa","aa",16,"aa"),
            Card("bb","bb",2,"bb"),
            Card("harry","gry",10,"abcd"),
            Card(" peter","gry",15,"aaa"),
            Card("aa","aa",16,"aa"),
            Card("bb","bb",2,"bb"),
            ).apply { shuffle() }*/
    }




    fun updateShowVisibleCard(id: String) {
        val selects: List<Card>? = cards.value?.filter { it -> it.isSelect }
        val selectCount: Int = selects?.size ?: 0
        var nameFind: String = "";
        var cardEslesme: Card? = null
        var cardEslesmeme1: Card? = null
        var cardEslesmeme2: Card? = null
        var houseEslesmeyen1: String = "";
        var nameEslesmeyen2: String = "";
        var pointEslesmeyen1: Int = 1
        var pointEslesmeyen2: Int = 1



        if (selectCount >= 2) {
            val hasSameChar: Boolean = selects!!.get(0).cardName == selects.get(1).cardName
            val hasNoSameChar: Boolean = selects!!.get(0).cardName != selects.get(1).cardName

            if (hasSameChar) {                           // Kart eşleşme durumu
                nameFind = selects.get(0).cardName!!
                cardEslesme = selects.get(0)
            }else if(hasNoSameChar){
                cardEslesmeme1 = selects.get(0)
                cardEslesmeme2 =selects.get(1)
                pointEslesmeyen1 = selects.get(0).cardPoint!!
                nameEslesmeyen2 = selects.get(1).cardName!!
                pointEslesmeyen2 = selects.get(1).cardPoint!!
            }

        }

        var a = 1

        val list: MutableList<Card>? = cards.value?.map { it ->


            if (selectCount >= 2) {
                it.isSelect = false
            }

            if (it.cardName == nameFind) {                               // KART EŞLEŞME DURUMU

                var katSayi=1

                if(cardEslesme?.cardHouse == "Gryffindor" || cardEslesme?.cardHouse == "Slytherin"){
                    katSayi=2
                }

                if (oyuncuSayisi==1){                                  // Tek Oyunculu Oyun için

                    val skr = oyunSkor
                    oyunSkor= (cardEslesme?.cardPoint!!*katSayi)+skr   //  2 kart için otomatik 2 kat arttırıyor

                }else if(oyuncuSayisi==2){                             // İki Oyunculu Oyun için

                    if(player.value == 1){

                        val skr = oyunSkor1
                        oyunSkor1= (cardEslesme?.cardPoint!!*katSayi)+skr
                        sira.value = "1. Oyuncunun sırası"

                    }else if(player.value == 2){

                        val skr = oyunSkor2
                        oyunSkor2= (cardEslesme?.cardPoint!!*katSayi)+skr
                        sira.value = "2. Oyuncunun sırası"

                    }

                    Log.e("player eşleşme","$player")



                }
                it.isVisible = false


            }else if(it.cardName==nameEslesmeyen2 && selectCount == 2 && a==1){        // KART EŞLEŞMEME


                var katSayi1=1
                var katSayi2=1

                if(cardEslesmeme1?.cardHouse == "Gryffindor" || cardEslesmeme1?.cardHouse == "Slytherin"){
                    katSayi1=2
                }
                if(cardEslesmeme2?.cardHouse == "Gryffindor" || cardEslesmeme2?.cardHouse == "Slytherin"){
                    katSayi2=2
                }



                if (oyuncuSayisi==1){                                  // Tek Oyunculu Oyun için

                    if (cardEslesmeme1?.cardHouse ==cardEslesmeme2?.cardHouse){

                        val skr = oyunSkor
                        val puan1 = pointEslesmeyen1.toFloat()
                        val puan2 = pointEslesmeyen2.toFloat()
                        val carpim = puan1 * puan2
                        oyunSkor= skr-(carpim)/katSayi1

                    }else{
                        val skr = oyunSkor
                        val puan1 = pointEslesmeyen1.toFloat()
                        val puan2 = pointEslesmeyen2.toFloat()
                        val ort = (puan1 + puan2) / 2
                        oyunSkor= skr-ort*katSayi1*katSayi2

                    }

                }else if(oyuncuSayisi==2){                             // İki Oyunculu Oyun için

                    Log.e("player","$player")

                    if(player.value == 1){

                        if (cardEslesmeme1?.cardHouse ==cardEslesmeme2?.cardHouse){

                            val skr = oyunSkor1
                            val puan1 = pointEslesmeyen1.toFloat()
                            val puan2 = pointEslesmeyen2.toFloat()
                            val carpim = puan1 * puan2
                            oyunSkor1= skr-(carpim)/katSayi1

                        }else{
                            val skr = oyunSkor1
                            val puan1 = pointEslesmeyen1.toFloat()
                            val puan2 = pointEslesmeyen2.toFloat()
                            val ort = (puan1 + puan2) / 2
                            oyunSkor1= skr-ort*katSayi1*katSayi2

                        }

                        player.value += 1
                        sira.value = "2. Oyuncunun sırası"


                    }else if(player.value == 2){

                        if (cardEslesmeme1?.cardHouse ==cardEslesmeme2?.cardHouse){

                            val skr = oyunSkor2
                            val puan1 = pointEslesmeyen1.toFloat()
                            val puan2 = pointEslesmeyen2.toFloat()
                            val carpim = puan1 * puan2
                            oyunSkor2= skr-(carpim)/katSayi1

                        }else{
                            val skr = oyunSkor2
                            val puan1 = pointEslesmeyen1.toFloat()
                            val puan2 = pointEslesmeyen2.toFloat()
                            val ort = (puan1 + puan2) / 2
                            oyunSkor2= skr-ort*katSayi1*katSayi2

                        }

                        player.value -= 1
                        sira.value = "1. Oyuncunun sırası"

                    }

                    Log.e("player eşleşmeme","$player")
                    a+=1

                }

                Log.e("player cıkış","${player.value}")

            }



            if (it.id == id) {
                it.isSelect = true
                tiklanilanIsım.value = it.cardName.toString()
                tiklanilanPuan.value = it.cardPoint!!
            }


            it
        } as MutableList<Card>?



        val visibleCount: Int = list?.filter { it -> it.isVisible }?.size ?: 0
        if (visibleCount <= 0) {
            //loadCards()
            oyunBittiMi.value=true
            return
        }


        cards.value?.removeAll { true }
        cards.value = list
    }
}



