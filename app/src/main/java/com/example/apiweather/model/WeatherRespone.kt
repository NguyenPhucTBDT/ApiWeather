package com.example.apiweather.model
import com.google.gson.annotations.SerializedName

class WeatherRespone{
  @SerializedName("coord")
  var coord:Coord?=null
  @SerializedName("weather")
  var weather:ArrayList<Weather>?=null
  @SerializedName("main")
  var main:Main?=null
  @SerializedName("name")
  var name:String?=null
  @SerializedName("timezone")
  var timezone:Int=0
}
class Coord{
  @SerializedName("lon")
  var lon:Float=0.toFloat()
  @SerializedName("lat")
  var lat:Float=0.toFloat()
}
class Main{
  @SerializedName("temp")
  var temp:Float=0.toFloat()
  @SerializedName("feels_like")
  var feels_like:Float=0.toFloat()
  @SerializedName("temp_min")
  var temp_min:Float=0.toFloat()
  @SerializedName("temp_max")
  var temp_max:Float=0.toFloat()
  @SerializedName("pressure")
  var pressure:Int=0
  @SerializedName("humidity")
  var humidity:Int=0
  @SerializedName("sea_level")
  var sea_level:Int=0
  @SerializedName("grnd_level")
  var sea_lgrnd_levelevel:Int=0
}
class Weather{
  @SerializedName("id")
  var id:Int=0
  @SerializedName("main")
  var main:String?=null
  @SerializedName("description")
  var description:String?=null
  @SerializedName("icon")
  var icon:String?=null
}