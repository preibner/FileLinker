package io.github.preibner.filelinker

import tornadofx.*
import java.nio.file.Path

class MainViewController: Controller() {
    fun start(sourceDirectory: Path, targetDirectory: Path, regex: String) {
        runAsync {
            val fileLinker = FileLinker(sourceDirectory, targetDirectory, Regex(regex))
            fileLinker.link()
        }
    }
}
