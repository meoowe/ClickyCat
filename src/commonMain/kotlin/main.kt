
import korlibs.image.color.*
import korlibs.korge.*
import korlibs.korge.scene.*
import korlibs.math.geom.*
import scenes.*


// Entry point of the Korge application
suspend fun main() = Korge(
    // Set the window size to 780x400 pixels
    windowSize = Size(780, 400),
    // Set the background color to a shade of blue (#0063FF)
    backgroundColor = Colors["#0063FF"],
    // Set the window title to "Clicky Cat"
    title = "Clicky Cat"
) {
    // Get a reference to the scene container
    val sceneContainer = sceneContainer()

    // TODO: Make the volume adjustable

    // Change to the TitleScreen scene
    sceneContainer.changeTo { TitleScreen() }
    // If playMusic is true, play the loaded sound infinitely
}

// Initialize the score to 0
var score = 0

// Set playMusic to true by default (music will play)
var playMusic = true

// Initialize hiScore with the current score (0)
var hiScore = score

