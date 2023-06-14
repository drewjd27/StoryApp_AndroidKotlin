package com.example.storyapp

import com.example.storyapp.data.response.StoryResponse

object DataDummy {

    fun generateDummyStoryResponse(): List<StoryResponse> {
        val items: MutableList<StoryResponse> = arrayListOf()
        for (i in 0..100) {
            val quote = StoryResponse(
                i.toString(),
                "createdAt + $i",
                "desc $i",
                -14.4237043,
                127.8912792,
                "user $i",
                "photoUrl $i",
            )
            items.add(quote)
        }
        return items
    }
}