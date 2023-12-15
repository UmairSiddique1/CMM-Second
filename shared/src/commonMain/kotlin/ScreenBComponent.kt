import com.arkivanov.decompose.ComponentContext

class ScreenBComponent(val text:String, componentContext: ComponentContext):ComponentContext by componentContext{
}