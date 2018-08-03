package com.example.bga_s.nanonews

import io.realm.RealmObject

open class FeedItem (
    var title:String ="",
    var link:String = "",
    var description:String = "",
    var thumbnail:String =""
    ): RealmObject()