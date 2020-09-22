package com.zhangzheng.easyhttp.library


@Target(AnnotationTarget.CLASS)
annotation class URL(val value: String)

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class POST

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class GET

interface  IReq