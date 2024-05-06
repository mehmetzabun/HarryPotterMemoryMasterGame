package com.example.myapp

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

var oyuncuSayisi = 0
var zorlukSeviyesi = 0

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SelectPage(navController: NavController){

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
                                oyuncu = "İki Oyunculu"
                                oyunSkor=0.0
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
                                navController.navigate("homePage")
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
                                navController.navigate("homePage")
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
                                navController.navigate("HomePage")
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







