package com.stat.crud

import org.springframework.web.bind.annotation.*
import javax.websocket.server.PathParam

@RestController
@RequestMapping("state")
class StateController(
    private val stateService: StateService
){
    @PostMapping
    fun add(@RequestBody dto: StateDto) = stateService.add(dto)

    @GetMapping
    fun findAll() = stateService.findAll()

    @GetMapping("find-by-id/{id}")
    fun findById(@PathVariable id: Long) = stateService.findByIdAndDeletedFalse(id)

    @GetMapping("find-by-name")
    fun findByName(@RequestParam stateName: String) = stateService.findByStateNameAndDeletedFalse(stateName)

    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @RequestBody body: StateDto) = stateService.update(id, body)

    @PutMapping("delete/{id}")
    fun delete(@PathVariable id: Long) = stateService.delete(id)
}

@RestController
@RequestMapping("district")
class DistrictController(
    private val districtService: DistrictService
){
    @PostMapping
    fun add(@RequestBody addDistrictDto: AddDistrictDto) = districtService.add(addDistrictDto)

    @GetMapping
    fun findAll() = districtService.findAll()

    @GetMapping("find-by-id/{id}")
    fun findById(@PathVariable id: Long) = districtService.findByIdAndDeletedFalse(id)

    @GetMapping("find-by-name")
    fun findByName(@RequestParam districtName: String) = districtService.findByDistrictNameAndDeletedFalse(districtName)

    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @RequestBody body: AddDistrictDto) = districtService.update(id, body)

    @PutMapping("delete/{id}")
    fun delete(@PathVariable id: Long) = districtService.delete(id)
}

@RestController
@RequestMapping("resident")
class ResidentController(
    private val residentService: ResidentService
){
    @PostMapping
    fun add(@RequestBody addResidentDto: AddResidentDto) = residentService.add(addResidentDto)

    @GetMapping
    fun findAll() = residentService.findAll()

    @GetMapping("{id}")
    fun findById(@PathVariable id: Long) = residentService.findById(id)

    @GetMapping("find-by-full-name")
    fun findAllByFullName(@RequestParam fullName: String) = residentService.findAllByFullName(fullName)

    @PutMapping("move-to/{id}")
    fun moveTo(@PathVariable id: Long, @RequestParam districtId: Long) = residentService.moveTo(id, districtId)

    @PutMapping("update/{id}")
    fun update(@PathVariable id: Long, @RequestBody body: AddResidentDto) = residentService.update(id, body)

    @PutMapping("delete/{id}")
    fun delete(@PathVariable id: Long) = residentService.delete(id)
}

@RestController
@RequestMapping("full-info")
class FullInfoController(
    private val infoService: InfoService
){
    @GetMapping
    fun findFullInfo() = infoService.findFullInfo()

    @GetMapping("count")
    fun countAll() = infoService.countAll()

    @GetMapping("count-all-by-district-id/{districtId}")
    fun countAllByDistrictId(@PathVariable districtId: Long) = infoService.countAllByDistrictId(districtId)

    @GetMapping("count-all-by-state-id/{stateId}")
    fun countAllByStateId(@PathVariable stateId: Long) = infoService.countAllByStateId(stateId)
}