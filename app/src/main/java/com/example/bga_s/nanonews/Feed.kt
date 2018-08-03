package com.example.bga_s.nanonews

import io.realm.RealmList
import io.realm.RealmObject


open class Feed(var items: RealmList<FeedItem> = RealmList<FeedItem>()):RealmObject()