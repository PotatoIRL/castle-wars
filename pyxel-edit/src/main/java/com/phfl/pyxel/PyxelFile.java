package com.phfl.pyxel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phfl.pyxel.model.DocumentData;

public class PyxelFile {
  public DocumentData document;
  public byte[] image;

  public PyxelFile(String path) throws IOException {
    this(new File(path));
  }

  public PyxelFile(File file) throws IOException {
    this(new FileInputStream(file));
  }

  public PyxelFile(InputStream fileStream) throws IOException {
    ZipArchiveInputStream zis = new ZipArchiveInputStream(fileStream);
    for (ZipArchiveEntry ze; (ze = zis.getNextZipEntry()) != null;) {
      if (ze.getName().equals("docData.json")) {
        document = new ObjectMapper().readValue(IOUtils.toByteArray(zis), DocumentData.class);
      }
      if (ze.getName().equals("layer0.png")) {
        image = IOUtils.toByteArray(zis);
      }
    }
  }
}
