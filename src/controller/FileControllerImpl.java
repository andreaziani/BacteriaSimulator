package controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import model.Analisys;
import model.State;

/**
 * Implementation of FileController.
 *
 */
public class FileControllerImpl implements FileController {

    
    private <X> void saveData(final String path, final X data) {
        try (OutputStream file = new FileOutputStream(path)) {
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Override
    public void loadInitialState(final String path) {
        
    }

    @Override
    public void saveInitialState(final String path, final State initialState) {
        
    }

    @Override
    public void loadReplay(final String path) {
        
    }

    @Override
    public void saveReplay(final String path, final Replay replay) {
        
    }

    @Override
    public void saveAnalisys(final String path, final Analisys analisys) {

    }
}
