package com.rambots4571.rampage.structure;

import java.util.Iterator;

public class Sequence<E> implements Iterator<E> {
    private Node<E> first, current, last;
    private int size;

    private static class Node<T> {
        private T element;
        private Node<T> next;

        Node(T element) {
            this.element = element;
        }
    }

    public Sequence() {
        first = null;
        current = null;
        last = null;
        size = 0;
    }

    @Override
    public boolean hasNext() {
        return current.next != null;
    }

    @Override
    public E next() {
        E element = current.element;
        current = current.next;
        return element;
    }

    public void add(E element) {
        Node<E> addend = new Node<>(element);
        if (first == null) {
            first = addend;
            current = addend;
            last = addend;
        } else {
            last.next = addend;
            last = addend;
        }
        size++;
    }

    public int size() {
        return size;
    }
}
