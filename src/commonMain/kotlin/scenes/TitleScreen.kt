package scenes

import korlibs.audio.sound.*
import korlibs.image.color.*
import korlibs.io.file.std.*
import korlibs.korge.input.*
import korlibs.korge.scene.*
import korlibs.korge.ui.*
import korlibs.korge.view.*
import korlibs.korge.view.align.*

class TitleScreen : Scene() {
    override suspend fun SContainer.sceneInit() {
        //= Set version text
        val version = "1.3.2-DEV_27.4.2024"
        val verText = text("Version: $version") {
            stage?.let {
                alignBottomToBottomOf(it)
                fontSize = 15.0
            }
        }
        // Load click sound effect from resources
        val clickSound = resourcesVfs["click.mp3"].readSound()

        // Add the cloud background
        val background = image(__KR.KRImg.cover.read()) {
            anchor(.5, .5)
            scale(1)
            position(400, 200)
        }

        // Add the title text
        val title = text("Clicky the Cat") {
            positionX(150)
            color = Colors.LAWNGREEN
            fontSize = 30.0
        }

        // Add the play button
        val playButton = uiButton("Play") {
            position(350, 200)

        }

        // Add the quit button
        val quitButton = uiButton("Quit to Desktop") {
            position(350, 250)
        }

        // Add the credit button
        val creditsButton = uiButton {
            text = "Credits"
            stage?.let {
                alignRightToRightOf(it)
            }
            stage?.let {
                alignBottomToBottomOf(it)
            }
        }

        // Call main scene function
        sceneMain(
            playButton,
            clickSound,
            quitButton,
            creditsButton
        )
    }
    private suspend fun sceneMain(
        playButton: UIButton,
        clickSound: Sound,
        quitButton: UIButton,
        creditsButton: UIButton
    ) {
        // Handle play button click
        playButton.onClick {

            //Change to Scenes.GameScene()
            sceneContainer.changeTo {
                GameScene()
            }
        }

        // Play click sound on play button click
        playButton.onClick {
            clickSound.play()
        }

        //Handle quit button click
        quitButton.onClick {

            //Close the game window
            gameWindow.close(0)
        }

        // Handle credit button click
        creditsButton.onClick {

            //Change to Scenes.Credits()
            sceneContainer.changeTo {
                Credits()
            }
            // Play click sound
            clickSound.play()
        }
    }

}
