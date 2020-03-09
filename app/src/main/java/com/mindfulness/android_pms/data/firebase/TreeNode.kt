package com.mindfulness.android_pms.data.firebase

import kotlin.reflect.KFunction
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1

class TreeNode{/*<String>(val value: String?, val next: TreeNode<String>? = null){

    fun <T> makeLinkedList(vararg elements: T): TreeNode<T>? {
        var node: TreeNode<T>? = null
        for (element in elements.reversed()) {
            node = TreeNode(element, node)
        }
        return node
    }*/

    fun <T> printProperty(instance: T, prop: KProperty1<T, *>) {
        println("${prop.name} = ${prop.get(instance)}")
    }

    fun <T> incrementProperty(
        instance: T, prop: KMutableProperty1<T, Int>
    ) {
        val value = prop.get(instance)
        prop.set(instance, value + 1)
    }

    fun aaa(){
        val person = Person("Lisa", 23)

        printProperty(person, Person::name)
        incrementProperty(person, Person::age)


        val person2 = Person("Lisa", 32)
        val g: KFunction<String> = person2::greet//Person.::greet
        println(g.name)
        println(g.call(person2, "Anne"))



    }


}

class Person(val name: String, var age: Int) {
    fun present() = "I'm $name, and I'm $age years old"
    fun greet(other: String) = "Hi, $other, I'm $name"
}