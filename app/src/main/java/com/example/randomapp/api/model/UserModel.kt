package com.example.randomapp.api.model

import java.io.Serializable

data class UserModel(
    val gender: String,
    val name: Name,
    val location: Location,
    val email: String,
    val login: Login,
    val dob: Dob,
    val registered: Registered,
    val phone: String,
    val cell: String,
    val id: Id,
    val picture: Picture,
    val nat: String
):Serializable

data class Name(
    val title: String,
    val first: String,
    val last: String
):Serializable

data class Location(
    val street: Street,
    val city: String,
    val state: String,
    val country: String,
    val postcode: String,
    val coordinates: Coordinates,
    val timezone: Timezone
):Serializable

data class Street(
    val number: Int,
    val name: String
):Serializable

data class Coordinates(
    val latitude: String,
    val longitude: String
):Serializable

data class Timezone(
    val offset: String,
    val description: String
):Serializable

data class Login(
    val uuid: String,
    val username: String,
    val password: String,
    val salt: String,
    val md5: String,
    val sha1: String,
    val sha256: String
):Serializable

data class Dob(
    val date: String,
    val age: Int
):Serializable

data class Registered(
    val date: String,
    val age: String,
):Serializable

data class Id(
    val name: String?,
    val value: String?
):Serializable

data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
):Serializable
