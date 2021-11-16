import java.util.Map;

public class FileToKafkaConfig {

    public String getInputFormat() {
        return inputFormat;
    }

    public void setInputFormat(String inputFormat) {
        this.inputFormat = inputFormat;
    }

    public String getInputPath() {
        return inputPath;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    public int getPartitions() {
        return partitions;
    }

    public void setPartitions(int partitions) {
        this.partitions = partitions;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getKafkaRecordFormat() {
        return kafkaRecordFormat;
    }

    public void setKafkaRecordFormat(String kafkaRecordFormat) {
        this.kafkaRecordFormat = kafkaRecordFormat;
    }

    public Map<String, String> getSrcOptions() {
        return srcOptions;
    }

    public void setSrcOptions(Map<String, String> srcOptions) {
        this.srcOptions = srcOptions;
    }

    public Map<String, String> getSparkConf() {
        return sparkConf;
    }

    public void setSparkConf(Map<String, String> sparkConf) {
        this.sparkConf = sparkConf;
    }

    /**
     * Input format currently suuports parquet/json/avro/orc/csv
     */
    private String inputFormat;

    /**
     * source file Input path
     */
    private String inputPath;

    /**
     * Number of kafka paritions to write data
     */
    private int partitions;

    /**
     * destination kafka topic
     */
    private String topicName;

    /**
     * json/avro
     */
    private String kafkaRecordFormat;

    /**
     * additional  options for input reader , like headers -> true  for csv
     */
    private Map<String,String> srcOptions;

    /**
     * Spark config params, like master, appname, s3/hdfs specific options
     */
    private Map<String,String> sparkConf;

}
