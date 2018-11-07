package org.workfort.base.util.collection


/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 11/7/2018 at 6:55 PM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Purpose:
*  *
*  * Last edited by : Md. Azizul Islam on 11/7/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/

class ListExample {
    fun check(){
        /*val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
           .filter { it > 5 }
           .onEach { println(it) }*/

        val result = listOf(1, 2, 3, 4, 5)
                .map { n -> n * n }
                .filter { n -> n < 10 }
                .last()

        print(result)

        //Returns true if at least one element matches the given predicate
        val list = listOf(1, 2, 3, 4, 5, 6)
        val v = list.any { it % 2 == 0 }
        println(v)

        //All
        //Returns true if all the elements match the given predicate.
        val v1 = list.all { it % 2 == 0 }
        println(v1)

        //count
        //Returns the number of elements matching the given predicate.
        val v2 = list.count { it % 2 == 0 }
        println(v2)

        /**
         * fold
         * foldRight
        Accumulates the value starting with an initial value and applying an operation from the first to the last element in a collection.
         */
        val v3 = list.fold(4) { total, next -> total + next }
        println(v3)


        /**
         * forEach
         * orEachIndexed
        Performs the given operation to each element.
         */
        list.forEachIndexed { index, value -> println("position $index contains a $value") }

        /**
         * max
         * min
         * maxBy
         * minBy
         * none
         */
        val v4 = list.minBy { -it }
        println("v4 = $v4")

        /**
         * reduce
        Same as fold, but it doesn’t use an initial value. It accumulates the value applying an operation from the first to the last element in a collection.
         */
        val v5 = list.reduce { total, next -> total + next }
        println("v5 = $v5")

        /**
         * sumBy
        Returns the sum of all values produced by the transform function from the elements in the collection.
         */
        val v6 = list.sumBy { it % 2 }
        println("v6 = $v6")

        /**
         * 18.2 Filtering operations
         * drop
         * Returns a list containing all elements except first n elements.
         */

        val v7 = list.drop(4)
        println("v7 = $v7")

        val v8 = list.dropWhile { it < 3 }
        println("v8 = $v8")


        val v9 = list.windowed(2, 3, false)
        println("v9 = $v9")


        val names = listOf("test", "ddddddddd", "abc", "ab")

        names.filter { it -> it.length < 5 }
                .sortedBy { it }
                .map { it.toUpperCase() }
                .forEach { println(it) }


        val mapValue = mapOf<String, String>(Pair("jdfj", "dff"), Pair("kf", "firjfi"))

        val v10 = mapValue.filterKeys { it.contains("jdfj") }
        println("v10 = $v10")

        mapValue.onEach { it.value }

        //https://grokonez.com/kotlin/kotlin-map-collection-flatmap-flatmapto-methods
        //https://grokonez.com/kotlin/kotlin-map-methods-maxby-maxwith
        //https://grokonez.com/kotlin/convert-kotlin-list-map
        //https://grokonez.com/kotlin/kotlin-tutorial-convert-kotlin-map-list
        //http://zetcode.com/kotlin/sets/
    }
}