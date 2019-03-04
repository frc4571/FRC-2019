package com.rambots4571.rampage.structure

class Sequence<E> : Iterator<E> {
    private var first: Node<E>?
    private var current: Node<E>?
    private var last: Node<E>?
    var size: Int
        private set

    private class Node<T>(var element: T) {
        var next: Node<T>? = null
    }

    init {
        first = null
        current = null
        last = null
        size = 0
    }

    override fun hasNext(): Boolean {
        return current?.next != null
    }

    override fun next(): E {
        val e = current?.element
        current = current?.next
        return e!!
    }

    fun add(e: E) {
        val new = Node(e)
        if (first == null) {
            first = new
            current = new
            last = new
        } else {
            last?.next = new
            last = new
        }
        size++
    }
}