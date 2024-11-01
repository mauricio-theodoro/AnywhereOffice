package com.example.office.service;

import com.example.office.entity.Documents;
import com.example.office.repository.DocumentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Date;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class DocumentsService {

    @Autowired
    private DocumentsRepository documentsRepository;

    public Documents saveDocument(byte[] content, String originalFilename, long size, Long userId) throws IOException {
        String fileName = StringUtils.cleanPath(originalFilename);

        Documents documents = new Documents();
        documents.setName(fileName);
        documents.setContent(content);
        documents.setSize(size);
        documents.setUploadTime(new Date());
        documents.setUserId(userId); // Adicione esta linha

        return documentsRepository.save(documents);
    }
}
