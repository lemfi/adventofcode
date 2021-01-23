package com.github.lemfi.adventofcode.year2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigInteger

class Day21Test {

    @Test
    fun sample1() {

        Assertions.assertEquals(5, Day21.star1("""mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
trh fvjkl sbzzf mxmxvkd (contains dairy)
sqjhc fvjkl (contains soy)
sqjhc mxmxvkd sbzzf (contains fish)"""))
    }

    @Test
    fun sample2() {

        Assertions.assertEquals("mxmxvkd,sqjhc,fvjkl", Day21.star2("""mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
trh fvjkl sbzzf mxmxvkd (contains dairy)
sqjhc fvjkl (contains soy)
sqjhc mxmxvkd sbzzf (contains fish)"""))
    }

    @Test
    fun real1() {
        Assertions.assertEquals(2635, Day21.star1(Day21.data))
    }

    @Test
    fun real2() {
        Assertions.assertEquals("xncgqbcp,frkmp,qhqs,qnhjhn,dhsnxr,rzrktx,ntflq,lgnhmx", Day21.star2(Day21.data))
    }

}