package com.example.artspace

import com.example.artspace.model.Gallery

object Data {
    fun getAllData(): ArrayList<Gallery>{
        return arrayListOf<Gallery>(
            Gallery(
                id = 1,
                title = "Life is a Winking Light in The Darkness",
                creator = "Qinni Art",
                added = "2023",
                imgRes = R.drawable.image_1
            ),
            Gallery(
                id = 2,
                title = "Under the City",
                creator = "Jinhwa Jang",
                added = "2023",
                imgRes = R.drawable.image_2
            ),
            Gallery(
                id = 3,
                title = "Snowy Hill",
                creator = "Matt Schu",
                added = "2023",
                imgRes = R.drawable.image_3
            )
        )
    }
}