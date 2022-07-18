package com.example.journeysecurechatv2

import java.text.SimpleDateFormat
import java.util.*


class MyUtils {

    companion object {
        var currentDateTimeString = ""
    }

    var d: Date = Date()
    var sdf: SimpleDateFormat = SimpleDateFormat("hh:mm a")
    var currentDateTimeString: String = sdf.format(d)



}