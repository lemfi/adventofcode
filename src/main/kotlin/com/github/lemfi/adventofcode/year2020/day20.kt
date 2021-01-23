package com.github.lemfi.adventofcode.year2020

import java.nio.charset.Charset
import kotlin.math.sqrt
import kotlin.system.measureTimeMillis

fun main() {

    measureTimeMillis {

        Day20.star1(Day20.data)
            .apply { println("res: $this") }

    }.apply {
        println("time: ${this / 1000}")
    }

    Day20.star2(Day20.data)
        .apply { println("res: $this") }

}

object Day20 {

    fun star1(data: String): Long {

        return data.split("\n\n").map { tile ->
            tile.lines().let { lines ->
                Tile(
                    lines.first().substringAfter("Tile ").substringBefore(":").toLong(),
                    lines.drop(1).joinToString("\n")
                )
            }
        }.let {

            SquarePuzzleMaker(it).run {

                // assemblage du puzzle
                fill()

                // multiplication des identifiants des 4 coins :
                puzzle.first().first()!!.id *
                        puzzle.first().last()!!.id *
                        puzzle.last().first()!!.id *
                        puzzle.last().last()!!.id

            }
        }
    }

    fun star2(data: String): Int {

        return data.split("\n\n").map { tile ->
            tile.lines().let { lines ->
                Tile(
                    lines.first().substringAfter("Tile ").substringBefore(":").toLong(),
                    lines.drop(1).joinToString("\n")
                )
            }
        }.let {

            SquarePuzzleMaker(it).run {

                // assemblage du puzzle
                fill()

                drawFinalImage().let {
                    image ->
                    image
                        .rotations()
                        .fold(0) { monsters, image -> image.findMonsters().let { if (it > monsters) it else monsters } }
                        .let { monsters ->
                            image.count { it == '#' } - monsters * 15
                        }
                }

            }
        }
    }

    fun String.rotations(): List<String> {
        val flip: String.() -> String = { lines().map { it.toList().reversed().joinToString("") }.joinToString("\n") }
        val rotate180: String.() -> String = { lines().reversed().joinToString("\n") }
        val rotate90: String.() -> String = { lines().mapIndexed { y, line ->
            line.toList().mapIndexed { x, _ ->
                lines()[x].toList()[y].toString()
            }.joinToString("")
        }
            .joinToString("\n")
            .flip()
        }

        val rotate270: String.() -> String = { rotate90().rotate180().flip() }

        return listOf(rotate180(), rotate270(), rotate90(), flip(), this, flip().rotate90(), flip().rotate180(), flip().rotate270())
    }

    fun String.sideUp() = lines().first()

    fun String.sideDown() = lines().last()

    fun String.sideLeft() = lines().map {
        it.toList().first().toString()
    }.joinToString("")

    fun String.sideRight() = lines().map { it.toList().last().toString() }.joinToString("")

    data class Tile(val id: Long, var display: String) {

        val rotations = display.rotations()

        fun removeBorders() = display.let {
            it.lines().toMutableList().drop(1).dropLast(1).map {
                it.toList().drop(1).dropLast(1).joinToString("")
            }.joinToString("\n")
        }
    }

    class SquarePuzzleMaker(val tiles: List<Tile>) {

        // la taille d'un coté puzzle
        private val size = sqrt(tiles.size.toFloat()).toInt()

        // le puzzle à remplir, j'instancie tous ses emplacements avec la valeur null
        val puzzle: List<MutableList<Tile?>> =
            (0 until size).map {
                mutableListOf(*(0 until size).map { null }.toTypedArray())
            }

        // pas de contrainte : le coté est toujours valide
        private val noConstraint: (String, List<Tile>) -> Boolean = { _, _ -> true }

        // le coté ne doit correspondre à aucun des cotés des autres fragments
        private val noMatchConstraint: (String, List<Tile>) -> Boolean = { t, a -> a.none { it.run { rotations.any { it.sideRight() == t } } } }

        // le coté doit correspondre à celui du fragment à coté duquel on souhaite le placer
        private fun matchConstraint(expectedMatch: String): (String, List<Tile>) -> Boolean = { t, _ ->  t == expectedMatch }

        // récupération de la contrainte s'appliquant à gauche d'une case
        private fun leftConstraint(line: Int, column: Int) =
            puzzle.get(line).getOrNull(column - 1)?.let { leftTile ->
                matchConstraint(leftTile.display.sideRight())
            } ?: if (column == 0) noMatchConstraint else noConstraint

        // récupération de la contrainte s'appliquant à droite d'une case
        private fun rightConstraint(line: Int, column: Int) =
            puzzle.get(line).getOrNull(column + 1)?.let { rightTile ->
                matchConstraint(rightTile.display.sideLeft())
            } ?: if (column == size - 1) noMatchConstraint else noConstraint

