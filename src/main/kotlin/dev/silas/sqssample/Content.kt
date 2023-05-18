package dev.silas.sqssample

data class Content(
    val type: ContentType,
    val body: String
)

enum class ContentType {
    TEST
}