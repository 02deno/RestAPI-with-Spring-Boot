package deno.springboot.armut.service

import deno.springboot.armut.datasource.BankDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BankServiceTest {

    private val dataSource:BankDataSource = mockk(relaxed = true)
    private val bankService = BankService(dataSource)
    @Test
    fun `should call its data source to retrieve banks`() {

        // we can now verify that when we call
        // the bankService.getBanks() it actually
        // calls this data source so thats what we want to
        // test in this test case.
        // we want to make sure or we want to verify
        // that our data source.retrieveBanks has been
        // called


        // given
        //every {dataSource.retrieveBanks()} returns emptyList()


        // when
        //we dont need the return value
        bankService.getBanks()

        // then
        verify(exactly = 1) {dataSource.retrieveBanks()}

    }
}