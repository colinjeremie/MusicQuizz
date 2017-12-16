package com.github.colinjeremie.willyoufindit.utils

import java.text.Normalizer

fun String.normalize() =
        Normalizer.normalize(this, Normalizer.Form.NFD).replace("[^\\p{ASCII}]".toRegex(), "")
