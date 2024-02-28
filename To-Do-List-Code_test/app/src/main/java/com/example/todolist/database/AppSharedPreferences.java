package com.example.todolist.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.todolist.model.TodoItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AppSharedPreferences {
    private static final String PREF_NAME = "ToDoAppPreferences";
    private static AppSharedPreferences instance;
    private final SharedPreferences sharedPreferences;
    // Private constructor to prevent instantiation from outside
    private static final String mapKey = "TO_DO_LIST";

    private AppSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Singleton instance creation method
    public static synchronized AppSharedPreferences getInstance(Context context) {
        if (instance == null) {
            instance = new AppSharedPreferences(context.getApplicationContext());
        }
        return instance;
    }

    // Method to save a HashMap to SharedPreferences using Gson serialization
    public void saveHashMap(Long id,TodoItem item) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        HashMap<Long, TodoItem> hashMap = getHashMap();
        hashMap.put(id,item);
        Gson gson = new Gson();
        String json = gson.toJson(hashMap);
        editor.putString(mapKey, json);
        editor.apply();
    }

    public void removeItemFromHashMap(Long id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        HashMap<Long, TodoItem> hashMap = getHashMap();
        if(hashMap.containsKey(id)){
            hashMap.remove(id);
        }
        Gson gson = new Gson();
        String json = gson.toJson(hashMap);
        editor.putString(mapKey, json);
        editor.apply();
    }

    // Method to retrieve a HashMap from SharedPreferences using Gson deserialization
    public HashMap<Long, TodoItem> getHashMap() {
        String json = sharedPreferences.getString(mapKey, null);
        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<HashMap<Long, TodoItem>>() {}.getType();
            return gson.fromJson(json, type);
        }
        return new HashMap<>(); // Return an empty HashMap if no data found
    }

    public ArrayList<TodoItem> getTodoListItems() {
        ArrayList<TodoItem> items = new ArrayList<>();
        String json = sharedPreferences.getString(mapKey, null);
        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<HashMap<Long, TodoItem>>() {}.getType();
            HashMap<Long, TodoItem> data = gson.fromJson(json, type);
            for (Map.Entry<Long, TodoItem> entry : data.entrySet()) {
                items.add(entry.getValue());
            }
            return items;
        }
        return items; // Return an empty HashMap if no data found
    }



}
