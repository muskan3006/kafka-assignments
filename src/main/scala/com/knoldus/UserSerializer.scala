package com.knoldus

import java.io.{ByteArrayOutputStream, ObjectOutputStream}

import org.apache.kafka.common.serialization.Serializer

class UserSerializer extends Serializer[User] {

  override def serialize(topic: String, data: User): Array[Byte] = {
    try {
      val byteOut = new ByteArrayOutputStream()
      val objOut = new ObjectOutputStream(byteOut)
      objOut.writeObject(data)
      objOut.close()
      byteOut.close()
      byteOut.toByteArray
    }
    catch {
      case ex: Exception => throw new Exception(ex.getMessage)
    }
  }

  override def close(): Unit = {

  }
}
