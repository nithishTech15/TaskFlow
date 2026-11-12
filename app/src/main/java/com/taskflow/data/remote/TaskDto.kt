package com.taskflow.data.remote

import com.google.gson.annotations.SerializedName

data class TaskDto(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("completed")
    val completed: Boolean?
)
