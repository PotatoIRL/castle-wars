package com.phfl.pe;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phfl.pe.data.DocumentData;

public class PyxelFile {
  public DocumentData document;
  public byte[] image;

  public PyxelFile(String path) throws IOException {
    this(new File(path));
  }

  public PyxelFile(File file) throws IOException {
    ZipFile pyxelFile = new ZipFile(file);

    ZipEntry docDataEntry = pyxelFile.getEntry("docData.json");
    ObjectMapper mapper = new ObjectMapper();
    document = mapper.readValue(pyxelFile.getInputStream(docDataEntry), DocumentData.class);

    ZipEntry layer0Entry = pyxelFile.getEntry("layer0.png");
    this.image = IOUtils.toByteArray(pyxelFile.getInputStream(layer0Entry));

    pyxelFile.close();
  }
}
