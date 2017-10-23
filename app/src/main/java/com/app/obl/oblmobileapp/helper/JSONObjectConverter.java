package com.app.obl.oblmobileapp.helper;

/**
 * Created by ONE BANK 1 on 12/1/2015.
 */
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class JSONObjectConverter {

    public JSONObjectConverter(){
    }

    public static List<String> getStringListFromJsonArray(JSONArray jArray) throws JSONException {
        List<String> returnList = new ArrayList<String>();
        for (int i = 0; i < jArray.length(); i++) {
            String val = jArray.getString(i);
            returnList.add(val);
        }
        return returnList;
    }

    public static String[] getStringArrayFromJsonArray(JSONArray jArray) throws JSONException {
        String[] returnList = new String[jArray.length()];
        for (int i = 0; i < jArray.length(); i++) {
            String val = jArray.getString(i);
            returnList[i]=val;
        }
        return returnList;
    }

    public static String[] getStringArrayFromJsonArrayForTag(JSONArray jArray,String jsonTag) throws JSONException {
        //JSONArray nestedArray=new JSONArray(jArray.getString(jsonTag));
        String[] returnList = new String[jArray.length()];
        for (int i = 0; i < jArray.length(); i++) {
            String val = jArray.getString(i);
            returnList[i]=val;
        }
        return returnList;
    }

    public static List<Object> getListFromJsonArray(JSONArray jArray) throws JSONException {
        List<Object> returnList = new ArrayList<Object>();
        for (int i = 0; i < jArray.length(); i++) {
            returnList.add(convertJsonItem(jArray.get(i)));
        }
        return returnList;
    }

    @SuppressWarnings("unchecked")
    public static List<Object> getListFromJsonObject(JSONObject jObject) throws JSONException {
        List<Object> returnList = new ArrayList<Object>();
        Iterator<String> keys = jObject.keys();

        List<String> keysList = new ArrayList<String>();
        while (keys.hasNext()) {
            keysList.add(keys.next());
        }
        Collections.sort(keysList);

        for (String key : keysList) {
            List<Object> nestedList = new ArrayList<Object>();
            nestedList.add(key);
            nestedList.add(convertJsonItem(jObject.get(key)));
            returnList.add(nestedList);
        }

        return returnList;
    }


    public static Object convertJsonItem(Object o) throws JSONException {
        if (o == null) {
            return "null";
        }

        if (o instanceof JSONObject) {
            return getListFromJsonObject((JSONObject) o);
        }

        if (o instanceof JSONArray) {
            return getListFromJsonArray((JSONArray) o);
        }

        if (o.equals(Boolean.FALSE) || (o instanceof String &&
                ((String) o).equalsIgnoreCase("false"))) {
            return false;
        }

        if (o.equals(Boolean.TRUE) || (o instanceof String && ((String) o).equalsIgnoreCase("true"))) {
            return true;
        }

        if (o instanceof Number) {
            return o;
        }

        return o.toString();
    }

    public static String getJsonRepresentation(Object value) throws JSONException {
        if (value == null || value.equals(null)) {
            return "null";
        }
      /*
       * This has been commented out to prevent the need for the Kawa library. Do NOT use Fstring
       * or YailList in any of your data, otherwise this conversion won't work.
       *
      if (value instanceof FString) {
        return JSONObject.quote(value.toString());
      }
      if (value instanceof YailList) {
        return ((YailList) value).toJSONString();
      }
      */

        if (value instanceof Number) {
            return JSONObject.numberToString((Number) value);
        }
        if (value instanceof Boolean) {
            return value.toString();
        }
        if (value.getClass().isArray()) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            String separator = "";
            for (Object o: (Object[]) value) {
                sb.append(separator).append(getJsonRepresentation(o));
                separator = ",";
            }
            sb.append("]");
            return sb.toString();
        }
        if (value instanceof String) {
            return value.toString();
        }
        return JSONObject.quote(value.toString());
    }

    public static Object getObjectFromJson(String jsonString) throws JSONException {
        final Object value = (new JSONTokener(jsonString)).nextValue();
        // Note that the JSONTokener may return a value equals() to null.
        if (value == null || value.equals(null)) {
            return null;
        } else if ((value instanceof String) ||
                (value instanceof Number) ||
                (value instanceof Boolean)) {
            return value;
        } else if (value instanceof JSONArray) {
            return getListFromJsonArray((JSONArray)value);
        } else if (value instanceof JSONObject) {
            return getListFromJsonObject((JSONObject)value);
        }
        throw new JSONException("Invalid JSON string.");
    }
}
