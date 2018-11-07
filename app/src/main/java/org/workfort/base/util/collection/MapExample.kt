package org.workfort.base.util.collection

/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 10/24/2018 at 12:00 PM.
*  * Email : azizul@w3engineers.com
*  *
*  * Last edited by : Md. Azizul Islam on 10/24/2018.
*  *
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
*  ****************************************************************************
*/


class MapExample{
    constructor(){}
    fun check(){
        val productMap = hashMapOf<String, Product>("iPhone 8 Plus 64G" to Product("iPhone 8 Plus 64G", "Apple", 850.00f),
                "Samsung Galaxy S8 64GB Unlocked Phone" to Product("Samsung Galaxy S8 64GB Unlocked Phone", "Samsung", 699.99f),
                "iPad Pro 9.7-inch 32 GB" to Product("iPad Pro 9.7-inch 32 GB", "Apple", 474.98f),
                "Samsung Electronics Ultra HD Smart LED TV" to Product("Samsung Electronics Ultra HD Smart LED TV", "Samsung", 677.92f),
                "Google Pixel Phone - 5 inch display" to Product("Google Pixel Phone - 5 inch display", "Google", 279.95f),
                "iPad Pro 9.7-inch 128G" to Product("iPad Pro 9.7-inch 128G", "Apple", 574.99f),
                "Google Wifi system 1-Pack" to Product("Google Wifi system 1-Pack", "Google", 149.90f),
                "Samsung Galaxy Tab 4" to Product("Samsung Galaxy Tab 4", "Samsung", 127.67f))


        /*val productsWithMaxPrice = productMap.maxWith(object: Comparator<Map.Entry<String, Product>> {
            override fun compare(p1: Map.Entry<String, Product>, p2: Map.Entry<String, Product>): Int = when {
                p1.value.price > p2.value.price -> 1
                p1.value.price == p2.value.price -> 0
                else -> -1
            }
        })
        var productsWithMaxPrice2 = productMap.maxBy { (_, product) -> product.price }
        println("Key: ${productsWithMaxPrice?.key}, Value: ${productsWithMaxPrice?.value}")

        val reduce = productMap.maxBy {}

        println("Key: ${productsWithMaxPrice?.key}, Value: ${productsWithMaxPrice?.value}")*/

        /**
         *
         * Filter
         *
         */
        var map = productMap.filter { (customer, address) -> address.price > 18 && customer === "S NUGENT AVE" }
        map.forEach { println("${it.key}, ${it.value}") }

        var multableMap = mutableMapOf<String, Product>()
        productMap.filterTo(multableMap, { (key, product) -> product.price > 18 && key === "S NUGENT AVE" })
        multableMap.forEach { println("${it.key}, ${it.value}") }


        map = productMap.filterKeys { it.length < 30 }
        map.forEach { println("${it.key}, ${it.value}") }

        map = productMap.filterValues { it.price == 7485f }
        map.forEach { println("${it.key}, ${it.value}") }

        /**
         *
         * Flatmap
         *
         *
         *
         */
        println("***********************************")
        val customerList = productMap.flatMap { (customer, _) -> listOf(customer) }
        customerList.forEach { println(it) }

        println("flatMap ***********************************")
        val addressList = productMap.flatMap { (_, address) -> listOf(address) }
        addressList.forEach { println(it) }

        println("price ***********************************")

        val customerInfos = productMap.flatMap { (customer, address) -> listOf("${customer} lives at ${address.price}") }
        customerInfos.forEach { println(it) }


        var newCustomerList = mutableListOf("")
        productMap.flatMapTo(newCustomerList, { (customer, _) -> listOf(customer) })
        newCustomerList.forEach { println(it) }


        /**
         * Max by
         *
         */

        val maxvalue = productMap.maxBy { (key, value) -> value.price }
        val minvalue = productMap.minBy { (key, value) -> value.price }

        val maxWith = productMap.maxWith(object : Comparator<Map.Entry<String, Product>> {
            override fun compare(p1: Map.Entry<String, Product>?, p2: Map.Entry<String, Product>?): Int {
                if (p1!!.value.price > p2!!.value.price)
                    return 1
                else if (p1!!.value.price == p2!!.value.price)
                    return 0
                else
                    return -1

            }
        })

        /**
         *
         * Any
         *
         */

        productMap.any { (key,value)-> value.price > 10f }

    }
}
