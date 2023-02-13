package com.example.chattingappk

class Messages {
    var messages: String?=null
    var sender: String?=null

    constructor(){}
    constructor(messages: String?, sender: String?) {
        this.messages = messages
        this.sender = sender
    }


}