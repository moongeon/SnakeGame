package com.mungeun.snakegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mungeun.snakegame.ui.theme.SnakeGameTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SnakeGameTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SelectableDropdownMenu(listOf("1","2"))
                    //SnakeGame()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SnakeGameTheme {
        //SnakeGame()
        SelectableDropdownMenu(listOf("1","2"))
    }
}





@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun SelectableDropdownMenu(names: List<String>){
    var isExpanded  by remember { mutableStateOf(false) }

    val selectedNames = remember { mutableStateListOf<String>() }

    ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = {
        isExpanded = !isExpanded
    }) {
        TextField(value = selectedNames.joinToString(", "),
            onValueChange = {},
            placeholder = { Text(text = "이름 선택")},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
            names.forEach { name ->
            AnimatedContent(targetState = selectedNames.contains(name), label = "") { isSelected ->
                if(isSelected){
                    DropdownMenuItem(text =  { Text(text = name)}, 
                        onClick = {
                        selectedNames.remove(name)
                    },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Check,
                                contentDescription = null
                            )
                        }
                    )
                }else{
                    DropdownMenuItem(
                        text = {
                            Text(text = name)
                        },
                        onClick = {
                            selectedNames.add(name)
                        },
                    )
                }


            }

            }

        }


    }


}

