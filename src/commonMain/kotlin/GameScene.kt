import korlibs.audio.sound.*
import korlibs.image.color.*
import korlibs.io.file.std.*
import korlibs.korge.*
import korlibs.korge.input.*
import korlibs.korge.scene.*
import korlibs.korge.ui.*
import korlibs.korge.view.*
import korlibs.math.geom.*

suspend fun window() = Korge(windowSize = Size(100, 400), backgroundColor = Colors["#FFFFFF"], title = "Click Cat") {
    val sceneContainer = sceneContainer()

    sceneContainer.changeTo{ GameScene() }
}

class GameScene() : Scene() {
    override suspend fun SContainer.sceneInit() {

    }
    override suspend fun SContainer.sceneMain() {
        // @TODO: Main scene code here (after sceneInit)
        val QuitButton = uiButton {

            onClick { println(
                message = "the game didn't quit LOL" +
                "\nwell if you can even call this stupid piece of code a game"
            ) }
        }
        QuitButton.text = "quit game"
        val sound = resourcesVfs["Track.mp3"].readMusic()
        sound.play(infinitePlaybackTimes)
    }

}
