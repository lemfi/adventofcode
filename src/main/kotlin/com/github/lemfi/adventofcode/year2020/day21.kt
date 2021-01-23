package com.github.lemfi.adventofcode.year2020

import java.nio.charset.Charset


fun main() {

    Day21.star1(Day21.data)
        .apply { println("res: $this") }

    Day21.star2(Day21.data)
        .apply { println("res: $this") }

}

object Day21 {

    fun star1(data: String): Int {

        return data
            .parse()
            .fitAllergens()
            .food.flatMap { it.ingredients }
            .apply { println(this.filter { it.allergen == null }.map { it.name }) }
            .count { it.allergen == null }
    }

    fun star2(data: String): String {

        return data
            .parse()
            .fitAllergens()
            .food.flatMap { it.ingredients }
            .filter { it.allergen != null }
            .distinct()
            .sortedBy { it.allergen }
            .map { it.name }
            .joinToString(",")
    }

    fun String.parse(): Shop = lines().map {
        val ingredientNames =  it.substringBefore("(").split(" ").filterNot { it.isBlank() }
        val allergens = it.substringAfter("(contains ").substringBefore(")").split(", ").toSet()
        Food(ingredientNames.map { Ingredient(it) }.toSet(), allergens)
    }.let {
        Shop(it, it.flatMap { it.allergens }.toSet())
    }

    data class Shop(
        val food: List<Food>,
        val allergens: Set<String>
    ) {

        fun fitAllergens(): Shop {
            val allergen = nextAllergen()

            val possibilities = possibilitiesForAllergen(allergen)

            var index = 0
            while (index < possibilities.size && !allFit()) {
                val ingredient = possibilities[index]
                food.filter { it.ingredients.any { it.name == ingredient.name } }.let {
                    it.forEach {
                        it.ingredients.filter { it.name == ingredient.name }.forEach { it.allergen = allergen }
                    }
                }
                if (!allFit()) fitAllergens()
                if (!allFit()) {
                    food.flatMap { it.ingredients }.filter { it.allergen == allergen }.let {
                        it.forEach { it.allergen = null }
                    }
                }

                index ++
            }
            return this
        }

        fun possibilitiesForAllergen(allergen: String) = if (food.any {it.ingredients.any { it.allergen == allergen }}) emptyList() else
            food.filter { it.allergens.contains(allergen) && it.ingredients.map { it.allergen }.none { it == allergen } }
                .sortedBy { it.ingredients.size }
                .map { it.ingredients.filter { it.allergen == null } }.first()

        fun allFit() = food.map { it.allFit() }.reduce { acc, b -> acc && b }

        fun nextAllergen() = food.flatMap { it.allergens - it.ingredients.map { it.allergen } }.filterNotNull().let {
            it.map { it to possibilitiesForAllergen(it) }.sortedBy { it.second.size }.first().first
        }
    }

    data class Food(
        val ingredients: Set<Ingredient>,
        val allergens: Set<String>
    ) {
        fun allFit() = ingredients.map { it.allergen }.toSet().containsAll(allergens)
    }

    data class Ingredient(
        val name: String,
        var allergen: String? = null
    )

    val data: String by lazy { javaClass.getResourceAsStream("/2020/day21.txt").readAllBytes().toString(Charset.defaultCharset()) }
}
