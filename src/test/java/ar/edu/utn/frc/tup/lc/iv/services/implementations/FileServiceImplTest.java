package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.FileDto;
import ar.edu.utn.frc.tup.lc.iv.entities.FileEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.FileOwnerRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.FilePlotRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.FileRepository;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.FileManagerClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileServiceImplTest {

    @InjectMocks
    private FileServiceImpl fileService;

    @Mock
    private FileRepository fileRepository;

    @Mock
    private FilePlotRepository filePlotRepository;

    @Mock
    private FileOwnerRepository fileOwnerRepository;

    @Mock
    private FileManagerClient fileManagerClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fileService = new FileServiceImpl(fileRepository, filePlotRepository, fileOwnerRepository, fileManagerClient);
    }

    @Test
    void getPlotFiles_ShouldReturnListOfFileDtos_WhenFilesExist() {
        Integer plotId = 1;

        FileEntity file1 = FileEntity.builder()
                .id(1)
                .fileUuid("uuid1")
                .name("file1")
                .createdDatetime(LocalDateTime.now())
                .createdUser(1)
                .lastUpdatedDatetime(LocalDateTime.now())
                .lastUpdatedUser(1)
                .build();

        FileEntity file2 = FileEntity.builder()
                .id(2)
                .fileUuid("uuid2")
                .name("file2")
                .createdDatetime(LocalDateTime.now())
                .createdUser(1)
                .lastUpdatedDatetime(LocalDateTime.now())
                .lastUpdatedUser(1)
                .build();

        when(fileRepository.findFileByPlotId(plotId)).thenReturn(Arrays.asList(file1, file2));

        List<FileDto> result = fileService.getPlotFiles(plotId);

        assertEquals(2, result.size());
        assertEquals("file1", result.get(0).getName());
        assertEquals("uuid1", result.get(0).getUuid());
        assertEquals("file2", result.get(1).getName());
        assertEquals("uuid2", result.get(1).getUuid());
    }

    @Test
    void getPlotFiles_ShouldReturnEmptyList_WhenNoFilesExist() {
        Integer plotId = 1;
        when(fileRepository.findFileByPlotId(plotId)).thenReturn(Collections.emptyList());

        List<FileDto> result = fileService.getPlotFiles(plotId);

        assertEquals(0, result.size());
    }

    @Test
    void getOwnerFiles_ShouldReturnListOfFileDtos_WhenFilesExist() {
        Integer ownerId = 2;

        FileEntity file1 = FileEntity.builder()
                .id(3)
                .fileUuid("uuid3")
                .name("file3")
                .createdDatetime(LocalDateTime.now())
                .createdUser(1)
                .lastUpdatedDatetime(LocalDateTime.now())
                .lastUpdatedUser(1)
                .build();

        FileEntity file2 = FileEntity.builder()
                .id(4)
                .fileUuid("uuid4")
                .name("file4")
                .createdDatetime(LocalDateTime.now())
                .createdUser(1)
                .lastUpdatedDatetime(LocalDateTime.now())
                .lastUpdatedUser(1)
                .build();

        when(fileRepository.findFileByOwnerId(ownerId)).thenReturn(Arrays.asList(file1, file2));

        List<FileDto> result = fileService.getOwnerFiles(ownerId);

        assertEquals(2, result.size());
        assertEquals("file3", result.get(0).getName());
        assertEquals("uuid3", result.get(0).getUuid());
        assertEquals("file4", result.get(1).getName());
        assertEquals("uuid4", result.get(1).getUuid());
    }

    @Test
    void getOwnerFiles_ShouldReturnEmptyList_WhenNoFilesExist() {
        Integer ownerId = 2;
        when(fileRepository.findFileByOwnerId(ownerId)).thenReturn(Collections.emptyList());

        List<FileDto> result = fileService.getOwnerFiles(ownerId);

        assertEquals(0, result.size());
    }
}
