package deno.springboot.armut.datasource

import deno.springboot.armut.model.Bank

interface BankDataSource {
    // abstract
    // just defines what kind
    // of functionality we except
    // from our data source

    fun retrieveBanks():Collection<Bank>
    fun retrieveBank(accountNumber: String): Bank
    fun createBank(bank: Bank): Bank
    fun updateBank(bank: Bank): Bank
    fun deleteBank(accountNumber: String)
}