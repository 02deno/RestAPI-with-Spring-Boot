package deno.springboot.armut.datasource.mock

import deno.springboot.armut.datasource.BankDataSource
import deno.springboot.armut.model.Bank
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException

@Repository("mock")
class MockBankDataSource : BankDataSource{
    val banks = mutableListOf<Bank>(
        Bank("1234",3.1,17),
        Bank("7877",39.8,0),
        Bank("1233",0.0,6)
    )
    override fun retrieveBanks(): Collection<Bank> = banks
    override fun retrieveBank(accountNumber: String): Bank =
        banks.firstOrNull() { it.accountNumber == accountNumber}
        // if not found * not such element exception
            ?: throw NoSuchElementException("Could not find a bank with account number $accountNumber")

    override fun createBank(bank: Bank): Bank {
        if(banks.any {it.accountNumber == bank.accountNumber}) {
            throw IllegalArgumentException("Bank with account number ${bank.accountNumber} already exists")
        }
        banks.add(bank)
        return bank
    }

    override fun updateBank(bank: Bank): Bank {
        val currentBank = banks.firstOrNull() { it.accountNumber == bank.accountNumber}
        // if not found * not such element exception
            ?: throw NoSuchElementException("Could not find a bank with account number ${bank.accountNumber}")

        banks.remove(currentBank)
        banks.add(bank)
        return bank
    }

    override fun deleteBank(accountNumber: String) {
        val currentBank = banks.firstOrNull() { it.accountNumber == accountNumber}
        // if not found * not such element exception
            ?: throw NoSuchElementException("Could not find a bank with account number ${accountNumber}")

        banks.remove(currentBank)
    }


}