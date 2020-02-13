package io.github.preibner.filelinker

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class FileLinker(val sourceDirectory: Path, val targetDirectory: Path, val regex: Regex) {
    fun link(): List<String> {
        val createdLinks = mutableListOf<String>()

        sourceDirectory.toFile().walkTopDown().filter { it.name.matches(regex) }.forEach {
            val linkTarget = Paths.get(it.absolutePath)
            val fileName = it.name
            val link = Paths.get(targetDirectory.toString(), fileName)
            if(Files.exists(link)){
                Files.delete(link)
            }
            Files.createLink(link, linkTarget)
            createdLinks.add(link.toAbsolutePath().toString())
        }

        return createdLinks
    }
}