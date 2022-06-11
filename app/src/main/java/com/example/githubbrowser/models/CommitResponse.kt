package com.example.githubbrowser.models

import java.io.Serializable

data class CommitResponse(
    val commit: Commit,
    val author :User
) : Serializable
