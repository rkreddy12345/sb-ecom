package com.rkecom.crud.product.service.impl;

import com.rkecom.crud.core.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadFile ( String path, MultipartFile file ) throws IOException {
        String originalFilename = file.getOriginalFilename ();
        String uuid= UUID.randomUUID ().toString ();
        String fileName="";
        if(Objects.nonNull (originalFilename)){
            fileName=uuid.concat ( originalFilename.substring ( originalFilename.lastIndexOf ( "." ) ) );
        }
        String filePath=path + File.separator + fileName;
        File folder=new File ( path );
        if(!folder.exists ()){
            folder.mkdir();
        }
        Files.copy ( file.getInputStream (), Paths.get (filePath) );
        return fileName;
    }
}
