import java.util.Map;

public class KafkaToFileConfig {


    /**
     * Spark config params, like master, appname, s3/hdfs specific options
     */
    private Map<String,String> sparkConf;


    /**
     * All kafka related options including serializers/deserializers and all
     */
    private Map<String,String> kafkaOptions;

    /**
     * output file format ( parquet/json/csv..etc)
     */
    private String outputFormat;

    /**
     * Number of output partitions
     */
    private int numberOfPartitions;

    /**
     * Where to write the output
     */
    private String destPath;

    /**
     * Comma separated partition columns
     */
    private String partitionColumns;

    /**
     * append/overwrite
     */
    private String outputMode;

    public KafkaToFileConfig(){
        numberOfPartitions=-1;
    }

    public Map<String, String> getKafkaOptions() {
        return kafkaOptions;
    }

    public void setKafkaOptions(Map<String, String> kafkaOptions) {
        this.kafkaOptions = kafkaOptions;
    }

    public Map<String, String> getSparkConf() {
        return sparkConf;
    }

    public void setSparkConf(Map<String, String> sparkConf) {
        this.sparkConf = sparkConf;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    public int getNumberOfPartitions() {
        return numberOfPartitions;
    }

    public void setNumberOfPartitions(int numberOfPartitions) {
        this.numberOfPartitions = numberOfPartitions;
    }

    public String getDestPath() {
        return destPath;
    }

    public void setDestPath(String destPath) {
        this.destPath = destPath;
    }

    public String getPartitionColumns() {
        return partitionColumns;
    }

    public void setPartitionColumns(String partitionColumns) {
        this.partitionColumns = partitionColumns;
    }

    public String getOutputMode() {
        return outputMode;
    }

    public void setOutputMode(String outputMode) {
        this.outputMode = outputMode;
    }
}
