package com.m2f.sherpanytest.utils

import android.view.View
import android.view.ViewGroup


fun <V : View?> ViewGroup.findChildrenByClass(clazz: Class<V>): Collection<V> {
    return gatherChildrenByClass(this, clazz, ArrayList())
}

private fun <V : View?> gatherChildrenByClass(viewGroup: ViewGroup, clazz: Class<V>, childrenFound: MutableCollection<V>): Collection<V> {
    for (i in 0 until viewGroup.childCount) {
        val child: View = viewGroup.getChildAt(i)
        if (clazz.isAssignableFrom(child.javaClass)) {
            childrenFound.add(child as V)
        }
        if (child is ViewGroup) {
            gatherChildrenByClass(child, clazz, childrenFound)
        }
    }
    return childrenFound
}