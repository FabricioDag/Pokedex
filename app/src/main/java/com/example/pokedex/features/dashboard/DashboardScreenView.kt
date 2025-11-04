import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.models.PokemonList
import com.example.pokedex.viewModel.MainViewModel

@Composable
fun DashboardScreenView(navController: NavHostController) {

    val mainViewModel =  viewModel<MainViewModel>()
    val state = mainViewModel.state
    // conteúdo da tela
    Scaffold (modifier = Modifier.background(Color.Transparent),
        topBar = {
            TopBar()
        },content = { paddingValues ->
            LazyColumn(Modifier.padding(paddingValues).fillMaxSize().background(Color.Transparent), content = {
                items(state.pokemon.size){
                    PokemonItems(itemIndex = it, pokemonList = state.pokemon, navController= navController)
                }
            })

        }

        )


}

@Composable
fun PokemonItems(itemIndex: Int, pokemonList: List<Pokemon>, navController: NavHostController) {
    Card (
        Modifier.wrapContentSize()
            .padding(10.dp)
            .clickable{
                //navController.navigate("details/${pokemonList[itemIndex].id}")
                navController.navigate("details")
            },
        elevation = CardDefaults.cardElevation(8.dp)
    ){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter){
            Column(
                modifier = Modifier.fillMaxHeight()
                    .background(Color.LightGray.copy(.7f))
                    .padding(6.dp)
            ) {
                Text(text = pokemonList[itemIndex].name, modifier = Modifier.fillMaxWidth()
                    .basicMarquee(),)

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(title = {Text("Pokedex")})
}