package com.example.myapp

import android.annotation.SuppressLint
import android.util.Log
import android.view.Surface
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

fun ekle(kullanici_ad:String,email:String,sifre:String){

    val db = FirebaseDatabase.getInstance()
    val refKayit = db.getReference("kisiler")

    val yeniKayit = Kisiler(kullanici_ad,email,sifre)
    refKayit.push().setValue(yeniKayit)

}

/*
fun kartEkle(ad:String,ev:String,puan:Int,resim:String){

    val db = FirebaseDatabase.getInstance()
    val refKayit = db.getReference("kartlar")

    val yeniKayit = Card(ad,ev,puan,resim)
    refKayit.push().setValue(yeniKayit)

}*/



/*
fun kontrol(kullanici_ad:String){

    val db = FirebaseDatabase.getInstance()
    val ref = db.getReference("kisiler")

    ref.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            for (d in snapshot.children) {
                val kisi = d.getValue(Kisiler::class.java)

                if (kisi != null) {

                    if (kisi.kullanici_ad == kullanici_ad){
                        kontrol = 0
                        println("kullanıcı kayıtlı")
                    }
                }

            }
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }

    })

}*/


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RegisterPage(navController: NavController){

    var scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val tfKullaniciAd = remember { mutableStateOf("") }
    val tfSifreTekrar = remember { mutableStateOf("") }
    val tfSifre = remember { mutableStateOf("") }
    val tfEmail = remember { mutableStateOf("") }

    var kullaniciAdi = ""
    var email = ""
    var sifre = ""
    var tekrarSifre = ""


    kullaniciAdi = tfKullaniciAd.value
    sifre = tfSifre.value


    val db = FirebaseDatabase.getInstance()
    var refKisi = db.getReference("kisiler")

    var kontrol=1

    val sorgu = refKisi.orderByChild("kullanici_ad").equalTo(kullaniciAdi)

    sorgu.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            for (d in snapshot.children) {
                val kisi = d.getValue(Kisiler::class.java)

                if (kisi != null) {
                    kontrol=0
                    Log.e("kontrol", "$kontrol")
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


                    Text(text = "KAYIT", fontFamily = firaSansFamily, fontWeight = FontWeight.Normal , fontSize = 50.sp, modifier = Modifier.padding(5.dp,50.dp,5.dp,5.dp))
                    Text(text = "SAYFASI", fontFamily = firaSansFamily, fontWeight = FontWeight.Normal , fontSize = 50.sp,modifier = Modifier.padding(5.dp,5.dp,5.dp,30.dp))


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
                        value = tfEmail.value,
                        onValueChange = { tfEmail.value = it },
                        label ={Text(text= "Email Adresi")},
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            textColor = Color.Black,
                            focusedIndicatorColor = colorResource(id = R.color.darkloginBackground)
                        ),
                        modifier = Modifier.padding(5.dp)
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


                    TextField(
                        value = tfSifreTekrar.value,
                        onValueChange = { tfSifreTekrar.value = it },
                        label ={Text(text= "Şifre Tekrar")},
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            textColor = Color.Black,
                            focusedIndicatorColor = colorResource(id = R.color.darkloginBackground)
                        ),
                        modifier = Modifier.padding(5.dp)
                    )

                    Button(
                        onClick = {

                            kullaniciAdi = tfKullaniciAd.value
                            email = tfEmail.value
                            sifre = tfSifre.value
                            tekrarSifre = tfSifreTekrar.value

                            if (kullaniciAdi == "" || email == "" || sifre == "" || tekrarSifre == "" ) {
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(message = "Eksik bilgiler var. Lütfen kontrol ediniz.")
                                }
                            }else if(sifre != tekrarSifre){
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(message = "Şifreler uyumlu değil.")
                                }
                            }else if(kontrol==0){
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(message = "Kullanici adi zaten kayıtlı.")
                                }
                                println(kontrol)
                            }else{
                                ekle(kullaniciAdi,email,sifre)
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(message = "Kayıt başarılı.")

                                }
                                println(kontrol)
                                navController.navigate("loginPage")
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
                        Text(text = "Kayıt")
                    }

                }
            }
        }
    )
}




@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ResetPasswordPage(navController: NavController){

    var scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val tfKullaniciAd = remember { mutableStateOf("") }
    val tfSifreTekrar = remember { mutableStateOf("") }
    val tfSifre = remember { mutableStateOf("") }

    var kullaniciAdi = ""
    var sifre = ""
    var sifreTekrar = ""

    val db = FirebaseDatabase.getInstance()
    var refKisi = db.getReference("kisiler")


    kullaniciAdi = tfKullaniciAd.value
    sifre = tfSifre.value
    sifreTekrar = tfSifreTekrar.value

    var kontrol=1
    Log.e("tfKullaniciDis","$kullaniciAdi")
    val sorgu = refKisi.orderByChild("kullanici_ad").equalTo(kullaniciAdi)
    val sorgu2 = refKisi.orderByChild("sifre").equalTo(sifre)

    sorgu.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            for (d in snapshot.children) {
                val kisi = d.getValue(Kisiler::class.java)

                if (kisi != null) {



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

                    Text(text = "ŞİFRENİ", fontFamily = firaSansFamily, fontWeight = FontWeight.Normal , fontSize = 50.sp, modifier = Modifier.padding(5.dp,50.dp,5.dp,5.dp))
                    Text(text = "GÜNCELLE", fontFamily = firaSansFamily, fontWeight = FontWeight.Normal , fontSize = 50.sp,modifier = Modifier.padding(5.dp,5.dp,5.dp,30.dp))

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

                    TextField(
                        value = tfSifreTekrar.value,
                        onValueChange = { tfSifreTekrar.value = it },
                        label ={Text(text= "Şifre Tekrar")},
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            textColor = Color.Black,
                            focusedIndicatorColor = colorResource(id = R.color.darkloginBackground)
                        ),
                        modifier = Modifier.padding(5.dp)
                    )

                    Button(
                        onClick = {
                            if (tfKullaniciAd.value == null || tfSifre.value == null || tfSifreTekrar.value == null ) {
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(message = "Eksik bilgiler var. Lütfen kontrol ediniz.")
                                }
                            }else if(tfSifre.value != tfSifreTekrar.value){
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(message = "Şifreler uyumlu değil.")
                                }
                            }else{
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(message = "Kayıt başarılı.")
                                }
                                navController.navigate("loginPage")

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
                        Text(text = "Şifreyi Güncelle")
                    }


                }

            }
        }
    )
}
