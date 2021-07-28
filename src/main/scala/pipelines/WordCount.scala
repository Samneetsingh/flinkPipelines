package pipelines

import org.apache.flink.api.common.eventtime.WatermarkStrategy
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.connector.kafka.source.KafkaSource
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer
import org.apache.flink.streaming.api.datastream.DataStreamSource
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment

object WordCount {

  lazy val kafkaBrokers: String = "localhost:9092"
  lazy val kafkaInputTopic: String = "flink"
  lazy val kafkaGroupId: String = "flink"

  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    // Kafka Source
    val kafkaSource: KafkaSource[String] = KafkaSource
      .builder[String]
      .setBootstrapServers(kafkaBrokers)
      .setTopics(kafkaInputTopic)
      .setGroupId(kafkaGroupId)
      .setStartingOffsets(OffsetsInitializer.earliest)
      .setValueOnlyDeserializer(new SimpleStringSchema())
      .build()

    val textStream: DataStreamSource[String] = env.fromSource(kafkaSource, WatermarkStrategy.noWatermarks[String](), "Kafka Source")

    textStream.flatMap(new LineSplitter()).print()
    env.executeAsync("Kafka WordCount Job")
  }

}
