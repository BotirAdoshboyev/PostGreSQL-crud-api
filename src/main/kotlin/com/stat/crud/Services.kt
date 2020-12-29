package com.stat.crud

import org.springframework.stereotype.Service

interface StateService {
    fun add(dto: StateDto) : StateDto
    fun findAll() : List<StateDto>
    fun findByIdAndDeletedFalse(id: Long) : StateDto?
    fun findByStateNameAndDeletedFalse(stateName: String) : StateDto?
    fun update(id: Long, body: StateDto) : StateDto
    fun delete(id: Long) : String
}

@Service
class StateServiceImp(
    private val stateRepository: StateRepository
) : StateService {
    override fun add(dto: StateDto) = StateDto.toDto(stateRepository.save(State(dto.stateName)))

    override fun findAll() = stateRepository.findAllByDeletedFalse().map { StateDto.toDto(it) }

    override fun findByIdAndDeletedFalse(id: Long): StateDto? {
        stateRepository.findByIdAndDeletedFalse(id)?.let { state ->
            return StateDto.toDto(State(stateName = state.stateName, id = state.id))
        }
        throw NullPointerException("No State was found with ID $id")
    }

    override fun findByStateNameAndDeletedFalse(stateName: String) : StateDto? {
        stateRepository.findByStateNameAndDeletedFalse(stateName)?.let {
            return StateDto.toDto(State(stateName = it.stateName, id = it.id))
        }
        throw NullPointerException("No State's been found")
    }

    override fun update(id: Long, body: StateDto): StateDto {
        stateRepository.findByIdAndDeletedFalse(id)?.let { state ->
            state.stateName = body.stateName
            stateRepository.save(state)
            return StateDto.toDto(state)
        }
        throw NullPointerException("No State's been found")
    }

    override fun delete(id: Long): String {
        stateRepository.findByIdAndDeletedFalse(id)?.let { state ->
            state.deleted = true
            stateRepository.save(state)
            return "State was successfully deleted."
        }
        throw NullPointerException("No State's been found")
    }
}

interface DistrictService {
    fun add(dto: AddDistrictDto) : DistrictDto
    fun findAll() : List<DistrictDto>
    fun findByIdAndDeletedFalse(id: Long) : DistrictDto?
    fun findByDistrictNameAndDeletedFalse(districtName: String) : DistrictDto?
    fun update(id: Long, body: AddDistrictDto) : DistrictDto
    fun delete(id: Long) : String
}

@Service
class DistrictServiceImp(
    private val districtRepository: DistrictRepository,
    private val stateRepository: StateRepository
) : DistrictService {
    override fun add(dto: AddDistrictDto) : DistrictDto {
        stateRepository.findByIdAndDeletedFalse(dto.stateId)?.let { state->
            val district = districtRepository.save(District(districtName = dto.districtName, state = state))
            return DistrictDto.toDto(district)
        }
        throw NullPointerException("No State's been found")
    }

    override fun findAll() = districtRepository.findAllByDeletedFalse().map { district ->
        DistrictDto.toDto(District(districtName = district.districtName, state = district.state, id = district.id)) }

    override fun findByIdAndDeletedFalse(id: Long): DistrictDto? {
        districtRepository.findByIdAndDeletedFalse(id)?.let {
            return DistrictDto.toDto(it)
        }
        throw NullPointerException("No State's been found")
    }

    override fun findByDistrictNameAndDeletedFalse(districtName: String): DistrictDto? {
        districtRepository.findByDistrictNameAndDeletedFalse(districtName)?.let { return DistrictDto.toDto(it) }
        throw NullPointerException("No District's been found")
    }

    override fun update(id: Long, body: AddDistrictDto): DistrictDto {
        val state = stateRepository.findByIdAndDeletedFalse(body.stateId)?:throw NullPointerException("No State's been found")
        districtRepository.findByIdAndDeletedFalse(id)?.let { district->
            district.districtName = body.districtName
            district.state = state
            districtRepository.save(district)
            return DistrictDto.toDto(district)
        }
        throw NullPointerException("No District's been found")
    }

    override fun delete(id: Long): String {
        districtRepository.findByIdAndDeletedFalse(id)?.let { district->
            district.deleted = true
            districtRepository.save(district)
            return "District was successfully deleted."
        }
        throw NullPointerException("No District's been found")
    }
}

interface ResidentService {
    fun add(dto: AddResidentDto) : ResidentDto
    fun findAll() : List<ResidentDto>
    fun findById(id: Long) : ResidentDto
    fun findAllByFullName(fullName: String) : List<ResidentDto>
    fun moveTo(id: Long, districtId: Long) : ResidentDto
    fun update(id: Long, body: AddResidentDto) : ResidentDto
    fun delete(id: Long) : String
}

@Service
class ResidentServiceImp(
    private val residentRepository: ResidentRepository,
    private val districtRepository: DistrictRepository
) : ResidentService {
    override fun add(dto: AddResidentDto): ResidentDto {
        val district = districtRepository.findByIdAndDeletedFalse(dto.districtId)?:throw NullPointerException("No District's been found")
        val resident = residentRepository.save(Resident(
            fullName = dto.fullName,
            gender = dto.gender,
            birthYear = dto.birthYear,
            phoneNumber = dto.phoneNumber,
            district = district
        ))
        return ResidentDto.toDto(resident)
    }

    override fun findAll() = residentRepository.findAllByDeletedFalse().map { ResidentDto.toDto(it) }

    override fun findById(id: Long): ResidentDto {
        residentRepository.findByIdAndDeletedFalse(id)?.let { return ResidentDto.toDto(it) }
        throw NullPointerException("No Resident's been found")
    }

    override fun findAllByFullName(fullName: String) = residentRepository.findAllByFullNameAndDeletedFalse(fullName).map { ResidentDto.toDto(it) }

    override fun moveTo(id: Long, districtId: Long): ResidentDto {
        val district = districtRepository.findByIdAndDeletedFalse(districtId)?:throw NullPointerException("No District's been found")
        residentRepository.findByIdAndDeletedFalse(id)?.let { resident ->
            resident.district = district
            residentRepository.save(resident)
            return ResidentDto.toDto(resident)
        }
        throw NullPointerException("No Resident's been found")
    }

    override fun update(id: Long, body: AddResidentDto): ResidentDto {
        residentRepository.findByIdAndDeletedFalse(id)?.let { resident ->
            resident.fullName = body.fullName
            resident.birthYear = body.birthYear
            resident.gender = body.gender
            resident.phoneNumber = body.phoneNumber
            residentRepository.save(resident)
            return ResidentDto.toDto(resident)
        }
        throw NullPointerException("No Resident's been found")
    }

    override fun delete(id: Long): String {
        residentRepository.findByIdAndDeletedFalse(id)?.let {
            it.deleted = true
            residentRepository.save(it)
            return "Resident was successfully deleted."
        }
        throw NullPointerException("No Resident's been found")
    }
}

interface InfoService {
    fun findFullInfo(): List<InfoDto>
    fun countAll() : Int
    fun countAllByDistrictId(districtId: Long) : Int
    fun countAllByStateId(stateId: Long) : Int
}

@Service
class InfoServiceImpl(
    private val fullInfoRepository: FullInfoRepository
) : InfoService {
    override fun findFullInfo() = fullInfoRepository.findForFullResidentInfo().map { InfoDto.toDto(it) }

    override fun countAll() = fullInfoRepository.countAll()

    override fun countAllByDistrictId(districtId: Long) = fullInfoRepository.countAllByDistrictId(districtId)

    override fun countAllByStateId(stateId: Long) = fullInfoRepository.countAllByStateId(stateId)
}
