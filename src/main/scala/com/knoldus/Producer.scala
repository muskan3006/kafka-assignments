package com.knoldus

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object Producer extends App {


  writeToKafka("Muskan")


  def writeToKafka(topic: String): Unit = {

    val props = new Properties()

    props.put("bootstrap.servers", "localhost:9092")

    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    props.put("value.serializer", "com.knoldus.UserSerializer")

    val producer = new KafkaProducer[String, User](props)
    try {
      val list = Demo.listProducer
      list.map(user => new ProducerRecord[String, User](topic, user)).map(record => producer.send(record))

    } catch {
      case exception: Exception => exception.printStackTrace()
    } finally {
      producer.close()
    }
  }


}
