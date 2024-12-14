package com.github.lemfi.adventofcode.year2024

import com.github.lemfi.adventofcode.processStars
import com.github.lemfi.adventofcode.stuff.Coordinate
import java.util.UUID
import kotlin.random.Random

object Day14 {

    private fun String?.toData() = this ?: data(14)

    fun star1(input: String?) = input.parse()
        .let { robots ->
            val width = 101
            val height = 103

            val middleWidth = width / 2
            val middleHeight = height / 2

            val turns = 100

            repeat(turns) { x ->
                robots.move(width, height)
            }

            val q1 = robots.filter { it.x < middleWidth && it.y < middleHeight }
            val q2 = robots.filter { it.x > middleWidth && it.y < middleHeight }
            val q3 = robots.filter { it.x < middleWidth && it.y > middleHeight }
            val q4 = robots.filter { it.x > middleWidth && it.y > middleHeight }

            q1.size * q2.size * q3.size * q4.size
        }

    fun star2(input: String?) = input.parse()
        .let { robots ->
            val width = 101
            val height = 103

            val positions = mutableListOf<Set<Coordinate>>()

            while (true) {
                robots.move(width, height)

                if (positions.contains(robots.map { Coordinate(it.x, it.y) }.toSet())) break
                else (positions.add(robots.map { Coordinate(it.x, it.y) }.toSet()))
            }

            positions.firstOrNull { position ->
                (0 until height).joinToString("\n") { x ->
                    (0 until width).joinToString("") { y ->
                        if (position.contains(Coordinate(x, y))) "1" else " "
                    }
                } ==
                        """                                  1            1          1                                          
                                                                1       1                            
                                                      1                                              
                                                              1                                      
                           1                                      11                                 
                                                                                                     
                    1           1                     1                                              
                                                                                                     
                                                                                                     
                                         1  1          1         1                                   
    1                                                                                    1         1 
   1       1     1                                           1                                       
                      1               1                               1                              
              1                                         1            1                               
                                                                                                     
                                                                                  1                  
                    1       1      1                                                                 
                   1   1                                                                             
                                                                1                                    
                                                                  1     1                            
                                                                                                     
             1                 1              1  1                           1                       
                                                                                                     
                                           1                                                         
                                        1          1                                                 
                                                                                           1   1     
                                                                 1       1                           
           1                                                                    1                    
                            1                                                                        
                                          1                                                   1      
                                                                            1                        
                                                                                                     
                                                              1                                      
                                                                                                     
                                                                                                     
                1             111111111111111111111111111111111                      1               
                              1                               1                                      
                              1                               1     1                                
        1                     1                               1                    1                 
                  1           1                               1                          1           
           1       1          1                       1       1                         1            
                              1                      11       1                                    1 
                              1                  1  111       1                                      
                       1      1                 11 1111       1       1            1                 
   1                          1             1  11111111       1                                      
1                             1            11 111111111       1                                      
                              1        1  1111111111111       1                                      
  1                           1       11 11111111111111       1                                      
                              1      111111111111111111       1                 1                    
                      1       1     1111111111111111111111    1                   1   1              
                              1    11111111111111111111111    1           1                          
                           1  1     1111111111111111111111    1                      1               
                              1      111111111111111111       1                                      
                              1       11 11111111111111       1                                      
                              1        1  1111111111111       1                                      
                              1            11 111111111       1                                      
                              1             1  11111111       1          1                           
                              1                 11 1111       1                                      
                              1                  1  111       1                                      
                              1                      11       1                                      
         1 1               1  1                       1       1                                      
                              1                               1                                      
      1                       1                               1                                      
                              1                               1                                      
                              1                               1                                      
                              111111111111111111111111111111111                                1     
                                                                                                     
                                                      1                                              
                                                                                                     
                                                                                  1                  
                               1                           1                           1             
                                                    1                                         1      
                    1                                                                                
                                                                 1                                   
                                                                                                   1 
                                  1                                                                  
                                                                       1         1                   
                                                                                                     
                                                                                                     
                        1                                                                            
                                                                     1                               
                                                        1                                            
                                                                                              1      
     1                                                                 1             1               
                                       1                 1                                           
                                                                   1                                1
     1             1                                   1                                             
                    1                     1                                                          
                              1       1                       1                 1                    
                                         1                                           1               
                                                                                                     
                               1                  1                                                  
                          1                                   1                            1  1      
                                                          1              1                           
                          1                                                                          
                                                               1                                1    
                                             1                                                       
                                   1                                                                 
               1                                                                                     
                              1                                                                      
                    1                                     1                                      1   
                                                                                                     
                                                                                                     """
            }
                ?.let { positions.indexOf(it) + 1 }
                ?: 0
        }

    private fun List<Robot>.move(
        width: Int,
        height: Int
    ) {
        onEach { robot ->
            robot.x = ((robot.x + robot.vx)).let { if (it >= 0) it % width else (width + it) }
            robot.y = ((robot.y + robot.vy)).let { if (it >= 0) it % height else (height + it) }
        }
    }

    private fun String?.parse() = toData()
        .lines()
        .map { data ->
            Robot(
                x = data.substringAfter("p=").substringBefore(",").toInt(),
                y = data.substringAfter(",").substringBefore(" v").toInt(),
                vx = data.substringAfter("v=").substringBefore(",").toInt(),
                vy = data.substringAfterLast(",").toInt(),
            )
        }

    data class Robot(
        var x: Int,
        var y: Int,
        val vx: Int,
        val vy: Int,
        val uuid: String = UUID.randomUUID().toString() + Random.nextInt(),
    )
}

fun main() {
    processStars(Day14::star1, Day14::star2)
}
