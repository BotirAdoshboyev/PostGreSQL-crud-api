package com.stat.crud

data class StateDto(
    var stateName: String,
    var id: Long? = null
){
    companion object{
        fun toDto(state: State) = state.run { StateDto(stateName, id) }
    }
}

data class DistrictDto(
    var districtName: String,
    var state: StateDto,
    var id: Long? = null
){
    companion object{
        fun toDto(district: District) = district.run { DistrictDto(districtName, StateDto.toDto(state), id) }
    }
}

data class AddDistrictDto(var districtName: String, var stateId: Long)

data class ResidentDto(
    var fullName: String,
    var gender: String,
    var birthYear: Int,
    var phoneNumber: String,
    var district: DistrictDto,
    var id: Long? = null
){
    companion object{
        fun toDto(resident: Resident) = resident.run {
            ResidentDto(fullName, gender, birthYear, phoneNumber, DistrictDto.toDto(district), id) }
    }
}

data class AddResidentDto(var fullName: String, var gender: String, var birthYear: Int, var phoneNumber: String, var districtId: Long)

data class InfoDto(
    var fullName: String,
    var districtName: String,
    var stateName: String,
    var phoneNumber: String,
    var birthYear: Int,
    var gender: String,
    var id: Long? = null
){
    companion object{
        fun toDto(info: Info) = info.run { InfoDto(fullName, districtName, stateName, phoneNumber, birthYear, gender, id) }
    }
}