package org.launchcode.Atlas.data;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.launchcode.Atlas.model.Marker;

import java.io.IOException;


//provides support for turning Marker into Json
public class MarkerSerializer extends StdSerializer<Marker> {

    public MarkerSerializer() {
        this(null);
    }

    public MarkerSerializer(Class<Marker> marker) {
        super(marker);
    }

    @Override
    public void serialize(Marker value, JsonGenerator gen, SerializerProvider provider) throws IOException, JsonParseException {
       gen.writeStartObject();
       gen.writeNumberField("id", value.getId());
       gen.writeStringField("name", value.getMarkerName());
       gen.writeNumberField("lat", value.getLocation().getY());
       gen.writeNumberField("lng", value.getLocation().getX());
       gen.writeStringField("description", value.getDescription());
       gen.writeStringField("imageName", value.getImageName());
       gen.writeEndObject();
    }
}
