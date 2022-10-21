package prr.core;


import prr.core.exception.ImportFileException;
import prr.core.exception.MissingFileAssociationException;
import prr.core.exception.UnavailableFileException;
import prr.core.exception.UnrecognizedEntryException;

import java.io.*;


/**
 * Manage access to network and implement load/save operations.
 */
public class NetworkManager {
    private String _fileName;
    /**
     * The network itself.
     */
    private Network _network = new Network();

    public Network getNetwork() {
        return _network;
    }


    public String getFileName() {
        return _fileName;
    }

    /**
     * @param fileName name of the file containing the serialized application's state
     *                 to load.
     * @throws UnavailableFileException if the specified file does not exist or there is
     *                                  an error while processing this file.
     */
    public void load(String fileName) throws UnavailableFileException {
        try (ObjectInputStream objIn = new ObjectInputStream(new BufferedInputStream(
                new FileInputStream(fileName)))) {

            _network = (Network) objIn.readObject();
            _fileName = fileName;
        } catch (IOException | ClassNotFoundException e) {
            throw new UnavailableFileException(fileName);
        }
    }

    /**
     * Saves the serialized application's state into the file associated to the current network.
     *
     * @throws FileNotFoundException           if for some reason the file cannot be created or opened.
     * @throws MissingFileAssociationException if the current network does not have a file.
     * @throws IOException                     if there is some error while serializing the state of the network to disk.
     */
    public void save() throws FileNotFoundException, MissingFileAssociationException, IOException {
        if (_fileName == null)
            throw new MissingFileAssociationException();
        try (ObjectOutputStream objOut = new ObjectOutputStream(new BufferedOutputStream(
                new FileOutputStream(_fileName)))) {

            objOut.writeObject(_network);
        }
    }

    /**
     * Saves the serialized application's state into the specified file. The current network is
     * associated to this file.
     *
     * @param fileName the name of the file.
     * @throws FileNotFoundException           if for some reason the file cannot be created or opened.
     * @throws MissingFileAssociationException if the current network does not have a file.
     * @throws IOException                     if there is some error while serializing the state of the network to disk.
     */
    public void saveAs(String fileName) throws FileNotFoundException, MissingFileAssociationException, IOException {
        _fileName = fileName;
        save();
    }

    /**
     * Read text input file and create domain entities..
     *
     * @param fileName name of the text input file
     * @throws ImportFileException if something goes wrong while reading file
     */
    public void importFile(String fileName) throws ImportFileException {
        try {
            _network.importFile(fileName);
        } catch (IOException | UnrecognizedEntryException e) {
            throw new ImportFileException(fileName, e);
        }
    }
}
