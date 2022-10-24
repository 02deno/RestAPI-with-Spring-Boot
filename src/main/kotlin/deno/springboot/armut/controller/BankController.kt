package deno.springboot.armut.controller

import deno.springboot.armut.model.Bank
import deno.springboot.armut.service.BankService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/banks")
class BankController(private val service:BankService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e : NoSuchElementException) : ResponseEntity <String> =
        ResponseEntity(e.message,HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e : IllegalArgumentException) : ResponseEntity <String> =
        ResponseEntity(e.message,HttpStatus.BAD_REQUEST)


    //service variable is private
    //because it is only used inside of this controller
    @GetMapping
    fun getAllBanks():Collection<Bank> = service.getBanks()

    @GetMapping("/{accountNumber}")
    fun getBank(@PathVariable accountNumber : String) : Bank= service.getBank(accountNumber)
        // first step , not go ahead of yourself= "You want data about $accountNumber"

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun addBank(@RequestBody bank:Bank) : Bank = service.addBank(bank)

    @PatchMapping
    fun updateBank(@RequestBody bank:Bank):Bank = service.updateBank(bank)

    @DeleteMapping("/{accountNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBank(@PathVariable accountNumber: String) : Unit = service.deleteBank(accountNumber)
}