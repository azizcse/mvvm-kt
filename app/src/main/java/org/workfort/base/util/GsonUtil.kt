package org.workfort.base.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.lang.reflect.Type
import java.util.ArrayList


/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 10/29/2018 at 3:22 PM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Purpose:
*  *
*  * Last edited by : Md. Azizul Islam on 10/29/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/

object GsonUtil {
    private val mGson = Gson()

    /**
     * This method converts a model class object to json String
     *
     * @param singleObject an object of model class
     * @return [String] the desired json
     *
     * i.e: String json = GsonUtils.toJson(singleObject)
     * */
    fun <T> toJson(singleObject: T): String? {
        val type = object : TypeToken<T>() {}.type

        try {
            return mGson.toJson(singleObject, type)
        } catch (e: Exception) {

        }

        return null
    }

    /**
     * This method converts a objects of model class object to json String
     *
     * @param objects a objects of model class object
     * @return [String] the desired json
     *
     * i.e: String json = GsonUtils.toJson(objects)
     * */
    fun <T> toJson(objects: List<T>): String? {
        val type = object : TypeToken<List<T>>() {}.type

        try {
            return mGson.toJson(objects, type)
        } catch (e: Exception) {

        }

        return null
    }

    /**
     * This method converts a map of model class object to json String
     *
     * @param objects an map of model class objects
     * @return [String] the desired json
     *
     * i.e: String json = GsonUtils.toJson(objects)
     * */
    fun <T1, T2> toJson(objects: Map<T1, T2>): String? {
        val type = object : TypeToken<Map<T1, T2>>() {}.type

        try {
            return mGson.toJson(objects, type)
        } catch (e: Exception) {

        }

        return null
    }

    /**
     * This method converts an array of model class object to json String
     *
     * @param objects an array of objects of model class
     * @return [String] the desired json
     *
     * i.e: String json = GsonUtils.toJson(objects)
     * */
    fun <T> toJson(objects: Array<T>): String? {
        val type = object : TypeToken<Array<T>>() {}.type

        try {
            return mGson.toJson(objects, type)
        } catch (e: Exception) {
        }

        return null
    }

    /**
     * This method converts a json string to model class object
     *
     * @param jsonString the string from which the object is to be de-serialized
     * @param modelClass the specific class of which the object is to be generated
     * @return [T] an object of the model class
     *
     * i.e: ModelClass modelClassObject = GsonUtils.fromJson(jsonString, ModelClass.class)
     * */
    fun <T> fromJson(jsonString: String, modelClass: Class<T>): T? {
        try {
            return mGson.fromJson(jsonString, modelClass)
        } catch (e: Exception) {

        }

        return null
    }

    /**
     * This method converts a json string to a json object
     *
     * @param jsonString the string from which the object is to be de-serialized
     * @return [JsonObject] a json object to be used
     *
     * i.e: JsonObject jsonObject = GsonUtils.fromJson(json)
     * */
    fun fromJson(jsonString: String): JSONObject? {
        try {
            return mGson.fromJson(jsonString, JSONObject::class.java)
        } catch (e: Exception) {

        }

        return null
    }

    /**
     * This method converts a json string to a list of model class object
     *
     * @param jsonString the string from which the object is to be de-serialized
     * @param desiredType type of source.
     * @return [List] list of the desired objects
     *
     * i.e: List<ModelClass> list =
     * GsonUtils.fromJson(json, object : TypeToken<List<ModelClass>>(){}.type)
     * */
    fun <T> fromJson(jsonString: String, desiredType: Type): List<T> {
        try {
            return mGson.fromJson<List<T>>(jsonString, desiredType)
        } catch (e: Exception) {

        }

        return ArrayList()
    }
}