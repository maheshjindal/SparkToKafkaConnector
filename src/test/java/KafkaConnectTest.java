import org.apache.commons.lang3.builder.ToStringExclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Add test Cases
 *
 */
public class KafkaConnectTest {


    public static void main(String[] args) {


        // prepare all confs
        Map<String,String> sparkOptions = new HashMap<>();
        sparkOptions.put("spark.app.name","fileToKafka");
        sparkOptions.put("spark.master","local[2]");


        FileToKafkaConfig fileToKafkaConfig = new FileToKafkaConfig();
        fileToKafkaConfig.setInputPath("/Users/maheshjindal/Documents/input.json");
        fileToKafkaConfig.setInputFormat("json");
        fileToKafkaConfig.setSparkConf(sparkOptions);
        fileToKafkaConfig.setSrcOptions(new HashMap<>());
        fileToKafkaConfig.setPartitions(2);
        fileToKafkaConfig.setKafkaRecordFormat("json");
        fileToKafkaConfig.setTopicName("testtopic");


        /**
         * Read from file and write to kafka
         */
        KafkaConnect.fileToKafka(fileToKafkaConfig);




//
//        Map<String,String> kafkaOptions = new HashMap<>();
//        kafkaOptions.put("kafka.bootstrap.servers", "localhost:9092");
//        kafkaOptions.put("subscribePattern", "testtopic");
//        kafkaOptions.put("startingOffsets", "earliest");
//        kafkaOptions.put("endingOffsets", "latest");
//        kafkaOptions.put("includeHeaders","false");
//
//        KafkaToFileConfig kafkaToFileConfig = new KafkaToFileConfig();
//        kafkaToFileConfig.setSparkConf(sparkOptions);
//        kafkaToFileConfig.setKafkaOptions(kafkaOptions);
//        kafkaToFileConfig.setOutputFormat("csv");
//        kafkaToFileConfig.setDestPath("/tmp/testdataout");
//        kafkaToFileConfig.setOutputMode("overwrite");

        /**
         * write to file from given kafka topic
         */
//        KafkaConnect.kafkaToFile(kafkaToFileConfig);

    }
}
