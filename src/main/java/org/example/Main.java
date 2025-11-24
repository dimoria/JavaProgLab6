package org.example;

import java.util.*;

public class Main {



    enum MusicStyle { POP, ROCK, JAZZ }

    abstract static class MusicComposition {
        private final String title;
        private final int duration;
        private final MusicStyle style;

        public MusicComposition(String title, int duration, MusicStyle style) {
            this.title = title;
            this.duration = duration;
            this.style = style;
        }

        public String getTitle() { return title; }
        public int getDuration() { return duration; }
        public MusicStyle getStyle() { return style; }

        @Override
        public String toString() {
            return title + " (" + style + ", " + duration + " sec)";
        }
    }

    static class PopSong extends MusicComposition {
        public PopSong(String title, int duration) {
            super(title, duration, MusicStyle.POP);
        }
    }

    static class RockSong extends MusicComposition {
        public RockSong(String title, int duration) {
            super(title, duration, MusicStyle.ROCK);
        }
    }

    static class JazzSong extends MusicComposition {
        public JazzSong(String title, int duration) {
            super(title, duration, MusicStyle.JAZZ);
        }
    }

    /**
     * Реалізація List<T> через однозв’язний список.
     */
    static class MyLinkedList<T> implements List<T> {

        /**
         * Внутрішній клас — вузол списку.
         */
        private class Node {
            T value;
            Node next;
            Node(T value) { this.value = value; }
        }

        private Node head;     // перший елемент
        private int size = 0;  // кількість елементів


        /** Порожній список */
        public MyLinkedList() {}

        /** Список з одним елементом */
        public MyLinkedList(T element) {
            add(element);
        }

        /** Список зі стандартної колекції */
        public MyLinkedList(Collection<T> collection) {
            for (T el : collection) add(el);
        }

        private Node getNode(int index) {
            if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
            Node current = head;
            for (int i = 0; i < index; i++) current = current.next;
            return current;
        }

        @Override
        public int size() { return size; }

        @Override
        public boolean isEmpty() { return size == 0; }

        @Override
        public boolean add(T t) {
            Node newNode = new Node(t);
            if (head == null) {
                head = newNode;
            } else {
                Node last = getNode(size - 1);
                last.next = newNode;
            }
            size++;
            return true;
        }

        @Override
        public void add(int index, T element) {
            if (index < 0 || index > size) throw new IndexOutOfBoundsException();

            Node newNode = new Node(element);

            if (index == 0) {
                newNode.next = head;
                head = newNode;
            } else {
                Node prev = getNode(index - 1);
                newNode.next = prev.next;
                prev.next = newNode;
            }
            size++;
        }

        @Override
        public T get(int index) {
            return getNode(index).value;
        }

        @Override
        public T remove(int index) {
            if (index < 0 || index >= size) throw new IndexOutOfBoundsException();

            T removed;

            if (index == 0) {
                removed = head.value;
                head = head.next;
            } else {
                Node prev = getNode(index - 1);
                removed = prev.next.value;
                prev.next = prev.next.next;
            }
            size--;
            return removed;
        }

        @Override
        public boolean remove(Object o) {
            if (isEmpty()) return false;

            if (head.value.equals(o)) {
                head = head.next;
                size--;
                return true;
            }

            Node prev = head;
            Node curr = head.next;

            while (curr != null) {
                if (curr.value.equals(o)) {
                    prev.next = curr.next;
                    size--;
                    return true;
                }
                prev = curr;
                curr = curr.next;
            }
            return false;
        }

        @Override
        public boolean contains(Object o) {
            Node curr = head;
            while (curr != null) {
                if (curr.value.equals(o)) return true;
                curr = curr.next;
            }
            return false;
        }

        @Override public Iterator<T> iterator() {
            return new Iterator<>() {
                Node curr = head;
                @Override public boolean hasNext() { return curr != null; }
                @Override public T next() {
                    T v = curr.value;
                    curr = curr.next;
                    return v;
                }
            };
        }

        @Override public Object[] toArray() { throw new UnsupportedOperationException(); }
        @Override public <U> U[] toArray(U[] a) { throw new UnsupportedOperationException(); }
        @Override public boolean containsAll(Collection<?> c) { throw new UnsupportedOperationException(); }
        @Override public boolean addAll(Collection<? extends T> c) { c.forEach(this::add); return true; }
        @Override public boolean addAll(int index, Collection<? extends T> c) { throw new UnsupportedOperationException(); }
        @Override public boolean removeAll(Collection<?> c) { throw new UnsupportedOperationException(); }
        @Override public boolean retainAll(Collection<?> c) { throw new UnsupportedOperationException(); }
        @Override public void clear() { head = null; size = 0; }
        @Override public T set(int index, T element) { throw new UnsupportedOperationException(); }
        @Override public int indexOf(Object o) { throw new UnsupportedOperationException(); }
        @Override public int lastIndexOf(Object o) { throw new UnsupportedOperationException(); }
        @Override public ListIterator<T> listIterator() { throw new UnsupportedOperationException(); }
        @Override public ListIterator<T> listIterator(int index) { throw new UnsupportedOperationException(); }
        @Override public List<T> subList(int fromIndex, int toIndex) { throw new UnsupportedOperationException(); }
    }

    public static void main(String[] args) {

        MyLinkedList<MusicComposition> list = new MyLinkedList<>();

        list.add(new RockSong("Numb", 185));
        list.add(new PopSong("Blinding Lights", 200));
        list.add(new JazzSong("Autumn Leaves", 240));

        System.out.println("Список композицій:");
        for (MusicComposition m : list) {
            System.out.println(m);
        }

        System.out.println("\nДодаємо елемент на позицію 1...");
        list.add(1, new PopSong("Levitating", 205));

        System.out.println("\nПісля вставки:");
        list.forEach(System.out::println);

        System.out.println("\nВидаляємо елемент із позиції 0...");
        list.remove(0);

        System.out.println("\nПісля видалення:");
        list.forEach(System.out::println);
    }
}
