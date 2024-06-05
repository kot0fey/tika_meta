package org.example.ingest;

import org.elasticsearch.ingest.AbstractProcessor;
import org.elasticsearch.ingest.IngestDocument;
import org.elasticsearch.ingest.Processor;

import java.io.IOException;
import java.util.Map;

import static org.elasticsearch.ingest.ConfigurationUtils.readStringProperty;


public class ExtractMetadataProcessor extends AbstractProcessor {
    public static final String TYPE = "metadata_extractor";

    private final String field;
    private final String targetField;

    public ExtractMetadataProcessor(String tag, String description, String field, String targetField) throws IOException {
        super(tag, description);
        this.field = field;
        this.targetField = targetField;
    }

    @Override
    public IngestDocument execute(IngestDocument ingestDocument) {
        String content = ingestDocument.getFieldValue(field, String.class);
        String metadata = MetadataExtractor.extract(content);
        ingestDocument.setFieldValue(targetField, metadata);
        return ingestDocument;
    }

    @Override
    public String getType() {
        return TYPE;
    }


    public static final class Factory implements Processor.Factory {

        @Override
        public Processor create(Map<String, Processor.Factory> factories, String tag, String description, Map<String, Object> config) throws Exception {
            String field = readStringProperty(TYPE, tag, config, "path");
            String targetField = readStringProperty(TYPE, tag, config, "metadata", "default_field_name");
            return new ExtractMetadataProcessor(tag, description, field, targetField);
        }
    }

}
