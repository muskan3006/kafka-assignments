package com.knoldus

import java.io.{BufferedWriter, File, FileWriter}
import java.util
import java.util.Properties

import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.write
import org.apache.kafka.clients.consumer.KafkaConsumer

import scala.jdk.CollectionConverters._

object Consumer extends App {

  consumeFromKafka("Muskan")

  def consumeFromKafka(topic: String): Unit = {

    val props = new Properties()

    props.put("bootstrap.servers", "localhost:9092")

    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

    props.put("value.deserializer", "com.knoldus.UserDeserializer")

    props.put("auto.offset.reset", "latest")

    props.put("group.id", "consumer-group")

    val consumer: KafkaConsumer[String, User] = new KafkaConsumer[String, User](props)

    consumer.subscribe(util.Arrays.asList(topic))

    implicit val formats: DefaultFormats.type = DefaultFormats
    val record = consumer.poll(1000).asScala
    record.map(singleRecord => for {data <- record.iterator
                                    json = write(data.value())
                                    } yield output(json))

    def output(convertedJson: String): Unit = {
      println(convertedJson)
      val writer = new BufferedWriter(new FileWriter(new File("./src/main/resources/consumerData.txt"), true))
      writer.write(convertedJson)
      writer.close()
    }
  }

}
