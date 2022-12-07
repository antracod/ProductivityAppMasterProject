package com.leusoft.taskcore.utils

data class ChatChannel(val userIds: MutableList<String>) {
    constructor() : this(mutableListOf())
}