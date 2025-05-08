package com.example.sehatin.view.screen.dashboard.detail.diet

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.flowlayout.FlowRow
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.compose.back
import com.example.compose.ter
import com.example.compose.textColor
import com.example.sehatin.R
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.data.model.response.FoodDetailResponse
import com.example.sehatin.navigation.DetailDestinations
import com.example.sehatin.navigation.MainDestinations
import com.example.sehatin.navigation.SehatInSurface
import com.example.sehatin.utils.capitalizeWords
import com.example.sehatin.view.components.CustomButton
import com.example.sehatin.view.components.CustomTopAppBar
import com.example.sehatin.view.components.DynamicDialog
import com.example.sehatin.view.components.HomeSections
import com.example.sehatin.view.components.PortionInput
import com.example.sehatin.viewmodel.DashboardViewModel
import com.example.sehatin.viewmodel.DietViewModel
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun FoodDetail(
    foodId: String,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    dashboardViewModel: DashboardViewModel,
    dietViewModel: DietViewModel,
    navigateToRoute: (String) -> Unit,
) {

    Log.e("FoodDetail", "FoodDetail: $foodId")

    LaunchedEffect(foodId) {
        dashboardViewModel.getFoodDetail(foodId)
        dietViewModel.getOneFoodHistory(foodId)

    }


    FoodDetailContent(
        onBackClick = onBackClick,
        foodId = foodId,
        dietViewModel = dietViewModel,
        dashboardViewModel = dashboardViewModel,
        navigateToRoute = navigateToRoute,
    )

//    when (val state = foodState) {
//        is ResultResponse.Loading -> {
//            Log.e("RESULT", "Loading")
//        }
//
//        is ResultResponse.Success -> {
//        }
//
//        is ResultResponse.Error -> {
//            Log.e("RESULT", "Error: ${state.error}")
//        }
//
//        else -> {
//
//        }
//    }

//    FoodDetail(
//        onBackClick = onBackClick,
//        item = FakeData.foods[0]
//    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FoodDetailContent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    foodId: String,
    dashboardViewModel: DashboardViewModel,
    dietViewModel: DietViewModel,
    navigateToRoute: (String) -> Unit,
) {

    val foodState by dashboardViewModel.foodDetailState.collectAsStateWithLifecycle(initialValue = null)

    val foodDetail by dashboardViewModel.foodDetail.collectAsStateWithLifecycle(initialValue = null)

    val isLoading = foodState is ResultResponse.Loading
//    val isLoading = true


    val foodHistory by dietViewModel.foodHistory.collectAsStateWithLifecycle(initialValue = null)
    val addFoodHistoryState by dietViewModel.createFoodHistoryState.collectAsStateWithLifecycle(
        ResultResponse.None
    )

    val deleteFoodHistoryState by dietViewModel.deleteFoodHistoryState.collectAsStateWithLifecycle(
        ResultResponse.None
    )

    var showCircularProgress by remember { mutableStateOf(false) }

    var showDialog by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }
    var dialogMessage by remember { mutableStateOf("") }
    var isDialogError by remember { mutableStateOf(false) }
    var isDialogSuccess by remember { mutableStateOf(false) }
    var isDialogWarning by remember { mutableStateOf(false) }

    var onDialogConfirm: (() -> Unit)? by remember { mutableStateOf(null) }
    var confirmText: String? by remember { mutableStateOf(null) }
