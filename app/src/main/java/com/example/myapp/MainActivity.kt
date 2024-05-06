package com.example.myapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentSender
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Base64
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.createBitmap
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgs
import com.example.myapp.ui.theme.MyAppTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val viewModel: EmojiViewModel by viewModels()

    @RequiresApi(33)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.kartAl()

        setContent {
            MyAppTheme {
                Pages()
            }
        }
    }

    @RequiresApi(33)
    @Composable
    fun Pages() {

        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "loginPage"){
            composable("homePage"){
                MainContent(navController = navController)
            }
            composable("loginPage"){
                LoginPage(navController = navController)
            }
            composable("registerPage"){
                RegisterPage(navController = navController)
            }
            composable("resetPassPage"){
                ResetPasswordPage(navController = navController)
            }
            composable("selectPage"){
                SelectPage(navController = navController)
            }
            composable("selectPage2"){
                SelectPage2(navController = navController)
            }

        }


    }



    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    private fun MainContent(navController: NavController) {

        val mContext = LocalContext.current
        val mMediaPlayer = MediaPlayer.create(mContext, R.raw.ana)
        mMediaPlayer.start()

        Scaffold(


        ) {
            val cards: List<Card> by viewModel.getCards().observeAsState(listOf())
            CardsGrid(cards = cards)
        }
    }

    var sureBittiMi = mutableStateOf(false)

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun CardsGrid(cards: List<Card>) {

        val time:Long
        if(oyuncuSayisi==1){
            time = 45
        }else{
            time = 60
        }

        var countDownTimer :CountDownTimer

        val timedata= remember {
            mutableStateOf(time)
        }

        countDownTimer = object :CountDownTimer(time*1000,1000){
            override fun onTick(millisUntilFinished: Long) {
                timedata.value=millisUntilFinished/1000
                //Log.e("time","${timedata.value}")

            }

            override fun onFinish() {
                timedata.value=0
                sureBittiMi.value=true
            }
        }
        DisposableEffect(key1 = "key"){
            countDownTimer.start()
            onDispose {
                countDownTimer.cancel()
            }
        }




        Scaffold {

            Column(
                modifier = Modifier
                    .background(color = colorResource(id = R.color.loginBackground))
                    .fillMaxSize(),
                verticalArrangement =   Arrangement.Top,

                ){


                Text(text = "$oyuncu  $zorluk zorluk seviyesi", color = Color.Red, fontFamily = firaSansFamily, fontWeight = FontWeight.Normal , fontSize = 30.sp,modifier = Modifier.padding(15.dp,15.dp,5.dp,8.dp))
                Divider(color = Color.Red, thickness = 3.dp)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(text = "Kalan Sure : ${timedata.value} sn", fontFamily = firaSansFamily, fontWeight = FontWeight.Normal , fontSize = 30.sp,modifier = Modifier.padding(10.dp,10.dp,5.dp,10.dp))

                }


                LazyVerticalGrid(
                    columns = GridCells.Fixed(count = column),
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier
                        .padding(5.dp)
                        .border(
                            5.dp,
                            Color.Black,
                            shape = RoundedCornerShape(corner = CornerSize(5.dp))
                        )
                        .background(color = colorResource(id = R.color.loginBackground))
                ) {
                    items(cards.count()) { cardIndex ->
                        CardItem(cards[cardIndex])
                    }
                }
                // Log.e("col", "$column")

                if (oyuncuSayisi==1){

                    Text(text = "Skor : $oyunSkor", fontFamily = firaSansFamily, fontWeight = FontWeight.Normal , fontSize = 30.sp,modifier = Modifier.padding(15.dp,15.dp,5.dp,8.dp))
                    Text(text = "Tiklanilan : ${tiklanilanIsım.value}", fontFamily = firaSansFamily, fontWeight = FontWeight.Normal , fontSize = 20.sp,modifier = Modifier.padding(10.dp,10.dp,5.dp,1.dp))
                    Text(text = "TiklanilanPuan : ${tiklanilanPuan.value}", fontFamily = firaSansFamily, fontWeight = FontWeight.Normal , fontSize = 20.sp,modifier = Modifier.padding(10.dp,10.dp,5.dp,10.dp))

                }else if(oyuncuSayisi==2){

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text(text = "1. Oyuncu Skor : $oyunSkor1", fontFamily = firaSansFamily, fontWeight = FontWeight.Normal , fontSize = 22.sp,modifier = Modifier.padding(7.dp,7.dp,5.dp,7.dp))
                        Text(text = "VS",color = Color.Red, fontFamily = firaSansFamily, fontWeight = FontWeight.Normal , fontSize = 22.sp,modifier = Modifier.padding(7.dp,7.dp,5.dp,7.dp))
                        Text(text = "2. Oyuncu Skor : $oyunSkor2", fontFamily = firaSansFamily, fontWeight = FontWeight.Normal , fontSize = 22.sp,modifier = Modifier.padding(7.dp,7.dp,5.dp,7.dp))

                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(text = "Tiklanilan : ${tiklanilanIsım.value}", fontFamily = firaSansFamily, fontWeight = FontWeight.Normal , fontSize = 15.sp,modifier = Modifier.padding(10.dp,10.dp,5.dp,1.dp))
                            Text(text = "TiklanilanPuan : ${tiklanilanPuan.value}", fontFamily = firaSansFamily, fontWeight = FontWeight.Normal , fontSize = 15.sp,modifier = Modifier.padding(10.dp,10.dp,5.dp,10.dp))

                        }
                        if (oyuncuSayisi==2){

                            Box(modifier = Modifier
                                .padding(3.dp)
                                .border(
                                    1.dp,
                                    Color.Red,
                                    shape = RoundedCornerShape(corner = CornerSize(3.dp))
                                )) {
                                Text(text = "${sira.value}", fontFamily = firaSansFamily, fontWeight = FontWeight.Normal , fontSize = 25.sp,modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 10.dp))
                            }

                           }

                    }


                }

                if (oyunBittiMi.value){


                    val vContext = LocalContext.current
                    val vMediaPlayer = MediaPlayer.create(vContext, R.raw.congratulations)
                    val sMediaPlayer = MediaPlayer.create(vContext, R.raw.shocked)


                    var sonuc = ""

                    countDownTimer.cancel()

                    if (oyuncuSayisi==1){
                        if(oyunSkor>0){
                            sonuc="Tebrikler Oyunu kazandınız. :)  Puan->$oyunSkor"
                            vMediaPlayer.start()
                        }else{
                            sonuc="Game Over. :(  Puan->$oyunSkor"
                            sMediaPlayer.start()
                        }
                    }else{
                        if(oyunSkor1 > oyunSkor2){
                            sonuc="1.Oyuncu oyunu kazandı. Tebrikler.  Puan->$oyunSkor1"
                            vMediaPlayer.start()
                        }else if(oyunSkor1 < oyunSkor2){
                            sonuc="2.Oyuncu oyunu kazandı. Tebrikler.  Puan->$oyunSkor2"
                            vMediaPlayer.start()
                        }else if(oyunSkor1 == oyunSkor2){
                            sonuc="Berabere.  Puan->$oyunSkor1"
                            vMediaPlayer.start()
                        }else{
                            sonuc="Game Over. :(  Puan->$oyunSkor"
                            sMediaPlayer.start()
                        }
                    }

                    AlertDialog(
                        onDismissRequest = {
                            oyunBittiMi.value = false },
                        title = { Text(text = "Oyun Bitti")},
                        text = { Text(text = "$sonuc") },
                        confirmButton = { Button(onClick = {
                            oyunSkor=0.0
                            oyunSkor1=0.0
                            oyunSkor2=0.0
                            oyunBittiMi.value = false
                            vMediaPlayer.stop()
                            sMediaPlayer.stop()
                        }) {
                            Text(text = "TAMAM")
                        }}
                    )

                }
                if (sureBittiMi.value){

                    val vContext = LocalContext.current
                    val vMediaPlayer = MediaPlayer.create(vContext, R.raw.congratulations)
                    val sMediaPlayer = MediaPlayer.create(vContext, R.raw.shocked)

                    var sonuc = ""

                    if (oyuncuSayisi==1){
                        if(oyunSkor>0){
                            sonuc="Tebrikler Oyunu kazandınız. :)  Puan->$oyunSkor"
                            vMediaPlayer.start()
                        }else{
                            sonuc="Game Over. :(  Puan->$oyunSkor"
                            sMediaPlayer.start()
                        }
                    }else{
                        if(oyunSkor1 > oyunSkor2){
                            sonuc="1.Oyuncu oyunu kazandı. Tebrikler.  Puan->$oyunSkor1"
                            vMediaPlayer.start()
                        }else if(oyunSkor1 < oyunSkor2){
                            sonuc="2.Oyuncu oyunu kazandı. Tebrikler.  Puan->$oyunSkor2"
                            vMediaPlayer.start()
                        }else if(oyunSkor1 == oyunSkor2){
                            sonuc="Berabere.  Puan->$oyunSkor1"
                            vMediaPlayer.start()
                        }else{
                            sonuc="Game Over. :(  Puan->$oyunSkor"
                            sMediaPlayer.start()
                        }
                    }

                    AlertDialog(
                        onDismissRequest = {
                            oyunBittiMi.value = false },
                        title = { Text(text = "Sure Bitti")},
                        text = { Text(text = "$sonuc") },
                        confirmButton = { Button(onClick = {
                            oyunSkor=0.0
                            oyunSkor1=0.0
                            oyunSkor2=0.0
                            sureBittiMi.value = false
                            vMediaPlayer.stop()
                            sMediaPlayer.stop()
                        }) {
                            Text(text = "TAMAM")
                        }}
                    )

                }



            }
                

        }

    }






    @Composable
    private fun CardItem(kart: Card) {

        val imageBytes = Base64.decode(kart.cardImage!!, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
       // imageView.setImageBitmap(decodedImage)


        Card(
            modifier = Modifier
                .padding(all = 7.dp)
                .background(color = colorResource(id = R.color.loginBackground))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .size(cardSize.dp)
                    .background(
                        color = Color.White.copy(alpha = if (kart.isVisible) 0.4F else 0.0F),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        if (kart.isVisible) {
                            viewModel.updateShowVisibleCard(kart.id)
                        }
                    }

            ){
                if (kart.isSelect) {
                   // Text(text = kart.cardName!!)

                    var img :String = kart.cardImage!!

                    val urlImg = img

                    val decodeBitmap = urlImg.toBitmap()

                    val imgBit = decodeBitmap.asImageBitmap()

                    Image(bitmap = imgBit, contentDescription = "")

                    // Image(bitmap = decodedImage.asImageBitmap(), contentDescription = "some useful description",)


                }else if(kart.isVisible){
                    Image(painter = painterResource(id = R.drawable.hp_c), contentDescription = "")
                }
            }
        }
    }

    fun String.toBitmap():Bitmap{

        Base64.decode(this,Base64.DEFAULT).apply {
            return BitmapFactory.decodeByteArray(this,0,size)
        }

    }


    @Preview(showBackground = true)
    @Composable
    private fun DefaultPreview() {
        MyAppTheme {

        }
    }



    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun LoginPage(navController: NavController) {               // *************** LOGİN SAYFASI

        var scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()

        val tfKullaniciAd = remember { mutableStateOf("") }
        val tfSifre = remember { mutableStateOf("") }

        var kullaniciAdi = ""
        var sifre = ""


        val db = FirebaseDatabase.getInstance()
        var refKisi = db.getReference("kisiler")


        kullaniciAdi = tfKullaniciAd.value
        sifre = tfSifre.value

        var kontrol=1

        val sorgu = refKisi.orderByChild("kullanici_ad").equalTo(kullaniciAdi)
        val sorgu2 = refKisi.orderByChild("sifre").equalTo(sifre)

        sorgu.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (d in snapshot.children) {
                    val kisi = d.getValue(Kisiler::class.java)

                    if (kisi != null) {

                        sorgu2.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for (dS in snapshot.children) {
                                    val Dsifre = dS.getValue(Kisiler::class.java)

                                    if (Dsifre != null && Dsifre.sifre == sifre) {

                                        kontrol=0

                                    }
                                }
                            }
                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }
                        })



                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })



        Scaffold(

            scaffoldState = scaffoldState,

            content = {

                Surface(color = colorResource(id = R.color.loginBackground)) {


                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(colorResource(id = R.color.loginBackground)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {



                        Image(
                            painter = painterResource(id = R.drawable.hogwarts),
                            contentDescription = " ",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(140.dp)
                                .clip(CircleShape) // clip to the circle shape
                                .border(1.dp, Color.Black, CircleShape)//optional
                        )

                        Text(text = "HARRY POTTER", fontFamily = firaSansFamily, fontWeight = FontWeight.Normal , fontSize = 50.sp, modifier = Modifier.padding(5.dp,50.dp,5.dp,5.dp))
                        Text(text = "MEMORY MASTER", fontFamily = firaSansFamily, fontWeight = FontWeight.Normal , fontSize = 50.sp,modifier = Modifier.padding(5.dp,5.dp,5.dp,30.dp))

                        TextField(
                            value = tfKullaniciAd.value,
                            onValueChange = { tfKullaniciAd.value = it },
                            label ={Text(text= "Kullanıcı Adı")},
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.White,
                                textColor = Color.Black,
                                focusedIndicatorColor = colorResource(id = R.color.darkloginBackground)
                            ),
                            modifier = Modifier.padding(10.dp)
                        )

                        TextField(
                            value = tfSifre.value,
                            onValueChange = { tfSifre.value = it },
                            label ={Text(text= "Şifre")},
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.White,
                                textColor = Color.Black,
                                focusedIndicatorColor = colorResource(id = R.color.darkloginBackground)
                            ),
                            modifier = Modifier.padding(5.dp)
                        )


                        Text("                                       Şifremi unuttum.",
                            Modifier
                                .clickable {
                                    navController.navigate("resetPassPage")
                                }
                                .padding(0.dp, 0.dp, 0.dp, 12.dp),
                            color = Color.Red,
                        )


                        Button(

                            onClick = {

                                kullaniciAdi = tfKullaniciAd.value
                                sifre = tfSifre.value

                                if (kullaniciAdi == "" || sifre == "") {
                                    scope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(message = "Eksik bilgiler var. Lütfen kontrol ediniz.")
                                    }
                                }else if(kontrol==0){

                                   // kartEkle("Helga Hufflepuff","Hufflepuff",20,"base64")

                                    scope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(message = "Giriş başarılı.")

                                    }
                                    navController.navigate("selectPage")
                                }else{
                                    //ekle(kullaniciAdi,sifre)
                                    /*kartEkle("Rowena Ravenclaw","Ravenclaw",20,"abcd")
                                    kartEkle("Albus Dumbledore","Gryffindor",20,"abcde")
                                    kartEkle("Severus Snape","Slytherin",18,"abcdef")
                                    kartEkle("Ernest Macmillan","Hufflepuff",5,"abcdefg")*/
                                    scope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(message = "Kullanıcı adı veya şifre hatalı")

                                    }
                                }

                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = colorResource(id = R.color.darkloginBackground),
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .size(250.dp, 75.dp)
                                .padding(10.dp)

                        ) {
                            Text(text = "Giriş Yap")
                        }


                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {

                            Text("Hesabınız yok mu ?",
                                Modifier.padding(5.dp),
                                color = Color.White,
                            )

                            Text("Hemen kaydolun.",
                                Modifier
                                    .clickable {
                                        navController.navigate("registerPage")
                                    }
                                    .padding(5.dp),
                                color = Color.Magenta,
                            )

                        }

                    }
                }
            }
        )

    }


    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun SelectPage(navController: NavController){              // ************** SEÇİM SAYFALARI

        Scaffold(

            content = {

                Surface(color = colorResource(id = R.color.loginBackground)) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(colorResource(id = R.color.loginBackground)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {

                        Text(text = "OYUNCU SAYISI", fontFamily = firaSansFamily, fontWeight = FontWeight.Normal , fontSize = 50.sp, modifier = Modifier.padding(5.dp,50.dp,5.dp,5.dp))

                        Spacer(modifier = Modifier.size(50.dp))


                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {

                            Image(painter = painterResource(id = R.drawable.single), contentDescription = "",
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(0.dp, 0.dp, 20.dp, 0.dp)
                            )

                            Spacer(modifier = Modifier.size(60.dp))

                            Image(painter = painterResource(id = R.drawable.group), contentDescription = "",
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(10.dp, 0.dp, 0.dp, 0.dp)
                            )


                        }


                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {

                            Button(
                                onClick = {
                                    oyuncuSayisi = 1
                                    oyuncu="Tek oyunculu"
                                    oyunSkor = 0.0
                                    navController.navigate("selectPage2")
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = colorResource(id = R.color.darkloginBackground),
                                    contentColor = Color.White
                                ),
                                modifier = Modifier
                                    .size(175.dp, 75.dp)
                                    .padding(10.dp)
                            ) {
                                Text(text = "Tek Oyunculu")
                            }


                            Button(
                                onClick = {
                                    oyuncuSayisi = 2
                                    oyuncu="Çok oyunculu"
                                    oyunSkor = 0.0
                                    navController.navigate("selectPage2")
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = colorResource(id = R.color.darkloginBackground),
                                    contentColor = Color.White
                                ),
                                modifier = Modifier
                                    .size(175.dp, 75.dp)
                                    .padding(10.dp)
                            ) {
                                Text(text = "Çok Oyunculu")
                            }


                        }

                    }
                }
            }
        )
    }


    @RequiresApi(33)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun SelectPage2(navController: NavController){

        Scaffold(

            content = {

                Surface(color = colorResource(id = R.color.loginBackground)) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(colorResource(id = R.color.loginBackground)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {

                        Text(text = "ZORLUK SEVİYESİ", fontFamily = firaSansFamily, fontWeight = FontWeight.Normal , fontSize = 50.sp, modifier = Modifier.padding(5.dp,50.dp,5.dp,5.dp))

                        Spacer(modifier = Modifier.size(50.dp))


                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {

                            Image(painter = painterResource(id = R.drawable.single), contentDescription = "",
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(0.dp, 0.dp, 20.dp, 0.dp)
                            )

                            Button(
                                onClick = {
                                    zorlukSeviyesi = 1
                                    viewModel.loadCards()
                                    navController.navigate("homePage"){
                                        popUpTo("selectPage2"){inclusive=true}
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = colorResource(id = R.color.darkloginBackground),
                                    contentColor = Color.White
                                ),
                                modifier = Modifier
                                    .size(175.dp, 75.dp)
                                    .padding(10.dp)
                            ) {
                                Text(text = "2*2")
                            }

                        }


                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {

                            Image(painter = painterResource(id = R.drawable.single), contentDescription = "",
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(0.dp, 0.dp, 20.dp, 0.dp)
                            )

                            Button(
                                onClick = {
                                    zorlukSeviyesi = 2
                                    viewModel.loadCards()
                                    navController.navigate("homePage"){
                                        popUpTo("selectPage2"){inclusive=true}
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = colorResource(id = R.color.darkloginBackground),
                                    contentColor = Color.White
                                ),
                                modifier = Modifier
                                    .size(175.dp, 75.dp)
                                    .padding(10.dp)
                            ) {
                                Text(text = "4*4")
                            }
                        }



                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {

                            Image(painter = painterResource(id = R.drawable.single), contentDescription = "",
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(0.dp, 0.dp, 20.dp, 0.dp)
                            )

                            Button(
                                onClick = {
                                    zorlukSeviyesi = 3
                                    viewModel.loadCards()
                                    navController.navigate("HomePage"){
                                        popUpTo("selectPage2"){inclusive=true}
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = colorResource(id = R.color.darkloginBackground),
                                    contentColor = Color.White
                                ),
                                modifier = Modifier
                                    .size(175.dp, 75.dp)
                                    .padding(10.dp)
                            ) {
                                Text(text = "6*6")
                            }
                        }


                    }
                }
            }
        )
    }

}

