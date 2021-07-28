package pipelines

import org.apache.flink.api.common.functions.FlatMapFunction
import org.apache.flink.util.Collector

class LineSplitter extends FlatMapFunction[String, (String, Int)] {
  override def flatMap(value: String, out: Collector[(String, Int)]): Unit = {
    value.toLowerCase.split("\\W+").filter(_.nonEmpty)
  }
}
