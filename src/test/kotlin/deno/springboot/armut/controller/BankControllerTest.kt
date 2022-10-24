package deno.springboot.armut.controller

import com.fasterxml.jackson.databind.ObjectMapper
import deno.springboot.armut.model.Bank
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor(
    val mockMvc : MockMvc,
    val objectMapper: ObjectMapper

) {

    val baseUrl = "/api/banks"

    @Nested
    @DisplayName("GET /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks{
        @Test
        fun `should return all banks`() {
            // when/then
            // when
            mockMvc.get(baseUrl)
                .andDo { print() }

                // then
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].account_number" ) {value("1234")}
                    jsonPath("$[1].trust" ) {value(39.8)}
                    jsonPath("$[2].default_transaction_fee" ) {value(6)}
                }

        }
    }

       @Nested
       @DisplayName("GET /api/banks/{accountNumber}")
       @TestInstance(TestInstance.Lifecycle.PER_CLASS)
       inner class GetBank{
           @Test
           fun `should return the bank with the given account number`() {
               // given
               val accountNumber = 1234
    
               // when/then
               mockMvc.get("$baseUrl/$accountNumber")
                   .andDo { print() }
                   .andExpect {
                       status { isOk() }
                       content { contentType(MediaType.APPLICATION_JSON) }
                       // another way : create an expected bank object
                       // compare the expected bank against the returned bank
                       // with json inside of content
                       jsonPath("$.trust") {value("3.1")}
                       jsonPath("$.default_transaction_fee") {value("17")}
    
                   }
    
           }
    
           @Test
           fun `should return NOT FOUND if the account number does not exist`() {
               // given
               val accountNumber = "does_not_exist"
    
               // when - then
               mockMvc.get("$baseUrl/$accountNumber")
                   .andDo { print() }
                   .andExpect { status { isNotFound() } }
    
    
           }
       }


    @Nested
    @DisplayName("POST /api/banks/")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class PostNewBank {
        @Test
        fun `should add the new bank`() {
            // given
            val newBank = Bank("apo",34.3,4)

            // when
            val performPost = mockMvc.post("$baseUrl/") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }

            //then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        // instead of jsonpath
                        // instead of asserting each property of object by itself
                        json(objectMapper.writeValueAsString(newBank))
                    }

                }

            mockMvc.get("$baseUrl/${newBank.accountNumber}")
                .andExpect { content { json(objectMapper.writeValueAsString(newBank)) } }
        }

        @Test
        fun `should return BAD REQUEST if bank with given account number already exists`() {
            // given
            val invalidBank = Bank("1234",1.0,1)

            // when
            val performPost = mockMvc.post("$baseUrl/") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }


            // then
            performPost
                .andDo { print() }
                .andExpect { status { isBadRequest() } }

        }
    }

    @Nested
    @DisplayName("PATCH /api/banks")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class PatchExistingBank{
        @Test
        fun `should update an existing bank`() {
            // given
            val updatedBank = Bank("1234",1.4,18)

            // when
            val performPatchRequest = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedBank)
            }

            // then
            performPatchRequest
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(updatedBank))
                    }
                }

            // bank informations really updated,not tricked us by just returning our original object
            mockMvc.get("$baseUrl/${updatedBank.accountNumber}")
                .andExpect { content { json(objectMapper.writeValueAsString(updatedBank)) } }

        }

        @Test
        fun `should return BAD REQUEST if no bank with given account number exists`() {
            // given
            val invalidBank = Bank("does_not_exist",1.0,1)

            // when
            val performPatchRequest = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }

            // then
            performPatchRequest
                .andDo { print() }
                .andExpect { status { isNotFound() } }
            //we except content to be empty too

        }
    }

    @Nested
    @DisplayName("DELETE /api/banks/{accountNumber}")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class DeleteExistingBank{
        @Test
        @DirtiesContext
        fun `should delete the bank with the given account number`() {
            // given
            val accountNumber = 1234

            // when/then
            mockMvc.delete("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect { status { isNoContent() }
                }

            mockMvc.get("$baseUrl/$accountNumber")
                .andExpect { status { isNotFound() } }

        }

        @Test
        fun `should return NOT FOUND if no bank with given account number exists`() {
            // given
            val invalidAccountNumber = "does_not_exist"

            // when/then
            mockMvc.delete("$baseUrl/$invalidAccountNumber")
                .andDo { print() }
                .andExpect { status{ isNotFound() } }

        }
    }
    

}