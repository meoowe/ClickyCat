package scenes

import Credits
import __KR
import korlibs.audio.sound.*
import korlibs.image.color.*
import korlibs.image.format.*
import korlibs.io.file.std.*
import korlibs.korge.input.*
import korlibs.korge.scene.*
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
        val title = text("Clicky Cat") {
            positionX(150)
            color = Colors.LAWNGREEN
            fontSize = 60.0
            centerOnStage()
        }

        // Add the play button
        val playButton = image(resourcesVfs["img/buttons/playButton.png"].readBitmap()) {
            positionY(210)
            scale(0.125)
            centerXOnStage()
        }

        // Add the quit button
        val quitButton = image(resourcesVfs["img/buttons/exitButton.png"].readBitmap()) {
            positionY( 280)
            scale(0.125)
            centerXOnStage()
        }

        // Add the credit button
        val creditsButton = image(resourcesVfs["img/buttons/creditsButton.png"].readBitmap()) {
            position(655,340)
            scale(0.125)
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
        playButton: Image,
        clickSound: Sound,
        quitButton: Image,
        creditsButton: Image
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
