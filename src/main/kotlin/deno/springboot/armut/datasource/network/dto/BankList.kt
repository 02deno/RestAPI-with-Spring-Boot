package deno.springboot.armut.datasource.network.dto

import deno.springboot.armut.model.Bank

data class BankList (
    val results : Collection<Bank>
)