package com.example.office;

import com.example.office.entity.Documents;
import com.example.office.repository.DocumentsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OfficeApplicationTests {

	@Autowired
	private DocumentsRepository repo;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	@Rollback(false)
	void testInsertDocument() throws IOException {
		File file = new File("src/main/resources/static/UPX.docx");
		Documents documents = new Documents();

		byte[] bytes = Files.readAllBytes(file.toPath());
		documents.setContent(bytes);
		long fileSize = bytes.length;
		documents.setSize(fileSize);
		documents.setName("teste1");
		documents.setUploadTime(new Date());

		Documents savedDoc = repo.save(documents);

		Documents existDoc = entityManager.find(Documents.class, savedDoc.getId());

		// Verifique o tamanho do arquivo
		assertThat(existDoc.getSize()).isEqualTo(fileSize);
		// Verifique o nome do arquivo
		assertThat(existDoc.getName()).isEqualTo("teste1");
		// Verifique o conteúdo (opcional)
		assertThat(existDoc.getContent()).isEqualTo(bytes);
	}


}

