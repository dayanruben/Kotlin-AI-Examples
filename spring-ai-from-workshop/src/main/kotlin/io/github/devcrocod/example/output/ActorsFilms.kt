package io.github.devcrocod.example.output

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class ActorsFilms @JsonCreator constructor(
    @JsonProperty val actor: String,
    @JsonProperty val movies: List<String> // works only with jackson annotation
) {
    override fun toString(): String = "ActorsFilms{actor='$actor', movies=$movies}"
}