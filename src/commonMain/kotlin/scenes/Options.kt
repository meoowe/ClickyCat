package scenes

import __KR
import korlibs.audio.sound.*
import korlibs.image.color.*
import korlibs.io.file.std.*
import korlibs.korge.input.*
import korlibs.korge.scene.*
import korlibs.korge.ui.*
import korlibs.korge.view.*
import korlibs.korge.view.align.*

class Options : Scene() {
    override suspend fun SContainer.sceneInit(){
        val clickSound = resourcesVfs["click.mp3"].readSound()
        val cloudBackground = image(__KR.KRImg.clouds.read()) {
            anchor(.5, .5)
            scale(1)
            position(400, 200)
        }
        val scroll = image(__KR.KRImg.scroll.read()) {

            scale(0.5)
                .centerOnStage()
        }
        val quitButton = uiButton {
            text = "Quit To Title"
            centerXOnStage()
            positionY(125)
            bgColorOut = Colors.ORANGE
            bgColorOver = Colors.YELLOW
            textColor = Colors.BLACK
        }
        val credits = text("Made by meoowe and LeoNunk") {
            stage?.let {
                alignBottomToBottomOf(it)
            }
            fontSize = 20.0
        }
        val back = uiButton {
            text = "Back To Game"
            centerXOnStage()
            positionY(225)
            bgColorOut = Colors.ORANGE
            bgColorOver = Colors.YELLOW
            textColor = Colors.BLACK
        }
        val creditsButton = uiButton {
            text = "Credits"
            stage?.let {
                alignRightToRightOf(it)
            }
            centerXOnStage()
            positionY(355)
            bgColorOut = Colors.ORANGE
            bgColorOver = Colors.YELLOW
            textColor = Colors.BLACK

        }
        sceneMain(quitButton, back, creditsButton, clickSound)

    }
    private suspend fun sceneMain(
        quitButton: UIButton,
        back: UIButton,
        creditsButton: UIButton,
        clickSound: Sound
    ) {
        quitButton.onClick {
            sceneContainer.changeTo {
                TitleScreen()
            }
            clickSound.play()
        }
        back.onClick {
            sceneContainer.changeTo {
                GameScene()
            }
            clickSound.play()
        }
        creditsButton.onClick{
            sceneContainer.changeTo {
                Credits()
            }
            clickSound.play()
        }
    }


}
