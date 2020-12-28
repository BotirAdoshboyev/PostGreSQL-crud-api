package com.stat.crud

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface StateRepository : JpaRepository<State, Long> {
    fun findByStateNameAndDeletedFalse(stateName: String) : StateDto?
    fun findByIdAndDeletedFalse(id: Long) : State?
    fun findAllByDeletedFalse() : List<State>
}

interface DistrictRepository : JpaRepository<District, Long> {
    fun findByDistrictNameAndDeletedFalse(districtName: String) : District?
    fun findByIdAndDeletedFalse(id: Long) : District?
    fun findAllByDeletedFalse() : List<District>
}

interface ResidentRepository : JpaRepository<Resident, Long> {
    fun findAllByFullNameAndDeletedFalse(fullName: String) : List<Resident>
    fun findByIdAndDeletedFalse(id: Long) : Resident?
    fun findAllByDeletedFalse() : List<Resident>
}

interface FullInfoRepository : JpaRepository<Info, Long>{
    @Query(
        value = """select r.id, r.full_name, r.phone_number, r.birth_year, r.gender, d.district_name, s.state_name
            from resident r
            join district d on r.district_id = d.id
            join state s on d.state_id = s.id
            where r.deleted = false
            order by r.id""", nativeQuery = true
    )
    fun findForFullResidentInfo() : List<Info>

    @Query(
        value = """select count(r)
            from resident r
            where r.deleted = false""", nativeQuery = true
    )
    fun countAll() : Int

    @Query(
        value = """select count(r)
            from resident r
            where r.deleted = false and r.district_id = ?1
        """, nativeQuery = true
    )
    fun countAllByDistrictId(districtId: Long) : Int

    @Query(
        value = """select count(r)
            from resident r, district d
            where r.deleted = false and d.state_id = ?1 and r.district_id = d.id
        """, nativeQuery = true
    )
    fun countAllByStateId(stateId: Long) : Int
}