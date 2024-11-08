package io.github.devcrocod.example.stuff

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class Completion @JsonCreator constructor(
    @JsonProperty("completion") val completion: String?
) // exception with null