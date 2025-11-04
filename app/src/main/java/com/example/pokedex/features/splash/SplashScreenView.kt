import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.navigation.NavHostController
import com.example.pokedex.navigation.DashboardScreen
import com.example.pokedex.navigation.SplashScreen
import kotlinx.coroutines.delay

@Composable
fun SplashScreenView(navController: NavHostController) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
        delay(2000)
        navController.navigate(DashboardScreen.route){
            popUpTo(SplashScreen.route) {inclusive = true}
        }
    }

    // conteúdo da tela
    Text("Hello Pokedex")
}


