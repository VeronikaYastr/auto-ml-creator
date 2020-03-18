package com.veryastr.bsu.service;

import com.veryastr.bsu.exceptions.MlCreatorSparkException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SparkService {

    private final SparkSession sparkSession;
    private final FileStorageService fileStorageService;
    private Map<UUID, Dataset<Row>> datasetMap = new HashMap<>();

    static {
        Logger.getLogger("org").setLevel(Level.ERROR);
    }

    @PostConstruct
    public void init() {
        sparkSession.sparkContext().setLogLevel("INFO");
    }

    public Dataset<Row> readFileIntoDataset(String filename, String fileType) {
        String fullFilename = fileStorageService.getFullFilename(filename);
        Dataset<Row> dataset;

        switch (fileType) {
            case MediaType.APPLICATION_JSON_VALUE:
                dataset = sparkSession.read()
                        .option("multiline", "true")
                        .json(fullFilename)
                        .as("JSON File")
                        .cache();
                break;
            case MediaType.TEXT_PLAIN_VALUE:
                dataset = sparkSession.read()
                        .text(fullFilename)
                        .as("Text File")
                        .cache();
                break;
            case MediaType.APPLICATION_OCTET_STREAM_VALUE:
                dataset = sparkSession.read()
                        .option("header", true)
                        .csv(fullFilename)
                        .as("CSV File")
                        .cache();
                break;
            default:
                throw new MlCreatorSparkException("Invalid file format: " + fileType + ". Supported formats: txt, csv, json.");
        }
        return dataset;
    }

    public UUID saveDatasetToCache(Dataset<Row> dataset) {
        UUID key = UUID.randomUUID();
        datasetMap.put(key, dataset);
        return key;
    }

    public Dataset<Row> getDatasetFromCache(UUID id) {
        Dataset<Row> dataset = datasetMap.get(id);
        if (dataset == null) {
            throw new MlCreatorSparkException("Dataset is not found: " + id);
        }
        return dataset;
    }

    private static void readInDifferentFormats(SparkSession session) {
        Dataset<Row> dfFromCSV = session.read()
                .option("header", true)
                .csv("C:\\Users\\veron\\auto-ml-creator\\uploads\\iris.csv")
                .as("Iris");
        dfFromCSV.show(10);
        log.info("Records count: {}", dfFromCSV.count());
        /*Map<String, String> jdbcOptions = new HashMap<>();
        jdbcOptions.put("driver", "org.postgresql.Driver");
        jdbcOptions.put("url", "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=123456Ab");
        jdbcOptions.put("dbtable", "public.fix_order");
        Dataset<Row> dfFromDB = session.read().format("jdbc").options(jdbcOptions).load();
        dfFromDB.show();

        Dataset<Row> dfFromCSV = session.read()
                .option("header", true)
                .csv("C:\\Users\\Veronika.Yastrebova\\Downloads\\Crimes_-_2001_to_present.csv")
                .as("Crimes");
        dfFromCSV.show(10);
        dfFromCSV.filter(dfFromCSV.col("Ward").between(10, 20)).select(dfFromCSV.col("Ward").alias("Middle_ward")).show(10);
        dfFromCSV.filter(dfFromCSV.col("Domestic").equalTo(true)).sort("Primary Type", "Description").show(10);
        log.info("Records count: {}", dfFromCSV.count());
        log.info("Records count without null values: {}", dfFromCSV.na().drop().count());*/
    }
}
