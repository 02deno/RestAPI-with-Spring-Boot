package deno.springboot.armut.datasource.mock

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MockBankDataSourceTest {
    private val mockDataSource = MockBankDataSource()


    @Test
    fun `should provide a collection of banks`() {
        // when
        val banks = mockDataSource.retrieveBanks()

        // then
        //assertThat(banks).isNotEmpty -not needed,aşağıdaki zaten 0 mu diye de bakıyor
        assertThat(banks.size).isGreaterThanOrEqualTo(3)
    }
    
    @Test
    fun `should provide some mock data`() {
        // when3
        val banks = mockDataSource.retrieveBanks()
        
        
        // then
        assertThat(banks).allMatch{ it.accountNumber.isNotBlank() }
        assertThat(banks).anyMatch{ it.trust != 0.0 }
        assertThat(banks).anyMatch{ it.transactionFee != 0 }
        // anyMatch : if at least one example or in our case
        // one bank unequal to 0 there is no error
        
    }

    @Test
    fun `should provide unique ids `() {
        // when
        val banks = mockDataSource.retrieveBanks()
        val ids = mutableSetOf<String>()
        for (bank in banks) {
            ids.add(bank.accountNumber)
            //println(bank.accountNumber)
        }
        // then
        assertThat(banks.size).isEqualTo(ids.size)
    }
}