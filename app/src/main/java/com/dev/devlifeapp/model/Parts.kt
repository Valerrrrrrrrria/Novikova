package com.dev.devlifeapp.model

data class Parts(
    var id: Long? = null,
    var description: String? = null,
    var votes: Int? = null,
    var author: String? = null,
    var date: String? = null,
    var gifURL: String? = null,        // "http://static.devli.ru/public/images/gifs/202109/518d3a2b-42eb-4b05-8e81-a5329a1d8288.gif"
    var gifSize: Long? = null,
    var previewURL: String? = null, // "https://static.devli.ru/public/images/previews/202109/518d3a2b-42eb-4b05-8e81-a5329a1d8288.jpg"
    var videoURL: String? = null,    // "http://static.devli.ru/public/images/v/202109/518d3a2b-42eb-4b05-8e81-a5329a1d8288.mp4"
    var videoPath: String? = null,  //	"/public/images/v/202109/518d3a2b-42eb-4b05-8e81-a5329a1d8288.mp4"
    var videoSize: String? = null,
    var type: String? = null,       //	"gif"
    var width: Int? = null,         //	"480"
    var height: Int? = null,        //	"592"
    var commentsCount: Int? = null, //	1
    var fileSize: Long? = null,    //	8031076
    var canVote: Boolean? = null
)