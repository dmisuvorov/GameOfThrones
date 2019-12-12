package ru.skillbranch.gameofthrones.util

fun <T> List<T>.dropLastUntil(predicate: (T) -> Boolean): List<T> {
    if (!isEmpty()) {
        val iterator = listIterator(size)
        while (iterator.hasPrevious()) {
            val previous = iterator.previous()
            if (predicate(previous)) {
                val nextIndex = iterator.nextIndex()
                return take(nextIndex)
            }
        }
    }
    return emptyList()
}