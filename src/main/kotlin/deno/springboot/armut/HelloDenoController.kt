package deno.springboot.armut

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
//handling rest requests
@RequestMapping("api/deno") //parent path
//define the endpoints in your class

//this means this restcontroller
//is responsible for any endpoints
//that start with api/deno
//localhost:8080/api/deno

/*
get endpoint : to fetch some data

 */
class HelloDenoController {
    @GetMapping//("springboot") appended == localhost:8080/api/deno /springboot
    fun helloDeno():String = "Hello,this is a REST endpoint!"
}