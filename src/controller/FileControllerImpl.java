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

import model.Analisys;

/**
 * Implementation of FileController.
 *
 */
public class FileControllerImpl implements FileController {

    private final Gson gson = new Gson();

    @Override
    public InitialState loadInitialState(final String path) throws IOException {
        try (JsonReader reader = new JsonReader(new BufferedReader(new FileReader(path)))) {
            return gson.fromJson(reader, InitialState.class);
        } catch (JsonIOException | JsonSyntaxException e) {
            throw new IOException();
        }
    }

    @Override
    public void saveInitialState(final String path, final InitialState initialState) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(gson.toJson(initialState));
        } catch (JsonIOException | JsonSyntaxException e) {
            throw new IOException();
        }
    }

    @Override
    public void loadReplay(final String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void saveReplay(final String path, final Replay replay) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void saveAnalisys(final String path, final Analisys analisys) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(analisys.toString());
        }
    }
}
