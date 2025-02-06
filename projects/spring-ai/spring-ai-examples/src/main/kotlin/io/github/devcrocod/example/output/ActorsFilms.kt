package io.github.devcrocod.example.output


data class ActorsFilms constructor(
    val actor: String,
    val movies: List<String> // works only with jackson annotation
) {
    override fun toString(): String = "ActorsFilms{actor='$actor', movies=$movies}"
}