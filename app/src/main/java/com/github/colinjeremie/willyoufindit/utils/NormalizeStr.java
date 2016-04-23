package com.github.colinjeremie.willyoufindit.utils;

import java.text.Normalizer;

/**
 * * WillYouFindIt
 * Created by jerem_000 on 4/23/2016.
 */
public class NormalizeStr {

    public static String normalizeIt(String pStr){
        return  Normalizer.normalize(pStr, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
}
