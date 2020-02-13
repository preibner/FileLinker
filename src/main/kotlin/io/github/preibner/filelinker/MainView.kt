package io.github.preibner.filelinker

import javafx.beans.binding.StringBinding
import javafx.beans.property.Property
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.awt.event.MouseAdapter
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

class MainView: View() {

    val controller: MainViewController by inject()
    val model= ViewModel()
    val sourceDirectory= model.bind{ SimpleObjectProperty<Path>(Paths.get(".")) }
    val regex= model.bind{ SimpleStringProperty() }
    val targetDirectory= model.bind { SimpleObjectProperty<Path>(Paths.get(".")) }

    override val root = form {
        fieldset {
            field("Source Directory") {
                textfield(sourceDirectory.stringBinding()) {
                    isEditable = false
                    setOnMouseClicked {
                        sourceDirectory.value = chooseDirectory("Choose source directory")?.toPath()
                        sourceDirectory.markDirty()
                    }
                }
            }
            field("Regex") {
                textfield(regex)
            }
            field("Target Directory") {
                textfield(targetDirectory.stringBinding()) {
                    isEditable = false
                    setOnMouseClicked {
                        targetDirectory.value = chooseDirectory("Choose target directory")?.toPath()
                        targetDirectory.markDirty()
                    }
                }
            }
        }

        button("OK"){
            action {
                controller.start(sourceDirectory.value!!, targetDirectory.value!!, regex.value)
            }
            disableProperty().bind(regex.booleanBinding{it != null && it.isEmpty() && sourceDirectory.value != null && targetDirectory.value != null})
        }
    }
}

private fun Property<Path>.stringBinding(): StringBinding {
    return this.stringBinding{it?.toAbsolutePath().toString()}
}
