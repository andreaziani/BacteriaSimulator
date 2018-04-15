package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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

    @Override
    public InitialState loadInitialState(final File file) throws IOException {
        try (JsonReader reader = new JsonReader(new BufferedReader(new FileReader(file)))) {
            return gson.fromJson(reader, InitialState.class);
        } catch (JsonIOException | JsonSyntaxException e) {
            throw new IOException();
        }
    }

    @Override
    public void saveInitialState(final File file, final InitialState initialState) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(gson.toJson(initialState));
        } catch (JsonIOException | JsonSyntaxException e) {
            throw new IOException();
        }
    }

    @Override
    public Replay loadReplay(final String path) throws IOException {
        try (JsonReader reader = new JsonReader(new BufferedReader(new FileReader(path)))) {
            return gson.fromJson(reader, Replay.class);
        } catch (JsonIOException | JsonSyntaxException e) {
            throw new IOException();
        }
    }

    @Override
    public void saveReplay(final String path, final Replay replay) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(gson.toJson(replay));
        } catch (JsonIOException | JsonSyntaxException e) {
            throw new IOException();
        }
    }

    @Override
    public void saveAnalysis(final File file, final Analysis analysis) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(analysis.getDescription());
        }
    }
}
