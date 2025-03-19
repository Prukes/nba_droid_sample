package cz.prukes.moneta

import android.os.Bundle
import android.widget.ListView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.prukes.moneta.ui.theme.MonetaNBATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MonetaNBATheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { paddingValue ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(paddingValue)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column{Text(
        text = "Hello $name!",
        modifier = modifier
    )
     Text("Hello",modifier=Modifier.padding(top = 50.dp))}
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MonetaNBATheme {
        Scaffold(modifier = Modifier.fillMaxSize()) {innerPadding -> Greeting("Android",modifier=Modifier.padding(innerPadding)) }
    }
}