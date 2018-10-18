package org.workfort.base.util


/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 10/17/2018 at 6:29 PM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Purpose:
*  *
*  * Last edited by : Md. Azizul Islam on 10/17/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/

sealed class ScreenState {
    object ERROR : ScreenState()
    object LOADING : ScreenState()
    data class DataValue(val name: String): ScreenState()
}