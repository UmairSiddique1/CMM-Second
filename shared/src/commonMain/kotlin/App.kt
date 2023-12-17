import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.SlideTransition
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {

        }
    }

expect fun getPlatformName(): String
class ScreenA: Screen {
    @Composable
    override fun Content() {
        val navigator= LocalNavigator.currentOrThrow
        Column {
            Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text("Screen A")
                Button(onClick = {
                    navigator.push(ScreenB())
                }) {
                    Text("Go to Screen B")
                }
            }
        }
    }

}

class ScreenB: Screen {
    @Composable
    override fun Content() {
        Text("Screen B")
    }
}