package dev.carrion.domain

import dev.carrion.domain.entities.Rate
import dev.carrion.domain.entities.Transaction
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class ConversionsKtTest{

    lateinit var transaction: Transaction
    lateinit var listRates: List<Rate>

    private val rate1 = Rate("CAD", "EUR", BigDecimal(2))
    private val rate2 = Rate("EUR", "CAD", BigDecimal(0.5))
    private val rate3 = Rate("CAD", "AUD", BigDecimal(2.0))
    private val rate4 = Rate("AUD", "CAD", BigDecimal(0.5))
    private val rate5 = Rate("EUR", "USD", BigDecimal(0.5))
    private val rate6 = Rate("USD", "EUR", BigDecimal(2.0))

    @Before
    fun initTests(){
        transaction = Transaction("001", BigDecimal(2.0), "EUR")
        listRates = listOf(rate1,rate2,rate3,rate4,rate5,rate6)
    }

    @Test
    fun `test getAmountForCurrency method returns direct conversion from list`(){
        val toCurrency = "CAD"

        val amount = getAmountForCurrency(transaction, toCurrency, listRates)


        assert(amount?.compareTo(BigDecimal(1.0)) == 0)
    }

    @Test
    fun `test getAmountForCurrency method returns created conversion level 1`() {
        val toCurrency = "AUD"

        val amount = getAmountForCurrency(transaction, toCurrency, listRates)

        assert(amount?.compareTo(BigDecimal(2.0 * 0.5 * 2.0)) == 0)
    }

    @Test
    fun `test getAmountForCurrency method returns created conversion level 2`() {

        val transaction = Transaction("001", BigDecimal(2.0), "USD")

        val toCurrency = "AUD"

        val amount = getAmountForCurrency(transaction, toCurrency, listRates)

        assert(amount?.compareTo(BigDecimal(2.0 * 2.0 * 0.5 * 2.0)) == 0)
    }

    @Test
    fun `test getAmountForCurrency method returns null when no conversion found`() {
        val toCurrency = "JPN"

        val amount = getAmountForCurrency(transaction, toCurrency, listRates)

        assert(amount == null)
    }
}