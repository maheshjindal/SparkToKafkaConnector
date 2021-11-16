import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.*;

import java.util.Map;

import static org.apache.spark.sql.avro.functions.*;
import static org.apache.spark.sql.functions.*;


public class KafkaConnect {


    public  static SparkSession getOrCreateSparkSession(Map<String,String> sparkOptions){
        final SparkConf sparkConfig = new SparkConf();
        sparkOptions.forEach(sparkConfig::set);
        return SparkSession.builder().config(sparkConfig).getOrCreate();
    }

    public static void fileToKafka(FileToKafkaConfig config){

        //create spark session
        SparkSession sparkSession = getOrCreateSparkSession(config.getSparkConf());

        // read data frame and  convert all columns into single json string, as we will be writing the entire record as value in kafka
        Dataset<String> rawDataFrame = sparkSession.read().format(config.getInputFormat()).options(config.getSrcOptions()).load(config.getInputPath()).toJSON();
        //append row number  field
        Dataset<Row> dsWithRowNum = mapWithRowNumber(rawDataFrame) ;

        // add all columns required before writing to kafka, remember that the  header should be of type array<struct<key:string,value:binary>> type as per spark
        Dataset<Row> finalDf  = dsWithRowNum.repartition(config.getPartitions()).
                withColumn("key",to_avro(col("rowNumber"))).
                withColumn("value",convertTo(config.getKafkaRecordFormat(),"data")).
                withColumn("headers", array(struct(lit("rowNumber").as("key"), to_avro(col("rowNumber")).as("value")))).
                withColumn("partition",spark_partition_id());

        finalDf.printSchema();

        // if topic has more than one partition first create topic in kafka before writing
        finalDf.write()
                .format("kafka")
                .option("kafka.bootstrap.servers", "localhost:9092")
                .option("topic",config.getTopicName())
                .option("includeHeaders", "true")
                .save();

    }



    public static void kafkaToFile(KafkaToFileConfig config){

        SparkSession sparkSession = getOrCreateSparkSession(config.getSparkConf());

        // if you want only message/value please use drop to drop remaining fields from below df
        Dataset<Row> kafkaDf = sparkSession
                .read()
                .format("kafka")
                .options(config.getKafkaOptions())
                .load();

        // if target number of partitions specified
        if(config.getNumberOfPartitions()>0){
            kafkaDf = kafkaDf.repartition(config.getNumberOfPartitions());
        }

        DataFrameWriter<Row> fsWriter = kafkaDf.write();
        if(config.getPartitionColumns()!=null && !config.getPartitionColumns().isEmpty()){
            fsWriter = fsWriter.partitionBy(config.getPartitionColumns().split(","));
        }
        // please remember that key & values by default will be in byte array format, use data frame options like cast to string, or to json ..etc to convert
        fsWriter.format(config.getOutputFormat())
                .mode(config.getOutputMode())
                .save(config.getDestPath());

    }


    /**
     * Add row number
     * @param inputDs
     * @return
     */
    public static Dataset mapWithRowNumber(Dataset<String> inputDs){
        JavaRDD<Row> tuple2RDD = inputDs.javaRDD().zipWithIndex().map(tuple -> RowFactory.create(tuple._1(),tuple._2()));
        return inputDs.sparkSession().createDataFrame(tuple2RDD,Encoders.tuple(Encoders.STRING(), Encoders.LONG()).schema()).toDF("data", "rowNumber");
    }


    /**
     * Convert the given column to given type ( for now avro)
     * @param toType
     * @param columnName
     * @return
     */
    public static Column convertTo(String toType,String columnName){
        Column convertedColumn = col(columnName);
        if(toType.equals("avro")){
            convertedColumn= to_avro(convertedColumn);
        }
        // if type is json the data is already in json format so return as is
        return convertedColumn;
    }


}
