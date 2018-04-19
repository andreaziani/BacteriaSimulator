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
import utils.exceptions.IllegalExtensionException;

/**
 * Implementation of FileController.
 */
public final class FileControllerImpl implements FileController {

    private final Gson gson = new Gson();

    private String getFullExtension(final String extension) {
        return "." + extension;
    }
    private boolean isPathCorrect(final String path, final String extension) {
        return path.endsWith(getFullExtension(extension));
    }

    private String getCorrectedPath(final String path, final String extension) {
        String result = path;
        if (!isPathCorrect(path, extension)) {
            result += getFullExtension(extension);
        }
        return result;
    }

    private void saveToTextFile(final String path, final String text) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(text);
        }
    }

    @Override
    public InitialState loadInitialState(final String path) throws IOException {
        if (!isPathCorrect(path, SIMULATION_EXTENTION)) {
            throw new IllegalExtensionException();
        }
        try (JsonReader reader = new JsonReader(new BufferedReader(new FileReader(path)))) {
            return gson.fromJson(reader, InitialState.class);
        } catch (JsonIOException | JsonSyntaxException e) {
            throw new FileFormatException();
        }
    }

    @Override
    public void saveInitialState(final String path, final InitialState initialState) throws IOException {
        try {
            saveToTextFile(getCorrectedPath(path, SIMULATION_EXTENTION), gson.toJson(initialState));
        } catch (JsonIOException | JsonSyntaxException e) {
            throw new IOException();
        }
    }

    @Override
    public Replay loadReplay(final String path) throws IOException {
        if (!isPathCorrect(path, REPLAY_EXTENTION)) {
            throw new IllegalExtensionException();
        }
        try (JsonReader reader = gson.newJsonReader(new BufferedReader(new FileReader(path)))) {
            reader.beginObject();
            reader.nextName();
            final Replay result = new Replay(gson.fromJson(reader, InitialState.class));
            reader.nextName();
            result.setAnalysis(gson.fromJson(reader, AnalysisImpl.class));
            reader.nextName();
            reader.beginArray();
            while (reader.hasNext()) {
                result.addSimpleState(gson.fromJson(reader, SimpleState.class));
            }
            reader.endArray();
            reader.endObject();
            return result;
        }
    }

    @Override
    public void saveReplay(final String path, final Replay replay) throws IOException {
        try (JsonWriter writer = gson.newJsonWriter(new BufferedWriter(new FileWriter(getCorrectedPath(path, REPLAY_EXTENTION))))) {
            writer.beginObject();
            writer.name("initialState");
            gson.toJson(replay.getInitialState(), replay.getInitialState().getClass(), writer);
            writer.name("analysis");
            gson.toJson(replay.getAnalysis(), replay.getAnalysis().getClass(), writer);
            writer.name("stateList");
            writer.beginArray();
            replay.getStateList().forEach(s -> gson.toJson(s, s.getClass(), writer));
            writer.endArray();
            writer.endObject();
        }
    }

    @Override
    public void saveAnalysis(final String path, final Analysis analysis) throws IOException {
        saveToTextFile(path, analysis.getDescription());
    }
}