//    var onDialogDismiss: (() -> Unit)? by remember { mutableStateOf(null) }


    LaunchedEffect(Unit) {

        dietViewModel.resetCreateFoodHistoryState()
        dietViewModel.resetDeleteFoodHistoryState()
    }



    LaunchedEffect(addFoodHistoryState) {
        when (addFoodHistoryState) {
            is ResultResponse.Loading -> {
//                Log.e("RESULT", "Loading")
                showCircularProgress = true
            }

            is ResultResponse.Success -> {
//                navigateToRoute("Diet")

                confirmText = null;
                showDialog = true
                dialogTitle = "Berhasil"
                dialogMessage = "Berhasil Menambahkan Makanan"
                isDialogSuccess = true
                isDialogError = false
                delay(2000) // delÏÒay 2 detik
                withContext(Dispatchers.Main) {
                    onBackClick()
                }
            }

            is ResultResponse.Error -> {

                confirmText = null;
                showCircularProgress = false
                showDialog = true
                dialogTitle = "Gagal"
                dialogMessage = "Gagal Menambahkan Makanan"
                isDialogError = true
                isDialogSuccess = false

            }

            else -> {

            }
        }
    }

    LaunchedEffect(deleteFoodHistoryState) {
        when (deleteFoodHistoryState) {
            is ResultResponse.Loading -> {
//                Log.e("RESULT", "Loading")
                showCircularProgress = true
            }

            is ResultResponse.Success -> {
//                navigateToRoute("Diet")
                confirmText = null;

                showDialog = true
                dialogTitle = "Berhasil"
                dialogMessage = "Berhasil Menghapus Makanan"
                isDialogSuccess = true
                isDialogError = false

                delay(2000) // delÏÒay 2 detik
                withContext(Dispatchers.Main) {
                    onBackClick()
                }
            }

            is ResultResponse.Error -> {

                confirmText = null;
                showCircularProgress = false
                showDialog = true
                dialogTitle = "Gagal"
                dialogMessage = "Gagal Menghapus Makanan"
                isDialogError = true
                isDialogSuccess = false

            }

            else -> {

            }
        }
    }


    var porsi by remember { mutableStateOf("0") }

    LaunchedEffect(foodHistory?.data?.serving_amount) {
        foodHistory?.data?.serving_amount?.let {
            porsi = it.toInt().toString()
        }
    }
    val sheetMaxHeight = (getScreenHeightDp() - 100).dp
    val sheetNormalHeight = (getScreenHeightDp() - 300).dp
    var sheetHeight by remember { mutableStateOf(sheetNormalHeight) }
    val animatedSheetHeight by animateDpAsState(
        targetValue = sheetHeight,
        animationSpec = tween(durationMillis = 300),
        label = "sheetAnimation"
    )

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    SehatInSurface(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .imePadding(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(back),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
//                    Image(
//                        painter = painterResource(id = R.drawable.telor), // Ganti jika ingin pakai `item.data.image` dengan `rememberAsyncImagePainter`
//                        contentDescription = "Food Image",
//                        modifier = Modifier.fillMaxWidth(),
//                        contentScale = ContentScale.Crop
//                    )
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(foodDetail?.data?.image)
                            .crossfade(true)
                            .diskCachePolicy(CachePolicy.ENABLED) // Aktifkan disk caching
                            .build(),
                        contentDescription = foodDetail?.data?.name,
                        placeholder = painterResource(id = R.drawable.food_placeholder), // Tambahkan resource placeholder
                        error = painterResource(id = R.drawable.food_placeholder) // Ganti dengan gambar error jika ada

                        ,        // Tambahkan resource error
                        modifier = Modifier
                            .fillMaxWidth()

//                            .height(220.dp), // Opsional: batasi tinggi agar UI stabil saat loading
                        , contentScale = ContentScale.Fit
                    )

                    CustomTopAppBar(
                        title = "",
                        showNavigationIcon = true,
                        modifier = Modifier.statusBarsPadding(),
                        onBackClick = onBackClick
                    )


                    if (isLoading) {

                        Column(
                            verticalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier

                                .fillMaxWidth()
                                .height(animatedSheetHeight)
                                .align(Alignment.BottomCenter)
                                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                                .background(Color.White),
                        ) {

                            Column {

                                Spacer(
                                    modifier = Modifier
                                        .height(20.dp)
                                )
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 32.dp)
                                ) {

                                    Box(
                                        modifier = Modifier
                                            .height(32.dp)
                                            .shimmer()
                                            .width(250.dp)

                                            .background(
                                                Color(0xFF3E7136).copy(alpha = 0.4f),
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                    )
                                    Box(
                                        modifier = Modifier
                                            .height(15.dp)
                                            .shimmer()
                                            .width(100.dp)

                                            .background(
                                                Color(0xFF3E7136).copy(alpha = 0.4f),
                                                shape = RoundedCornerShape(4.dp)
                                            )
                                    )
                                }
                                Spacer(
                                    modifier = Modifier
                                        .height(20.dp)
                                )
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 32.dp)
                                ) {

                                    Box(
                                        modifier = Modifier
                                            .height(20.dp)
                                            .shimmer()
                                            .fillMaxWidth()

                                            .background(
                                                Color(0xFF3E7136).copy(alpha = 0.4f),
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                    )

                                }
                                Spacer(
                                    modifier = Modifier
                                        .height(10.dp)
                                )
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 32.dp)
                                ) {

                                    Box(
                                        modifier = Modifier
                                            .height(20.dp)
                                            .shimmer()
                                            .fillMaxWidth()

                                            .background(
                                                Color(0xFF3E7136).copy(alpha = 0.4f),
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                    )

                                }
                                Spacer(
                                    modifier = Modifier
                                        .height(32.dp)
                                )
                                Column(
//                                horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 32.dp)
                                ) {

                                    Box(
                                        modifier = Modifier
                                            .height(15.dp)
                                            .shimmer()
                                            .fillMaxWidth()

                                            .background(
                                                Color(0xFF3E7136).copy(alpha = 0.4f),
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                    )
                                    Spacer(
                                        modifier = Modifier
                                            .height(10.dp)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .height(15.dp)
                                            .shimmer()
                                            .fillMaxWidth()

                                            .background(
                                                Color(0xFF3E7136).copy(alpha = 0.4f),
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                    )
                                    Spacer(
                                        modifier = Modifier
                                            .height(10.dp)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .height(15.dp)
                                            .shimmer()
                                            .fillMaxWidth()

                                            .background(
                                                Color(0xFF3E7136).copy(alpha = 0.4f),
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                    )
                                    Spacer(
                                        modifier = Modifier
                                            .height(10.dp)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .height(15.dp)
                                            .shimmer()
                                            .fillMaxWidth()

                                            .background(
                                                Color(0xFF3E7136).copy(alpha = 0.4f),
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                    )
                                    Spacer(
                                        modifier = Modifier
                                            .height(10.dp)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .height(15.dp)
                                            .shimmer()
                                            .fillMaxWidth()

                                            .background(
                                                Color(0xFF3E7136).copy(alpha = 0.4f),
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                    )
                                    Spacer(
                                        modifier = Modifier
                                            .height(10.dp)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .height(15.dp)
                                            .shimmer()
                                            .fillMaxWidth()

                                            .background(
                                                Color(0xFF3E7136).copy(alpha = 0.4f),
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                    )
                                    Spacer(
                                        modifier = Modifier
                                            .height(10.dp)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .height(15.dp)
                                            .shimmer()
                                            .fillMaxWidth()

                                            .background(
                                                Color(0xFF3E7136).copy(alpha = 0.4f),
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                    )
                                    Spacer(
                                        modifier = Modifier
                                            .height(10.dp)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .height(15.dp)
                                            .shimmer()
                                            .fillMaxWidth()

                                            .background(
                                                Color(0xFF3E7136).copy(alpha = 0.4f),
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                    )
                                    Spacer(
                                        modifier = Modifier
                                            .height(10.dp)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .height(15.dp)
                                            .shimmer()
                                            .fillMaxWidth()

                                            .background(
                                                Color(0xFF3E7136).copy(alpha = 0.4f),
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                    )

                                }
                                Spacer(
                                    modifier = Modifier
                                        .height(4.dp)
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween ,
//                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()

                                    .padding(horizontal = 32.dp, vertical = 20.dp)
                            ) {

                                Box(
                                    modifier = Modifier
                                        .height(62.dp)
                                        .shimmer()
//                                        .fillMaxWidth()
//                                        .width(.dp)
                                        .weight(0.6f)

                                        .background(
                                            Color(0xFF3E7136).copy(alpha = 0.4f),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                )
                                Spacer(
                                    modifier = Modifier
                                        .width(18.dp)
                                )
                                Box(
                                    modifier = Modifier
                                        .height(62.dp)
                                        .shimmer()
//                                        .fillMaxWidth()
                                        .weight(0.3f)

                                        .background(
                                            Color(0xFF3E7136).copy(alpha = 0.4f),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                )



                            }

                        }
                    } else {

                        Column(

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(animatedSheetHeight)
                                .align(Alignment.BottomCenter)
                                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                                .background(Color.White)
                                .pointerInput(Unit) {
                                    coroutineScope.launch {
                                        detectVerticalDragGestures(
                                            onVerticalDrag = { _, dragAmount ->
                                                sheetHeight =
                                                    (sheetHeight - dragAmount.dp).coerceIn(
                                                        sheetNormalHeight,
                                                        sheetMaxHeight
                                                    )
                                            },
                                            onDragEnd = {
                                                sheetHeight =
                                                    if (sheetHeight > (sheetNormalHeight + sheetMaxHeight) / 2)
                                                        sheetMaxHeight
                                                    else sheetNormalHeight
                                            }
                                        )
                                    }
                                }
                        ) {


                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 21.dp, vertical = 16.dp)
                                    .verticalScroll(scrollState),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
//                            Box(
//                                modifier = Modifier
//                                    .width(60.dp)
//                                    .height(6.dp)
//                                    .background(back, shape = RoundedCornerShape(3.dp))
//                            )

                                Column(

                                    modifier = Modifier.fillMaxSize()


                                ) {

                                    Spacer(modifier = Modifier.height(20.dp))

                                    Row(

                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.Top,
                                        modifier = Modifier.fillMaxWidth()

                                    ) {


                                        Text(
                                            modifier = Modifier.weight(1f),
                                            text = foodDetail?.data?.name?.capitalizeWords()
                                                .toString(),
                                            fontSize = 24.sp,
                                            lineHeight = 32.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )

                                        Row(
                                            horizontalArrangement = Arrangement.End,
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.weight(0.5f)
                                        ) {

                                            Text(
                                                modifier = Modifier,
                                                text = foodDetail?.data?.serving_amount?.toInt()
                                                    .toString(),
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            Text(
                                                modifier = Modifier,
                                                text = " ${
                                                    foodDetail?.data?.serving_unit?.toString()
                                                        ?.capitalizeWords()
                                                }",
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF686868)
                                            )
                                        }


                                    }

                                    Spacer(modifier = Modifier.height(16.dp))

                                    // Nutritional Info Row
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        val nutritionItems = listOf(
                                            Triple(
                                                "Kalori",
                                                foodDetail?.data?.calories?.toInt() ?: 0,
                                                0
                                            ),
                                            Triple(
                                                "Protein",
                                                foodDetail?.data?.protein?.toInt() ?: 0,
                                                1
                                            ),
                                            Triple("Lemak", foodDetail?.data?.fat?.toInt() ?: 0, 2),
                                            Triple(
                                                "Karbo",
                                                foodDetail?.data?.carb?.toInt() ?: 0,
                                                3
                                            ),
                                            Triple(
                                                "Serat",
                                                foodDetail?.data?.fiber?.toInt() ?: 0,
                                                4
                                            )
                                        )

                                        FlowRow(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 0.dp),
                                            mainAxisSpacing = 2.dp,
                                            crossAxisSpacing = 10.dp,
                                            mainAxisAlignment = FlowMainAxisAlignment.Start,
                                            crossAxisAlignment = FlowCrossAxisAlignment.Start
                                        ) {
                                            nutritionItems.forEach { (label, value, position) ->
                                                NutritionInfo(
                                                    units = label,
                                                    weight = value,
                                                    position = position
                                                )
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(20.dp))

                                    val descScroll = rememberScrollState()

                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .heightIn(
                                                min = 200.dp,
                                                max = 250.dp
                                            ) // BERI BATAS TINGGI
                                            .verticalScroll(descScroll)
                                    ) {
                                        Text(
                                            text = foodDetail?.data?.description ?: "",
                                            fontSize = 16.sp,
                                            color = textColor.copy(alpha = 0.7f),
                                            textAlign = TextAlign.Justify,
                                            modifier = Modifier
                                                .fillMaxWidth()
//                                            .padding(bottom = 16.dp)
                                        )
                                    }
                                }


                                Column {


                                    Row(

                                        horizontalArrangement = Arrangement.SpaceBetween,

                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
//                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        PortionInput(
                                            label = foodDetail?.data?.serving_unit ?: "",
                                            value = porsi.toString(),
                                            modifier = Modifier.weight(1f),
                                            onValueChange = { newValue ->
                                                // Pastikan hanya angka yang bisa diinputkan
                                                if (newValue.all { it.isDigit() }) {
                                                    porsi = newValue
                                                }
                                            }
                                        )

                                        Spacer(modifier = Modifier.width(12.dp))

                                        // Label yang menampilkan kata "Porsi"
                                        CustomButton(
                                            modifier = Modifier.weight(0.7f),
                                            defaultHeight = 64.dp,
                                            text = "Tambah",
                                            isAvailable = !showCircularProgress,
                                            fontSize = 18.sp,
                                            onClick = {
                                                if (porsi != "0") {
                                                    isDialogWarning = true
                                                    showDialog = true;
                                                    dialogTitle = "Tambah Makanan"
                                                    dialogMessage =
                                                        "Apakah Anda yakin ingin menambah makanan ini?"
                                                    confirmText = "Tambah"

                                                    onDialogConfirm = {
//                                                showDialog = false
                                                        dietViewModel.createFoodHistory(
                                                            foodDetail?.data?.id ?: "",
                                                            porsi.toDouble()
                                                        )

                                                    }
                                                }


                                            },
//                                    shape = RoundedCornerShape(16.dp)
                                        )

                                    }

                                    if (foodHistory?.data?.serving_amount != null) {

                                        Spacer(
                                            modifier = Modifier.height(16.dp)
                                        )

                                        Row(

                                            horizontalArrangement = Arrangement.SpaceBetween,

                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
//                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {


                                            // Label yang menampilkan kata "Porsi"
                                            CustomButton(
                                                modifier = Modifier.fillMaxWidth(),
                                                backgroundColor = MaterialTheme.colorScheme.error.copy(
                                                    alpha = 0.2f
                                                ),
                                                textColor = MaterialTheme.colorScheme.error,
                                                isOutlined = true,
                                                outlinedColor = MaterialTheme.colorScheme.error,
                                                defaultHeight = 64.dp,
                                                text = "Hapus",
                                                isAvailable = !showCircularProgress,
                                                fontSize = 18.sp,
                                                onClick = {


                                                    isDialogWarning = true
                                                    showDialog = true;
                                                    dialogTitle = "Hapus Makanan"
                                                    dialogMessage =
                                                        "Apakah Anda yakin ingin menghapus makanan ini?"
                                                    confirmText = "Hapus"

                                                    onDialogConfirm = {
//                                                showDialog = false
                                                        dietViewModel.deleteFoodHistory(
                                                            foodDetail?.data?.id ?: "",
                                                        )

                                                    }
                                                },
//                                    shape = RoundedCornerShape(16.dp)
                                            )

                                        }
                                    }


                                }


                            }


                        }
                    }

                }
            }

            if (showDialog) {
                DynamicDialog(
                    title = dialogTitle,
                    message = dialogMessage,
                    onDismiss = { showDialog = false },
                    isError = isDialogError,
                    isSuccess = isDialogSuccess,
                    onConfirm = onDialogConfirm,
                    isWarning = isDialogWarning,
                    confirmText = confirmText
//                isWarning = isDialogWarning,
//                dismissText = "Batal"
                )
            }
            if (showCircularProgress) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.8f))
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}
//
//@Composable
//private fun SkeletonContent() {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Spacer(modifier = Modifier.height(16.dp))
//        ShimmerCard(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp)
//                .clip(RoundedCornerShape(16.dp))
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        ShimmerCard(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp)
//                .clip(RoundedCornerShape(16.dp))
//        )
//    }
//}

@Composable
fun NutritionInfo(
    units: String,
    weight: Int = 0,
    position: Int = 0
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 4.dp)
    )
    {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(ter, RoundedCornerShape(50.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(
                    id = when (position) {
                        0 -> R.drawable.calories
                        1 -> R.drawable.chicken
                        2 -> R.drawable.bubble
                        3 -> R.drawable.sosis
                        else -> R.drawable.bubble
                    }
                ),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp) // Adds spacing between items
        ) {
            Text(
                text = weight.toString() + "g",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50)
            )
            Text(
                text = units,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun getScreenHeightDp(): Int {
    val configuration = LocalConfiguration.current
    return configuration.screenHeightDp
}