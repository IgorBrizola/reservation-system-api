package com.system.reservation.adpters.repository

import com.system.reservation.adpters.repository.jpa.TablesJpaRepository
import com.system.reservation.core.ports.output.TablesOutPutPort
import org.springframework.stereotype.Repository

@Repository
class ManageTablesRepository(
    private val tablesJpaRepository: TablesJpaRepository
): TablesOutPutPort {
}