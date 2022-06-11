package com.example.githubbrowser.models

import java.io.Serializable

data class Repository(
    val name:String,
    val description:String?,
    val html_url:String,
    val owner:String?,
):Serializable
