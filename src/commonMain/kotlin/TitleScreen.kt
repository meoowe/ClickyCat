import korlibs.audio.sound.*
import korlibs.image.color.*
import korlibs.image.font.*
import korlibs.io.file.std.*
import korlibs.korge.input.*
import korlibs.korge.scene.*
import korlibs.korge.ui.*
import korlibs.korge.view.*
import korlibs.korge.view.align.*

class TitleScreen() : Scene() {
    override suspend fun SContainer.sceneInit() {
        val version = "1.1.9-DEV_4.3.2024"
        val verText = text("Version: $version") {stage?.let { alignBottomToBottomOf(it);fontSize = 15.0 }}
        val clickSound = resourcesVfs["click.mp3"].readSound()
        val background = image(KR.img.cover.read()) { anchor(.5, .5);scale(1);position(400, 200) }
        val title = text("Clicky the Cat") { positionX(150);color = Colors.LAWNGREEN;fontSize = 30.0;font = resourcesVfs["PublicPixel.ttf"].readTtfFont() }
        val playButton = uiButton("Play") { position(350, 200) }
        val quitButton = uiButton("Quit to Desktop") { position(350, 250) }
        val creditsButton = uiButton {text = "Credits";stage?.let { alignRightToRightOf(it) };stage?.let { alignBottomToBottomOf(it) } }
        //Below is the main logic
        sceneMain(playButton, clickSound, quitButton, creditsButton)
    }
    suspend fun SContainer.sceneMain(playButton: UIButton,clickSound: Sound, quitButton: UIButton,creditsButton: UIButton) {
        playButton.onClick { sceneContainer.changeTo { GameScene()} }
        playButton.onClick { clickSound.play() }
        quitButton.onClick { gameWindow.close(0)  }
        creditsButton.onClick { sceneContainer.changeTo { Credits() }; clickSound.play() }
    }

}
