import korlibs.audio.sound.*
import korlibs.io.file.*
import korlibs.io.file.std.*
import korlibs.image.bitmap.*
import korlibs.image.atlas.*
import korlibs.image.font.*
import korlibs.image.format.*

// AUTO-GENERATED FILE! DO NOT MODIFY!

@Retention(AnnotationRetention.BINARY) annotation class ResourceVfsPath(val path: String)
inline class TypedVfsFile(val __file: VfsFile)
inline class TypedVfsFileTTF(val __file: VfsFile) {
  suspend fun read(): korlibs.image.font.TtfFont = this.__file.readTtfFont()
}
inline class TypedVfsFileBitmap(val __file: VfsFile) {
  suspend fun read(): korlibs.image.bitmap.Bitmap = this.__file.readBitmap()
  suspend fun readSlice(atlas: MutableAtlasUnit? = null, name: String? = null): BmpSlice = this.__file.readBitmapSlice(name, atlas)
}
inline class TypedVfsFileSound(val __file: VfsFile) {
  suspend fun read(): korlibs.audio.sound.Sound = this.__file.readSound()
}
interface TypedAtlas<T>

object KR : __KR.KR

object __KR {
	
	interface KR {
		val __file get() = resourcesVfs[""]
		@ResourceVfsPath("Track.mp3") val `track` get() = TypedVfsFileSound(resourcesVfs["Track.mp3"])
		@ResourceVfsPath("bark.mp3") val `bark` get() = TypedVfsFileSound(resourcesVfs["bark.mp3"])
		@ResourceVfsPath("click.mp3") val `click` get() = TypedVfsFileSound(resourcesVfs["click.mp3"])
		@ResourceVfsPath("img") val `img` get() = __KR.KRImg
		@ResourceVfsPath("music2.mp3") val `music2` get() = TypedVfsFileSound(resourcesVfs["music2.mp3"])
		@ResourceVfsPath("otherTrack.mp3") val `othertrack` get() = TypedVfsFileSound(resourcesVfs["otherTrack.mp3"])
		@ResourceVfsPath("useless") val `useless` get() = TypedVfsFile(resourcesVfs["useless"])
	}
	
	object KRImg {
		val __file get() = resourcesVfs["img"]
		@ResourceVfsPath("img/DogBark.png") val `dogbark` get() = TypedVfsFileBitmap(resourcesVfs["img/DogBark.png"])
		@ResourceVfsPath("img/IMG_0377.png") val `img0377` get() = TypedVfsFileBitmap(resourcesVfs["img/IMG_0377.png"])
		@ResourceVfsPath("img/Lost.png") val `lost` get() = TypedVfsFileBitmap(resourcesVfs["img/Lost.png"])
		@ResourceVfsPath("img/SCROLL.PNG") val `scroll` get() = TypedVfsFileBitmap(resourcesVfs["img/SCROLL.PNG"])
		@ResourceVfsPath("img/Unused_.png") val `unused` get() = TypedVfsFileBitmap(resourcesVfs["img/Unused_.png"])
		@ResourceVfsPath("img/balloon.png") val `balloon` get() = TypedVfsFileBitmap(resourcesVfs["img/balloon.png"])
		@ResourceVfsPath("img/cat.png") val `cat` get() = TypedVfsFileBitmap(resourcesVfs["img/cat.png"])
		@ResourceVfsPath("img/clouds.png") val `clouds` get() = TypedVfsFileBitmap(resourcesVfs["img/clouds.png"])
		@ResourceVfsPath("img/confetti1.png") val `confetti1` get() = TypedVfsFileBitmap(resourcesVfs["img/confetti1.png"])
		@ResourceVfsPath("img/confetti2.png") val `confetti2` get() = TypedVfsFileBitmap(resourcesVfs["img/confetti2.png"])
		@ResourceVfsPath("img/cover.PNG") val `cover` get() = TypedVfsFileBitmap(resourcesVfs["img/cover.PNG"])
		@ResourceVfsPath("img/credits.png") val `credits` get() = TypedVfsFileBitmap(resourcesVfs["img/credits.png"])
		@ResourceVfsPath("img/dog.PNG") val `dog` get() = TypedVfsFileBitmap(resourcesVfs["img/dog.PNG"])
		@ResourceVfsPath("img/dog_barking.png") val `dogBarking` get() = TypedVfsFileBitmap(resourcesVfs["img/dog_barking.png"])
		@ResourceVfsPath("img/grass1.png") val `grass1` get() = TypedVfsFileBitmap(resourcesVfs["img/grass1.png"])
		@ResourceVfsPath("img/grass2.png") val `grass2` get() = TypedVfsFileBitmap(resourcesVfs["img/grass2.png"])
		@ResourceVfsPath("img/grass3.png") val `grass3` get() = TypedVfsFileBitmap(resourcesVfs["img/grass3.png"])
		@ResourceVfsPath("img/house.PNG") val `house` get() = TypedVfsFileBitmap(resourcesVfs["img/house.PNG"])
		@ResourceVfsPath("img/icons") val `icons` get() = __KR.KRImgIcons
		@ResourceVfsPath("img/luna.png") val `luna` get() = TypedVfsFileBitmap(resourcesVfs["img/luna.png"])
		@ResourceVfsPath("img/mountain.png") val `mountain` get() = TypedVfsFileBitmap(resourcesVfs["img/mountain.png"])
		@ResourceVfsPath("img/trophy.png") val `trophy` get() = TypedVfsFileBitmap(resourcesVfs["img/trophy.png"])
	}
	
	object KRImgIcons {
		val __file get() = resourcesVfs["img/icons"]
		@ResourceVfsPath("img/icons/arrow-removebg-preview.png") val `arrowRemovebgPreview` get() = TypedVfsFileBitmap(resourcesVfs["img/icons/arrow-removebg-preview.png"])
		@ResourceVfsPath("img/icons/arrow.jpg") val `arrow` get() = TypedVfsFileBitmap(resourcesVfs["img/icons/arrow.jpg"])
		@ResourceVfsPath("img/icons/door-open-solid [MConverter.eu].jpg") val `doorOpenSolidMconverterEu` get() = TypedVfsFileBitmap(resourcesVfs["img/icons/door-open-solid [MConverter.eu].jpg"])
		@ResourceVfsPath("img/icons/exit_door.png") val `exitDoor` get() = TypedVfsFileBitmap(resourcesVfs["img/icons/exit_door.png"])
	}
}
