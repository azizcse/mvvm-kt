package org.workfort.base.util.collection

import java.util.*


/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 11/7/2018 at 6:54 PM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Purpose:
*  *
*  * Last edited by : Md. Azizul Islam on 11/7/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/

class SetExample {
    fun check(){
        //Mutable
        val hashSet = HashSet<String>()
        val linkedHashSet = LinkedHashSet<String>()
        val sortedSet = TreeSet<String>()
        val hashSet2 = hashSetOf<String>()
        val mutableSet = mutableSetOf<String>()

        //Immutable
        val setof = setOf<String>()

        val numset = setOf(11, 5, 3, 8, 1, 9, 6, 2)

        val len = numset.count()
        val max = numset.max()
        val min = numset.min()
        val sum = numset.sum()
        val avg = numset.average()

        println("len =$len max =$max min = $min sum = $sum avg =$avg")

        val count = numset.count()
        val count2 = numset.count { e -> e % 2 == 0 }
        val count3 = numset.count { it % 3 == 0 }

        println("count =$count modulus =$count2 module =$count3")

        //Kotlin Set first and last elements
        val w1 = numset.first()
        val w2 = numset.last()
        val w3 = numset.findLast { w -> w % 2 == 0 }


        println("first =$w1 last =$w2 findLast =$w3")

        /**
         *
         *
         * Kotlin Set iterate
         *
         *
         */
        numset.forEach { e -> print("$e ") }
        println()

        for (word in numset) {
            print("$word ")
        }

        println()

        for (i in 0 until numset.size) {
            print("${numset.elementAt(i)} ")
        }

        println()

        numset.forEachIndexed({ i, e -> println("$i - $e") })

        val it: Iterator<Int> = numset.asIterable().iterator()

        while (it.hasNext()) {

            val e = it.next()
            print("$e ")
        }

        println()

        /**
         *
         *
         * Kotlin set sort
         *
         */
        println("******** Set sort *****")
        val sortAsc = numset.sorted()
        println(sortAsc)

        val sortDesc = numset.sortedDescending()
        println(sortDesc)

        val revNums = numset.reversed()
        println(revNums)


        val res = numset.sortedBy { car -> car % 2 == 0 }
        res.forEach { e -> println(e) }

        println("*************")

        val res2 = numset.sortedByDescending { car -> car % 3 == 0 }
        res2.forEach { e -> println(e) }


        val nums = setOf(1, 2, 3)
        val nums2 = setOf(3, 4, 5)

        val nums3 = nums.union(nums2)
        println(nums3)


        /**
         *
         * Set filter
         *
         */
        val words = setOf("pen", "cup", "dog", "person", "cement", "coal", "spectacles")

        val words2 = words.filter { e -> e.length == 3 }
        words2.forEach { e -> print("$e ") }

        println()

        val words3 = words.filterNot { e -> e.length == 3 }

        words3.forEach { e -> print("$e ") }

        println()

        val cars = setOf(Car("Mazda", 6300), Car("Toyota", 12400),
                Car("Skoda", 5670), Car("Mercedes", 18600))

        val ress = cars.filter { car -> car.price > 10000 }
        ress.forEach { e -> println(e) }


        /**
         *
         * Set Take
         *
         */
        val take = numset.take(3)
        val take2 = numset.sorted().takeWhile { it % 2 == 0 }

        /**
         * Set drop
         *
         */
        val drop = numset.drop(3)
        println(drop)

        val drop2 = numset.sorted().dropWhile { e -> e < 0 }
        println(drop2)

        val drop3 = numset.sorted().dropLastWhile { e -> e > 0 }
        println(drop3)

        /**
         *
         * set any
         *
         */

        val r = revNums.any { e -> e > 10 }


        /**
         *
         *
         * Set group by
         *
         */

        val groupNums = setOf(1, 2, 3, 4, 5, 6, 7, 8)

        val group = groupNums.groupBy { if (it % 2 == 0) "even" else "odd" }
        println(group)

        val groupStr = setOf("as", "pen", "cup", "doll", "my", "dog", "spectacles")

        val group2 = groupStr.groupBy { it.length }
        println(group2)


        /**
         *
         *
         * Set partition
         *
         */

        val partitionNums = setOf(4, -5, 3, 2, -1, 7, -6, 8, 9)

        val (part1, part2) = partitionNums.partition { e -> e < 0 }

        println(part1)
        println(part2)

        /**
         *
         * Set chanked
         * 1*2 + 3*4 + 5*6
         *
         */

        /*val chankedNum = setOf(1, 2, 3, 4, 5)

        val chunked = chankedNum.chunked(2).fold(0) { total, next -> total + next[0] * next[1] }

        println("chunk ="+chunked)*/

        /**
         *
         * Set fold
         *
         */

        val expenses = setOf(2, 4, 1, 3, 5)

        val cash = 2

        val fold = expenses.fold(cash) {total, next -> total + next}
        println("fold ="+fold)

        /**
         *
         * Set reduce
         *
         */

        val reducenum = setOf(4, 5, 3, 2, 1, 7, 6, 8, 9)

        val reduce = reducenum.reduce { total, next -> total + next }
        println("Reduce $reduce")
    }
}