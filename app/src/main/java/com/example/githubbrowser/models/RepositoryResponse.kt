package com.example.githubbrowser.models

import java.io.Serializable

data class RepositoryResponse(
    val name:String,
    val description:String?,
    val html_url:String,
):Serializable
