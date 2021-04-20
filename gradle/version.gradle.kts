import java.util.*

fun getVersionNameFromProperties(): String {
    return loadVersionProperties().getProperty("version_name")
}

fun getVersionCodeFromProperties(): Int {
    return loadVersionProperties().getProperty("version_code").toInt()
}

fun loadVersionProperties(): Properties {
    val fis = project.rootProject.file("version.properties").inputStream()
    val props = Properties()
    props.load(fis)
    return props
}

extra.apply{
    set("version_name", getVersionNameFromProperties())
    set("version_code", getVersionCodeFromProperties())
}