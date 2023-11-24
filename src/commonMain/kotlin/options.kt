import korlibs.image.color.Colors.BLACK
import korlibs.image.color.Colors.ORANGE
import korlibs.image.color.Colors.YELLOW
import korlibs.image.format.*
import korlibs.io.file.std.*
import korlibs.korge.input.*
import korlibs.korge.scene.*
import korlibs.korge.ui.*
import korlibs.korge.view.*
import korlibs.korge.view.align.*


class Options() : Scene() {
    override suspend fun SContainer.sceneMain() {
        // @TODO: Main scene code here (after sceneInit)
        val cloudBackground = image(resourcesVfs["img/clouds.png"].readBitmap()) {
            anchor(.5, .5)
            scale(1)
            position(400, 200)
        }
        val scroll = image(resourcesVfs["img/scroll.png"].readBitmap()) {

            scale(0.5)
                .centerOnStage()
        }

        val quitButton = uiButton {

            onClick {
                sceneContainer.changeTo { TitleScreen() }
            }
            text = "Quit To Title"
            centerXOnStage()
            positionY(125)
            bgColorOut = ORANGE
            bgColorOver = YELLOW
            textColor = BLACK

        }
        val back = uiButton {
            onClick { sceneContainer.changeTo{GameScene()}}
            text = "Back To Game"
            centerXOnStage()
            positionY(225)
            bgColorOut = ORANGE
            bgColorOver = YELLOW
            textColor = BLACK
        }
        }



    }

