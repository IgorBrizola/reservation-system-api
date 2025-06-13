package com.system.reservation.core.useCases

import com.system.reservation.core.domain.exceptions.BusinessException
import com.system.reservation.core.ports.output.TablesOutPutPort
import com.system.reservation.util.UtilsTest.Companion.buildTables
import com.system.reservation.util.UtilsTest.Companion.buildTablesEntity
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class TablesUseCaseTest {
    @Mock
    private lateinit var tablesOutPutPort: TablesOutPutPort

    @InjectMocks
    private lateinit var tablesUseCase: TablesUseCase

    @Test
    fun `should create a new table`() {
        val table = buildTables()
        val savedTable = buildTablesEntity()

        whenever(tablesOutPutPort.save(table)).thenReturn(savedTable)

        val result = tablesUseCase.createNewTable(table)

        val expected = Unit

        verify(tablesOutPutPort).save(table)
        assertEquals(expected, result)
    }

    @Test
    fun `should throw exception when the name of the table is invalid!`() {
        val table = buildTables(nameInvalid = true)

        val result = assertThrows<BusinessException> { tablesUseCase.createNewTable(table) }

        val expected = "Table does not follow name default, replace ${table.name} to ${table.name.uppercase()}"

        assertEquals(expected, result.message)
    }

    @Test
    fun `should throw exception when the name of the table already exists!`() {
        val table = buildTables()

        whenever(tablesOutPutPort.existsTablesByName(any())).thenReturn(true)

        val result = assertThrows<BusinessException> { tablesUseCase.createNewTable(table) }

        val expected = "Table already exist with is name - ${table.name}"

        assertEquals(expected, result.message)
    }

    @Test
    fun `should list all tables`() {
        val allTables = listOf(buildTables())

        whenever(tablesOutPutPort.findAllTables(eq(null))).thenReturn(allTables)

        val result = tablesUseCase.listAllTables(statusTableIds = null)

        assertEquals(allTables, result)
    }
}
