package com.example.sehatin.view.screen.dashboard.profile

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.back
import com.example.compose.textColor
import com.example.sehatin.R
import com.example.sehatin.common.AccountList
import com.example.sehatin.common.getProfileItems
import com.example.sehatin.data.model.response.Detail
import com.example.sehatin.navigation.DetailDestinations
import com.example.sehatin.navigation.MainDestinations
import com.example.sehatin.navigation.SehatInSurface
import com.example.sehatin.viewmodel.LoginScreenViewModel
import com.example.sehatin.viewmodel.PersonalizeViewModel
import kotlinx.coroutines.delay

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginScreenViewModel,
    personalizeViewModel: PersonalizeViewModel,
    navigateToRoute: (String) -> Unit,
    navigateToMain:(String,Boolean) -> Unit
) {
    val temp = getProfileItems()
    ProfileScreen(
        modifier = modifier, id = 0, data = temp, loginViewModel = loginViewModel,
        navigateToRoute = navigateToRoute, personalizeViewModel = personalizeViewModel,
        navigateToMain = navigateToMain
    )
}

@Composable
private fun ProfileScreen(
    modifier: Modifier = Modifier,
    id: Int = 0,
    data: List<AccountList>,
    loginViewModel: LoginScreenViewModel,
    personalizeViewModel: PersonalizeViewModel,
    navigateToRoute: (String) -> Unit,
    navigateToMain:(String,Boolean) -> Unit
) {

    var profile by remember { mutableStateOf<Detail?>(null) }

    LaunchedEffect(Unit) {
        profile = personalizeViewModel.getUserDetail()
    }


    SehatInSurface(
        modifier = modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .background(back),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(60.dp))
                HeaderSection(
                    profile = profile ?: Detail(
                        birthday = "",
                        goal = "",
                        gender = "",
                        activity = "",
                        bmr = "",
                        weight = "",
                        password = "",
                        verifiedAt = "",
                        name = "",
                        id = "",
                        email = "",
                        height = "",
                        bmi = "",
                        weight_target = "",
                    ),
                )
                Spacer(modifier = Modifier.height(20.dp))
                BodySection(
                    data = data,
                    loginViewModel = loginViewModel,
                    navigateToRoute = navigateToRoute,
                    navigateToMain = navigateToMain

                )
            }
        }
    }
}

@Composable
private fun HeaderSection(
    modifier: Modifier = Modifier,
    profile: Detail
) {

    val genderIcon = when (profile.gender.toString().lowercase()) {
        "male" -> R.drawable.ic_male
        "female" -> R.drawable.ic_female
        else -> R.drawable.ic_male // default/fallback
    }
    Column(
        modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(90.dp) // Ukuran lingkaran luar
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.background), // Warna latar belakang lingkaran
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = genderIcon),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(85.dp) // Ukuran gambar lebih kecil dari lingkaran
//                    .clip(CircleShape)
                    .offset(y = 12.dp)
            )
        }
        Spacer(modifier = Modifier.size(9.dp))
        Text(
            text = profile.name.toString(),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 18.sp,
            color = textColor
        )
        Text(
            text = profile.email,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = Color(0xFF9C9C9C)
        )
    }
}

@Composable
private fun BodySection(
    modifier: Modifier = Modifier, data: List<AccountList>,
    loginViewModel: LoginScreenViewModel,
    navigateToRoute: (String) -> Unit,
    navigateToMain:    (String, Boolean) -> Unit
) {

    val vectorImages = listOf(
        R.drawable.update_data,
        R.drawable.lock,
        R.drawable.logout,
        R.drawable.weight_icon,
        R.drawable.height_icon,
    )

    Column(
        modifier = Modifier
            .padding(horizontal = 21.dp)
            .shadow(elevation = 1.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(horizontal = 18.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {

            item {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(RoundedCornerShape(10.dp))
//                        .background(Color(0xFFF3F3F3))
                        .padding(vertical = 15.dp, horizontal = 15.dp)
                        .clickable { navigateToRoute(DetailDestinations.UPDATE_WEIGHT_ONLY_ROUTE) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = vectorImages[3]),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = "Perbarui Berat Badan",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.height(13.dp))
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(RoundedCornerShape(10.dp))
//                        .background(Color(0xFFF3F3F3))
                        .padding(vertical = 15.dp, horizontal = 15.dp)
                        .clickable { navigateToRoute(DetailDestinations.UPDATE_HEIGHT_ONLY_ROUTE) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = vectorImages[4]),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = "Perbarui Tinggi Badan",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.height(13.dp))
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(RoundedCornerShape(10.dp))
//                        .background(Color(0xFFF3F3F3))
                        .padding(vertical = 15.dp, horizontal = 15.dp)
                        .clickable { navigateToRoute(DetailDestinations.UPDATE_HEIGHT_ROUTE) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = vectorImages[0]),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = "Perbarui Data Anda",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.height(13.dp))
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(RoundedCornerShape(10.dp))
//                        .background(Color(0xFFF3F3F3))
                        .padding(vertical = 15.dp, horizontal = 15.dp)
                        .clickable { navigateToRoute(DetailDestinations.CHANGE_PASSWORD_AUTHED_ROUTE) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = vectorImages[1]),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = "Ganti Kata Sandi",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.height(13.dp))
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.error.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(RoundedCornerShape(10.dp))
//                        .background(Color(0xFFF3F3F3))
                        .padding(vertical = 15.dp, horizontal = 15.dp)
                        .clickable {
                            loginViewModel.logout()
//                            delay(1000)
                            navigateToMain(MainDestinations.LOGIN_ROUTE,true)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = vectorImages[2]),
                        contentDescription = null,
                        tint = Color(0xFFC93E3E),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = "Keluar",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color(0xFFC93E3E)
                    )
                }
            }
        }
    }
}

