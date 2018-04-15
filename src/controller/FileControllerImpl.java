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

import model.Analysis;

/**
 * Implementation of FileController.
 *
 */
public class FileControllerImpl implements FileController {

    private final Gson gson = new Gson();

    private <T> T loadFromJsonFile(final String path, final Class<T> objectClass) throws IOException {
        try (JsonReader reader = new JsonReader(new BufferedReader(new FileReader(path)))) {
            return gson.fromJson(reader, objectClass);
        } catch (JsonIOException | JsonSyntaxException e) {
            throw new IOException();
        }
    }

    private void saveToJsonFile(final String path, final Object object) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(gson.toJson(object));
        } catch (JsonIOException | JsonSyntaxException e) {
            throw new IOException();
        }
    }

    @Override
    public InitialState loadInitialState(final String path) throws IOException {
        return loadFromJsonFile(path, InitialState.class);
    }

    @Override
    public void saveInitialState(final String path, final InitialState initialState) throws IOException {
        saveToJsonFile(path, initialState);
    }

    @Override
    public Replay loadReplay(final String path) throws IOException {
        return loadFromJsonFile(path, Replay.class);
    }

    @Override
    public void saveReplay(final String path, final Replay replay) throws IOException {
        saveToJsonFile(path, replay);
    }

    @Override
    public void saveAnalysis(final String path, final Analysis analysis) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(analysis.getDescription());
        }
    }
}
