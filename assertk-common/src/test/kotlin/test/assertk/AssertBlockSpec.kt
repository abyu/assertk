package test.assertk

import assertk.assert
import assertk.assertions.isEqualTo
import assertk.assertions.isNegative
import assertk.assertions.isPositive
import assertk.assertions.message
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails


class AssertBlockSpec_On_a_returns_value_assert_block_value {
    val subject = assert {
        1 + 1
    }

    @Test
    fun it_should_pass_a_successful_returns_value_assertion() {
        subject.returnedValue {
            isEqualTo(2)
        }
    }

    @Test
    fun it_should_fail_an_unsuccessful_return_value_assertion() {
        val error = assertFails {
            subject.returnedValue {
                isNegative()
            }
        }
        assertEquals("expected to be negative but was:<2>", error.message)
    }

    @Test
    fun it_should_fail_a_throws_error_assertion() {
        val error = assertFails {
            subject.thrownError {
                message().isEqualTo("error")
            }
        }
        assertEquals("expected exception but was:<2>", error.message)
    }

    @Test
    fun it_should_pass_doesNotThrowAnyException() {
        subject.doesNotThrowAnyException()
    }
}

class AssertBlockSpec_On_a_throws_error_assert_block {
    val subject = assert {
        throw Exception("test")
    }

    @Test
    fun it_should_pass_a_successful_throws_error_assertion() {
        subject.thrownError {
            message().isEqualTo("test")
        }
    }

    @Test
    fun it_should_fail_a_unsuccessful_throws_error_assertion() {
        val error = assertFails {
            subject.thrownError {
                message().isEqualTo("wrong")
            }
        }
        assertEquals(
            "expected [message]:<\"[wrong]\"> but was:<\"[test]\"> (${exceptionPackageName}Exception: test)",
            error.message
        )
    }

    @Test
    fun it_should_fail_a_returns_value_assertion() {
        val error = assertFails {
            subject.returnedValue {
                isPositive()
            }
        }
        assertEquals("expected value but threw:<${exceptionPackageName}Exception: test>", error.message)
    }

    @Test
    fun it_should_fail_doesNotThrowAnyException (){
        val error = assertFails {
            subject.doesNotThrowAnyException()
        }

        assertEquals("expected to not throw an exception but threw:<${exceptionPackageName}Exception: test>", error.message)
    }
}
