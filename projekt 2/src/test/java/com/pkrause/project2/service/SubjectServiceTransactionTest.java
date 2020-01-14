package com.pkrause.project2.service;

import com.pkrause.project2.domain.SubjectEntity;
import com.pkrause.project2.fixture.EntityFixture;
import com.pkrause.project2.repository.SubjectRepository;
import com.pkrause.project2.result.MultipleResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SubjectServiceTransactionTest {
    private EntityFixture entityFixture = new EntityFixture();

    private SubjectRepository repository;
    private SubjectService service;

    @Autowired
    public SubjectServiceTransactionTest(SubjectRepository repository, SubjectService service) {
        this.repository = repository;
        this.service = service;
    }

    @AfterEach
    public void afterTest() {
        this.repository.deleteAll();
    }

    @Test
    public void GivenCreate_WhenGivenInvalidEntities_ReturnsFailedResult() {
        SubjectEntity validSubjectEntity = this.entityFixture.validSubjectEntity(null);
        SubjectEntity invalidSubjectEntity = this.entityFixture.invalidSubjectEntity;

        List<SubjectEntity> subjects = Arrays.asList(validSubjectEntity, invalidSubjectEntity);

        try {
            service.create(subjects);
        } catch (Exception e) {
            e.printStackTrace();
        }

        MultipleResult<SubjectEntity> readResult = this.service.read();

        assertFalse(readResult.isSuccess());
        assertEquals(0, readResult.getResult().size());
    }

    @Test
    public void GivenCreate_WhenGivenValidEntity_ReturnsFailedResult() {
        SubjectEntity validSubjectEntity1 = this.entityFixture.validSubjectEntity(null);
        SubjectEntity validSubjectEntity2 = this.entityFixture.validSubjectEntity(null);

        List<SubjectEntity> subjects = Arrays.asList(validSubjectEntity1, validSubjectEntity2);

        try {
            service.create(subjects);
        } catch (Exception e) {
            e.printStackTrace();
        }

        MultipleResult<SubjectEntity> readResult = this.service.read();

        assertTrue(readResult.isSuccess());
        assertEquals(2, readResult.getResult().size());
    }
}
