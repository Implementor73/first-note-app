package com.example.notesversion_1.toolbar

import androidx.annotation.MenuRes
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment

typealias ActionsListener = () -> Unit

interface ICustomToolbar : MenuProvider
{
    @get:MenuRes val menuRes : Int
    fun getCustomActionToolbar() : Map<Int,ActionsListener>
}
/*
 следует изменить функцию тулбара, что бы муня по параметру были у каждого свои события в меню и сама минюшка
 подумать как добавить условностей для меню, что бы категории от куда зашли у каждого был свой список
 по логике у архива и основного меню все одно и то же кроме одной функи ( добавить и убрать - в архив и из)
 и только у удаления добавить разность. В любом случае подумать над этим моментом.
 */