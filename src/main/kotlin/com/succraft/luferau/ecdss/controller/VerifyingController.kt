package com.succraft.luferau.ecdss.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class VerifyingController {

    @PostMapping("/verify")
    fun signing(@RequestBody body: String) =
            body
}