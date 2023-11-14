import korlibs.korge.scene.*
import korlibs.korge.ui.*
import korlibs.korge.view.*

class GameScene() : Scene() {
    override suspend fun SContainer.sceneInit() {

    }
    override suspend fun SContainer.sceneMain() {
        // @TODO: Main scene code here (after sceneInit)
        val QuitButton = uiButton {"hello"}
    }
}
