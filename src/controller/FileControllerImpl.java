package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import model.Analysis;
import model.AnalysisImpl;
import model.replay.Replay;
import model.state.InitialState;
import model.state.SimpleState;
import utils.exceptions.FileFormatException;
import utils.exceptions.IllegalExtensionExeption;

/**
 * Implementation of FileController.
 */
public final class FileControllerImpl implements FileController {

    private final Gson gson = new Gson();

    private <T> T loadFromJsonFile(final String path, final Class<T> objectClass, final String extension) throws IOException {
        if (!path.endsWith(extension)) {
            throw new IllegalExtensionExeption();
        }
        try (JsonReader reader = new JsonReader(new BufferedReader(new FileReader(path)))) {
            return gson.fromJson(reader, objectClass);
        } catch (JsonIOException | JsonSyntaxException e) {
            throw new FileFormatException();
        }
    }

    private void saveToTextFile(final String path, final String text) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(text);
        }
    }

    private void saveToJsonFile(final String path, final Object object, final String extension) throws IOException {
        String correctPath = path;
        if (!path.endsWith(extension)) {
            correctPath = path + extension;
        }
        try {
            saveToTextFile(correctPath, gson.toJson(object));
        } catch (JsonIOException | JsonSyntaxException e) {
            throw new IOException();
        }
    }

    @Override
    public InitialState loadInitialState(final String path) throws IOException {
        return loadFromJsonFile(path, InitialState.class, "." + SIMULATION_EXTENTION);
    }

    @Override
    public void saveInitialState(final String path, final InitialState initialState) throws IOException {
        saveToJsonFile(path, initialState, "." + SIMULATION_EXTENTION);
    }

    @Override
    public Replay loadReplay(final String path) throws IOException {
        try (JsonReader reader = gson.newJsonReader(new BufferedReader(new FileReader(path)))) {
            final Replay result = new Replay(gson.fromJson(reader, InitialState.class));
            result.setAnalysis(gson.fromJson(reader, AnalysisImpl.class));
            reader.beginArray();
            while (reader.hasNext()) {
                result.addSimpleState(gson.fromJson(reader, SimpleState.class));
            }
            reader.endArray();
            return result;
        }
        //return loadFromJsonFile(path, Replay.class, "." + REPLAY_EXTENTION);
    }

    @Override
    public void saveReplay(final String path, final Replay replay) throws IOException {
        try (JsonWriter writer = gson.newJsonWriter(new BufferedWriter(new FileWriter(path)))) {
            gson.toJson(replay.getInitialState(), replay.getInitialState().getClass(), writer);
            gson.toJson(replay.getAnalysis(), replay.getAnalysis().getClass(), writer);
            writer.beginArray();
            replay.getStateList().forEach(s -> gson.toJson(s, s.getClass(), writer));
            writer.beginArray();
        }
        //saveToJsonFile(path, replay, "." + REPLAY_EXTENTION);
    }

    @Override
    public void saveAnalysis(final String path, final Analysis analysis) throws IOException {
        saveToTextFile(path, analysis.getDescription());
    }
}
