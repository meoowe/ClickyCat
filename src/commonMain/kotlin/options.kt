import korlibs.korge.input.*
import korlibs.korge.scene.*
import korlibs.korge.ui.*
import korlibs.korge.view.*
import korlibs.korge.view.align.*
import korlibs.korge.view.onClick

class Options() : Scene() {
    override suspend fun SContainer.sceneMain() {
        // @TODO: Main scene code here (after sceneInit)
        val quitButton = uiButton {

            onClick {
                sceneContainer.changeTo { TitleScreen() }
            }
            text = "Quit To Title"
                centerXOnStage()

        }
        val back = uiButton {
            onClick { sceneContainer.changeTo{GameScene()}}
            text = "Back To Game"
        }



    }
}
