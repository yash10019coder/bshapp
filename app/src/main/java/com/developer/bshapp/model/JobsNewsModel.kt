package com.developer.bshapp.model

data class JobsNewsModel(
    val id:Int,
    val companyname:String,
    val jobtitle:String,
    val description:String,
    val state:String,
    val city:String,
    val district:String,
    val phone:String,
    val Qualification:String,
    val experience:String, val MinSalary: String, val MaxSalary: String,
    val date:String)