package com.example.todoapplicationexample.notes

import android.graphics.drawable.Drawable
import com.example.todoapplicationexample.Constants
import java.io.Serializable


data class Note(
    val title: String,
    val text: String?,
    val imageList: List<Drawable>?
) : Serializable { // TODO: question( How can I use Parcelable with imageList? Here too match problems with Parcelable and @Parcelize )

    companion object {

        fun generateSimpleList(count: Int): List<Note> {
            val list = mutableListOf<Note>()
            for (i in 0 until count) {
                list.add(Note("Note ${i + 1}", Constants.LOREM_IPSUM_5, null))
            }
            return list.toList()
        }

    }

}
