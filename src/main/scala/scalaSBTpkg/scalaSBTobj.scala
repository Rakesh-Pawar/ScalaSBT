package scalaSBTpkg
import org.apache.spark.sql.SparkSession
import  org.apache.spark.sql.functions._
import  org.apache.spark.sql._
import org.apache.hadoop.conf.Configuration

object scalaSBTobj {
  def main(args: Array[String]): Unit = {
    println("Scala with SBT ...")

    val spark = SparkSession.builder
      .appName("ScaalaSBT").master("local[1]").getOrCreate()
    println("Spark Object: "+ spark)

    spark.sparkContext.setLogLevel("ERROR")

    // s3 credentials
    val accessKey = "AKIAZ23HUQHQOX74DHUF"
    val secretKey = "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"


    spark.sparkContext.hadoopConfiguration.set("spark.hadoop.fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")

    spark.sparkContext
      .hadoopConfiguration.set("fs.s3a.access.key", accessKey)

    spark.sparkContext
      .hadoopConfiguration.set("fs.s3a.secret.key", secretKey)

    spark.sparkContext
      .hadoopConfiguration.set("fs.s3a.endpoint", "s3.amazonaws.com")

    val inputFilePath = "s3a://ds-data-migration-bucket/incoming/1000_output.csv"
      // "D:\\Temp Files\\asl.csv"
//
    val data = spark.read.option("header", "true")
      .csv(inputFilePath)

    println(data.show())

    val txtFile = "s3a://rakesh-pwar-s3-bucket/inputData/baankdata.txt"

    val txtData = spark.read.textFile(txtFile)

    txtData.collect().foreach(f=>{ println(f)})
    spark.stop()
  }
}
