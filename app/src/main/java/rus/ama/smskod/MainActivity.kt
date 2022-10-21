package rus.ama.smskod

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import rus.ama.smskod.ui.theme.SmsKodComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mcrypt = MCrypt()
        val txt ="широта 55.34, longitude 44.22"
        Log.e("txt",txt)
        val encrypted = MCrypt.bytesToHex(mcrypt.encrypt(txt))
        Log.e("encrypted",encrypted.toString())
        val decrypted: String = (mcrypt.decrypt(encrypted)).toString()
        Log.e("decrypted",decrypted.toString())
        setContent {
            SmsKodComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    val count = remember{mutableStateOf(0)}
    Text("Clicks: ${count.value}",
        modifier = Modifier
            .clickable(onClick = { count.value += 1 })
            .background(Color.Yellow)
            .padding(30.dp))

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SmsKodComposeTheme {
        Greeting("Android")
    }
}