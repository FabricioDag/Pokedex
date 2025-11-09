import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.models.getTypeColor
import com.example.pokedex.features.dashboard.DashboardViewModel
import androidx.compose.foundation.lazy.itemsIndexed


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreenView(
    navController: NavHostController,
    viewModel: DashboardViewModel = viewModel()
) {
    val state = viewModel.uiState
    val filteredList = viewModel.getFilteredList()

    val searchText by viewModel.searchText.collectAsState()
    val isFilterSheetOpen by viewModel.isFilterSheetOpen.collectAsState()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // MODAL
    if (isFilterSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.toggleFilterSheet(false) },
            sheetState = sheetState
        ) {
            FilterSheetContent(
                onClose = { viewModel.toggleFilterSheet(false) },

                viewModel = viewModel,

                )
        }
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Pokédex",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Use the advanced search to find your favourite Pokémon!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            SearchBar(
                searchText = searchText,
                onTextChange = { viewModel.updateSearchText(it) },
                onSearchClick = { viewModel.performSearch() },
                onFilterClick = { viewModel.toggleFilterSheet(true) }
            )

            //TODO remove this; debug only
            val searchText = viewModel.searchText.value
            Text(text = searchText)

            //Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(filteredList) { pokemon ->
                    PokemonItem(
                        pokemon = pokemon,
                        onClick = { navController.navigate("details/${pokemon.name}") }
                    )

                    //PokemonListItem(name = pokemon.name, url = pokemon.url)
                }


            }

        }


    }
}

@Composable
fun PokemonItem(pokemon: Pokemon, onClick: () -> Unit) {
    val id = pokemon.url
        .trimEnd('/')
        .substringAfterLast('/')

    val imageUrl =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Círculo com sprite do Pokémon
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.Gray.copy(alpha = 0.2f))
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = pokemon.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Nome do Pokémon e ID alinhados horizontalmente
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = pokemon.name.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f) // ocupa todo o espaço até o ID
                )

                Text(
                    text = "#$id",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun SearchBar(
    searchText: String,
    onTextChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    onFilterClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = onTextChange,
            modifier = Modifier.weight(1f),
            placeholder = { Text("Search Pokémon...") },
            singleLine = true
        )

        IconButton(onClick = onSearchClick) {
            Icon(Icons.Default.Search, contentDescription = "Search")
        }

        IconButton(onClick = onFilterClick) {
            Icon(Icons.Default.Menu, contentDescription = "Filters")
        }
    }
}

@Composable
fun FilterSheetContent(
    viewModel: DashboardViewModel,
    onClose: () -> Unit,
) {
    var filterByType by remember { mutableStateOf(false) }
    var filterByGeneration by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(
            text = "Filters",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = filterByType,
                    onCheckedChange = {
                        filterByType = it
                        if (it) filterByGeneration = false // impede dupla seleção
                    }
                )
                Text("Por Tipo")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = filterByGeneration,
                    onCheckedChange = {
                        filterByGeneration = it
                        if (it) filterByType = false
                    }
                )
                Text("Por Geração")
            }
        }


        if (filterByType) {
            Text("Selecione o tipo:")
            val types = listOf(
                "Normal",   // id 1
                "Fighting", // id 2
                "Flying",   // id 3
                "Poison",   // id 4
                "Ground",   // id 5
                "Rock",     // id 6
                "Bug",      // id 7
                "Ghost",    // id 8
                "Steel",    // id 9
                "Fire",     // id 10
                "Water",    // id 11
                "Grass",    // id 12
                "Electric", // id 13
                "Psychic",  // id 14
                "Ice",      // id 15
                "Dragon",   // id 16
                "Dark",     // id 17
                "Fairy",    // id 18
                "Stellar",  // id 19
                "Unknown"   // id 10001
            )

            LazyColumn(modifier = Modifier.height(200.dp)) {
                itemsIndexed(
                    types
                ) { index,type ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                                viewModel.loadPokemonByType(index + 1)
                                onClose()
                            }
                            .padding(vertical = 6.dp)
                    ) {
                        Text(type, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }


        if (filterByGeneration) {
            Text("Selecione a geração:")
            val generations = listOf(
                "Gen I", "Gen II", "Gen III", "Gen IV",
                "Gen V", "Gen VI", "Gen VII", "Gen VIII"
            )

            LazyColumn(modifier = Modifier.height(200.dp)) {
                itemsIndexed(generations) { index,gen ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                                viewModel.loadPokemonByGeneration(index + 1)
                                onClose()
                            }
                            .padding(vertical = 6.dp)
                    ) {
                        Text(gen, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }



        TextButton(onClick = {
            viewModel.loadAllPokemonList() //
            onClose()
        }) {
            Text("Limpar filtros")
        }
    }

}


@Composable
fun TypeSelectionGrid(
    types: List<String>,
    selectedTypes: List<String>,
    onTypeToggle: (String) -> Unit
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
    ) {
        types.forEach { type ->
            val isSelected = selectedTypes.contains(type)
            val backgroundColor = getTypeColor(type)
            val borderColor = if (isSelected) Color.Black else Color.Transparent

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(backgroundColor)
                    .border(2.dp, borderColor, RoundedCornerShape(12.dp))
                    .clickable { onTypeToggle(type) }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = type.replaceFirstChar { it.uppercase() },
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun DropdownMenuField(
    label: String,
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                .clickable { expanded = true }
                .padding(horizontal = 12.dp, vertical = 16.dp)
        ) {
            Text(
                text = selectedOption ?: "Select...",
                color = if (selectedOption != null) Color.Black else Color.Gray
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

