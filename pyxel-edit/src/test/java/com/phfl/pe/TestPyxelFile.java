package com.phfl.pe;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestPyxelFile {
  @Test
  public void testLoadFile() throws IOException, URISyntaxException {
    URL resource = Object.class.getResource("/base-human.pyxel");

    PyxelFile pyxelFile = new PyxelFile(resource.getFile());

    System.out.println(new ObjectMapper().writeValueAsString(pyxelFile.document));
  }
}
