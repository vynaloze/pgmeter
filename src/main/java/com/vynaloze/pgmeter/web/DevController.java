package com.vynaloze.pgmeter.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;

@RestController
public class DevController {
    @RequestMapping(method = RequestMethod.GET, value = "/dev/size/{filename}")
    ResponseEntity<?> getSize(final @PathVariable String filename) {
        final var files = new HashMap<String, Long>();
        try {
            Files.walkFileTree(Paths.get("/"), new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.endsWith(filename)) {
                        files.put(file.toString(), file.toFile().length());
                    }
                    return super.visitFile(file, attrs);
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.SKIP_SUBTREE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(files, HttpStatus.OK);
    }
}
