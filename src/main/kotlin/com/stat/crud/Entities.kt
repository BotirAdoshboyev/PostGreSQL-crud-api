package com.stat.crud

import javax.persistence.*

@Entity(name = "state")
class State(
    @Column(unique = true) var stateName: String,
    var deleted: Boolean = false,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null
)

@Entity(name = "district")
class District(
    @Column(unique = true) var districtName: String,
    var deleted: Boolean = false,
    @ManyToOne var state: State,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null
)

@Entity(name = "resident")
class Resident(
    @Column(unique = true) var fullName: String,
    var gender: String,
    var birthYear: Int,
    var phoneNumber: String,
    var deleted: Boolean = false,
    @ManyToOne var district: District,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null
)

@Entity(name = "info")
class Info(
    var fullName: String,
    var districtName: String,
    var stateName: String,
    var phoneNumber: String,
    var birthYear: Int,
    var gender: String,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null
)