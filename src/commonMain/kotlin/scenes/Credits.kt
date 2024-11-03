import korlibs.audio.sound.*
import korlibs.image.color.*
import korlibs.io.file.std.*
import korlibs.korge.input.*
import korlibs.korge.scene.*
import korlibs.korge.ui.*
import korlibs.korge.view.*
import korlibs.korge.view.align.*
import scenes.*

class Credits : Scene() {
    override suspend fun SContainer.sceneMain() {
        val clickSound = resourcesVfs["click.mp3"].readSound()

        createCloudBackground()
        createCreditsScroll()
        createCreditsText()
        createBackButton(clickSound)
    }

    private suspend fun SContainer.createCloudBackground(): Image {
        return image(__KR.KRImg.clouds.read()) {
            anchor(.5, .5)
            scale(1)
            position(400, 200)
        }
    }

    private suspend fun SContainer.createCreditsScroll(): Image {
        return image(__KR.KRImg.credits.read()) {
            scale(0.5)
            centerOnStage()
        }
    }

    private fun SContainer.createCreditsText(): List<Text> {
        return listOf(
            text("Programming - meoowe") {
                positionX(210)
                positionY(125)
                color = Colors.BLACK
                fontSize = 35.0
            },
            text("Art - LeoNunk") {
                positionX(290)
                positionY(155)
                color = Colors.BLACK
                fontSize = 35.0
            }
        )
    }

    private fun SContainer.createBackButton(clickSound: Sound): UIButton {
        return uiButton {
            onClick {
                sceneContainer.changeTo {
                    TitleScreen()
                }
                clickSound.play()
            }
            text = "Back To Game"
            centerXOnStage()
            positionY(225)
            bgColorOut = Colors.ORANGE
            bgColorOver = Colors.YELLOW
            textColor = Colors.BLACK
        }
    }
}
