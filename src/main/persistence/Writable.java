package persistence;

import org.json.JSONObject;

// Interface for classes that can be converted to a JSONObject

public interface Writable {
    JSONObject toJson();
}
