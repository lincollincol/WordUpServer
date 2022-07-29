package com.linc.extensions

inline fun <T, K> Map<T, K>.forEachIndexed(action: (index: Int, Map.Entry<T, K>) -> Unit): Unit {
    var index = 0
    for (item in this) action(index++, item)
}
