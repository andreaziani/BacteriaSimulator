package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import model.Analysis;
import model.replay.Replay;
import model.replay.ReplayAnalysis;
import model.replay.ReplayState;
import model.state.InitialState;

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

    private void controlExtensionAndThrow(final String path, final String extension) {
        if (!isPathCorrect(path, extension)) {
            throw new IllegalExtensionException("The extension of the file must be: " + extension);
        }
    }

    @Override
    public InitialState loadInitialState(final String path) throws IOException {
        controlExtensionAndThrow(path, SIMULATION_EXTENTION);
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
            throw new IOException(e);
        }
    }

    @Override
    public Replay loadReplay(final String path) throws IOException {
        controlExtensionAndThrow(path, REPLAY_EXTENTION);
        try (JsonReader reader = gson.newJsonReader(
                new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(path)))))) {
            reader.beginObject();
            reader.nextName();
            final Replay result = new Replay(gson.fromJson(reader, InitialState.class));
            reader.nextName();
            result.setAnalysis(gson.fromJson(reader, ReplayAnalysis.class));
            reader.nextName();
            reader.beginArray();
            while (reader.hasNext()) {
                result.addReplayState(gson.fromJson(reader, ReplayState.class));
            }
            reader.endArray();
            reader.endObject();
            return result;
        }
    }

    @Override
    public void saveReplay(final String path, final Replay replay) throws IOException {
        try (JsonWriter writer = gson.newJsonWriter(new BufferedWriter(new OutputStreamWriter(
                new GZIPOutputStream(new FileOutputStream(getCorrectedPath(path, REPLAY_EXTENTION))))))) {
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
        } catch (JsonIOException | JsonSyntaxException e) {
            throw new IOException(e);
        }
    }

    @Override
    public void saveAnalysis(final String path, final Analysis analysis) throws IOException {
        saveToTextFile(path, analysis.getDescription());
    }
}
