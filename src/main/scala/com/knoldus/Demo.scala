package com.knoldus

import net.liftweb.json.DefaultFormats

case class User(id: Int, name: String, age: Int, address: String)

object Demo extends App {
  implicit val formats = DefaultFormats

  def listProducer: List[User] = {
    val parsedJsonData = net.liftweb.json.parse(getJsonString())
    val data = parsedJsonData.children.map { data =>
      data.extract[User]
    }
    data
  }

  def getJsonString(): String = {
    implicit val formats = DefaultFormats
    val jsonString =
      """  [
     {
       "id": 0001,
       "name":"your name",
       "age":25,
       "address":"Delhi"
}

    ]
  """
    jsonString
  }
}
