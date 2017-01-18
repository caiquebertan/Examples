package br.com.examples.caique.examplesproject.controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class GsonHelper {

	private static final String[] DATE_FORMATS = new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss", "dd-MM-yyyy HH:mm:ss", "dd/MM/yyyy HH:mm", "dd/MM/yyyy", "yyyy-MM-dd'T'HH:mm:ss.SSS", "yyyy-MM-dd'T'HH:mm:ss", "MMM dd, yyyy HH:mm:ss aaa" };
	 

	public Gson getGson() {
		return new GsonBuilder()
				 .registerTypeAdapter(Date.class, new DateSerializer())
				.registerTypeAdapter(Date.class, new DateDeserializer()).create();
	}

	public class DateSerializer implements JsonSerializer<Date> {

		@Override
		public JsonElement serialize(Date date, Type typfOfT, JsonSerializationContext context) {
			return date == null ? null : new JsonPrimitive(date.getTime());
		}
	}
	private class DateDeserializer implements JsonDeserializer<Date> {

		@Override
		public Date deserialize(JsonElement jsonElement, Type typeOF, JsonDeserializationContext context) throws JsonParseException {
			for (String format : DATE_FORMATS) {
				try {
					return new SimpleDateFormat(format, Locale.US).parse(jsonElement.getAsString());
				}
				catch (ParseException e) {
				}
			}
			
			try {
				return new Date(jsonElement.getAsJsonPrimitive().getAsLong());
			} catch (Exception e) {}
			
			throw new JsonParseException("Unparseable date: \"" + jsonElement.getAsString() + "\". Supported formats: " + Arrays.toString(DATE_FORMATS));
		}
	}
}
