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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.models.PokemonList
import com.example.pokedex.viewModel.DashboardViewModel
import com.example.pokedex.viewModel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreenView(
    navController: NavHostController,
    viewModel: DashboardViewModel = viewModel()
) {
    val state = viewModel.uiState
    val filteredList = viewModel.getFilteredList()
    var isExpanded by remember { mutableStateOf(false) }
    var isTypeMenuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pokedex!") },
                actions = {
                    // Botão que abre o dropdown de geração
                    Box {
                        IconButton(onClick = { isExpanded = !isExpanded }) {
                            //Icon(Icons.Default.FilterList, contentDescription = "Filtrar")
                            Text("Filter")
                        }
                        DropdownMenu(
                            expanded = isExpanded,
                            onDismissRequest = { isExpanded = false }
                        ) {
                            (1..9).forEach { gen ->
                                DropdownMenuItem(
                                    text = { Text("Geração $gen") },
                                    onClick = {
                                        viewModel.filterByGeneration(gen)
                                        isExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Box{
                        IconButton(onClick = { isExpanded = !isExpanded }) {
                            //Icon(Icons.Default.FilterList, contentDescription = "Filtrar")
                            Text("FilterType")
                        }
                        DropdownMenu(
                            expanded = isTypeMenuExpanded,
                            onDismissRequest = { isTypeMenuExpanded = false }
                        ) {
                            val types = listOf(
                                1 to "Normal",
                                2 to "Fighting",
                                3 to "Flying",
                                4 to "Poison",
                                5 to "Ground",
                                6 to "Rock",
                                7 to "Bug",
                                8 to "Ghost",
                                9 to "Steel",
                                10 to "Fire",
                                11 to "Water",
                                12 to "Grass",
                                13 to "Electric",
                                14 to "Psychic",
                                15 to "Ice",
                                16 to "Dragon",
                                17 to "Dark",
                                18 to "Fairy"
                            )

                            types.forEach { (id, name) ->
                                DropdownMenuItem(
                                    text = { Text(name) },
                                    onClick = {
                                        viewModel.filterByType(id)
                                        isTypeMenuExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(filteredList) { pokemon ->
                PokemonItem(
                    pokemon = pokemon,
                    onClick = { navController.navigate("details/${pokemon.name}") }
                )
            }

            // Quando chegar no final, carrega mais
            item {
                if (!state.isEndReached && !state.isLoading) {
                    LaunchedEffect(Unit) { viewModel.loadPokemonList() }
                }
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }


}

@Composable
fun PokemonItem(pokemon: Pokemon, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.LightGray.copy(alpha = 0.3f))
                .padding(12.dp)
        ) {
            Text(
                text = pokemon.name.replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.basicMarquee()
            )
        }
    }
}

