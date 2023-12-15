import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import kotlinx.serialization.Serializable

class RootComponent(componentContext: ComponentContext) :ComponentContext by componentContext{
    private val navigation= StackNavigation<Configuration>()
val childStack=childStack(
    source = navigation,
    serializer = Configuration.serializer(),
    initialConfiguration = Configuration.ScreenA,
    handleBackButton = true,
    childFactory = ::createChild
)
    private fun createChild(config:Configuration,context: ComponentContext):Child{
return when(config){
    Configuration.ScreenA->Child.ScreenA(
        ScreenAComponent(componentContext = context,
            onNavigateToScreenB = {text->
navigation.push(Configuration.ScreenB(text))
            })
    )
    is Configuration.ScreenB->Child.ScreenB(
        ScreenBComponent(config.text,context)
    )
}
    }
    sealed class Child{
        data class ScreenA(val component:ScreenAComponent):Child()
        data class ScreenB(val component:ScreenBComponent):Child()
    }
   @Serializable
    sealed class Configuration{
        data object ScreenA: Configuration()
        data class ScreenB(val text:String): Configuration()
    }
}