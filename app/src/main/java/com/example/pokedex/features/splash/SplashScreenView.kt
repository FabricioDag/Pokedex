import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

import androidx.navigation.NavHostController
import com.example.pokedex.navigation.DashboardScreen
import com.example.pokedex.navigation.SplashScreen
import kotlinx.coroutines.delay

@Composable
fun SplashScreenView(navController: NavHostController) {
    var startAnimation by remember { mutableStateOf(false) }

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    // Anima deslocamento do retângulo
    val topOffset by animateDpAsState(
        targetValue = if (startAnimation) -screenHeight / 2 else 0.dp,
        animationSpec = tween(durationMillis = 1200, easing = FastOutSlowInEasing)
    )

    // Anima deslocamento do retângulo
    val bottomOffset by animateDpAsState(
        targetValue = if (startAnimation) screenHeight / 2 else 0.dp,
        animationSpec = tween(durationMillis = 1200, easing = FastOutSlowInEasing)
    )

    LaunchedEffect(Unit) {
        delay(500)
        startAnimation = true
        delay(1500)
        navController.navigate("dashboard") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1C1C1E))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight / 2)
                .offset(y = topOffset)
                .background(Color(0xFFA61D23))
        )


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight / 2)
                .align(Alignment.BottomCenter)
                .offset(y = bottomOffset)
                .background(Color(0xFFA61D23))
        )

        Text(
            text = "POKÉDEX",
            style = MaterialTheme.typography.headlineLarge.copy(color = Color.White),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