        // récupération de la contrainte s'appliquant en haut d'une case
        private fun upConstraint(line: Int, column: Int) =
            puzzle.getOrNull(line - 1)?.get(column)?.let { upTile ->
                matchConstraint(upTile.display.sideDown())
            } ?: if (line == 0) noMatchConstraint else noConstraint

        // récupération de la contrainte s'appliquant en bas d'une case
        private fun downConstraint(line: Int, column: Int) =
            puzzle.getOrNull(line + 1)?.get(column)?.let { downTile ->
                matchConstraint(downTile.display.sideUp())
            } ?: if (line == size - 1) noMatchConstraint else noConstraint

        fun fill(line: Int = 0, column: Int = 0, availableTiles: List<Tile> = tiles): Boolean {
            return if (availableTiles.isEmpty()) puzzle.none { it.any { it == null } }
            else {
                val leftConstraint = leftConstraint(line, column)
                val rightConstraint = rightConstraint(line, column)
                val upConstraint = upConstraint(line, column)
                val downConstraint = downConstraint(line, column)

                // pour chaque fragments j'essaye ceux qui valident les contraintes
                availableTiles.fold(false) { puzzleEndedForTile, tile ->
                    puzzleEndedForTile ||
                            // pour chaque rotation d'un fragment j'essaye celles qui valident les contraintes
                            tile.rotations.fold(puzzleEndedForTile) { puzzleEndedForRotation, rotation ->
                                puzzleEndedForRotation ||
                                        // je soustrais des fragments disponibles celui que je vais essayer
                                        availableTiles.minusElement(tile).let { newAvailableTiles ->

                                            // si la rotation valide ses 4 contraintes
                                            // je fixe l'affichage du fragment sur cette rotation
                                            // et je remplis le puzzle
                                            if (leftConstraint(rotation.sideLeft(), newAvailableTiles)
                                                && rightConstraint(rotation.sideRight(), newAvailableTiles)
                                                && upConstraint(rotation.sideUp(), newAvailableTiles)
                                                && downConstraint(rotation.sideDown(), newAvailableTiles)
                                            ) {

                                                tile.display = rotation
                                                puzzle[line][column] = tile

                                                // puis je tente de remplir la case suivante du puzzle avec les fragments restants
                                                if (column == size - 1) fill(line + 1, 0, newAvailableTiles)
                                                else fill(line, column + 1, newAvailableTiles)
                                            } else {
                                                // si la rotation ne valide pas les 4 contraintes, je passe à la suivante
                                                puzzle[line][column] = null
                                                false
                                            }
                                        }
                            }
                }
            }
        }

        fun drawFinalImage() = puzzle.map { it.map { it!!.removeBorders() } }.let {
            it.map { tiles -> (tiles.first().lines().indices).map { indice ->
                tiles.joinToString("") { it.lines()[indice] }
            }.joinToString("\n")
            }.joinToString("\n")
        }
    }

    fun String.findMonsters(): Int =

        // construction des coordonnées des points de départ potentiels des monstres
        lines().flatMapIndexed {
                y, line -> line.toList().mapIndexed {
            // le monstre fait une longueur de 20 et le haut de sa tete se situe à l'indice 18
            // => aucun # avant l'insdice 18 ne peut etre le départ d'un monstre
            // le monstre fait une hauteur de 3
            // => les deux dernières lignes ne peuvent etre le départ d'un monstre
                x, cell -> if (x >= 18 && y <= lines().size - 2 && cell == '#') x to y else null
        }
        }
            .filterNotNull()
            .count { (x, y) ->
                // pour chaque coordonnée, je vérifie que tous les éléments du corps sont présents
                lines()[y+1].toList().get(x-18) == '#'
                        && lines()[y+1].toList().get(x-18 + 5) == '#'
                        && lines()[y+1].toList().get(x-18 + 6) == '#'
                        && lines()[y+1].toList().get(x-18 + 11) == '#'
                        && lines()[y+1].toList().get(x-18 + 12) == '#'
                        && lines()[y+1].toList().get(x-18 + 17) == '#'
                        && lines()[y+1].toList().get(x-18 + 18) == '#'
                        && lines()[y+1].toList().get(x-18 + 19) == '#'
                        && lines()[y+2].toList().get(x-18 + 1) == '#'
                        && lines()[y+2].toList().get(x-18 + 4) == '#'
                        && lines()[y+2].toList().get(x-18 + 7) == '#'
                        && lines()[y+2].toList().get(x-18 + 10) == '#'
                        && lines()[y+2].toList().get(x-18 + 13) == '#'
                        && lines()[y+2].toList().get(x-18 + 16) == '#'
            }

    val data: String by lazy { javaClass.getResourceAsStream("/2020/day20.txt").readAllBytes().toString(Charset.defaultCharset()) }
}
