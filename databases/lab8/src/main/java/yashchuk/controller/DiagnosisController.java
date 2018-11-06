package yashchuk.controller;

import yashchuk.DTO.DiagnosisDTO;
import yashchuk.domain.Diagnosis;
import yashchuk.exceptions.ExistsPatientsForDiagnosisException;
import yashchuk.exceptions.NoSuchMedicineException;
import yashchuk.exceptions.NoSuchDiagnosisException;
import yashchuk.exceptions.NoSuchPatientException;
import yashchuk.service.DiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class DiagnosisController {
    @Autowired
    DiagnosisService diagnosisService;

    @GetMapping(value = "/api/diagnosis")
    public ResponseEntity<List<DiagnosisDTO>> getAllDiagnosis() throws NoSuchPatientException, NoSuchMedicineException, NoSuchDiagnosisException {
        List<Diagnosis> diagnosisList = diagnosisService.getAllDiagnosis();
        Link link = linkTo(methodOn(DiagnosisController.class).getAllDiagnosis()).withSelfRel();

        List<DiagnosisDTO> diagnosisDTO = new ArrayList<>();
        for (Diagnosis entity : diagnosisList) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getId()).withSelfRel();
            DiagnosisDTO dto = new DiagnosisDTO(entity, selfLink);
            diagnosisDTO.add(dto);
        }

        return new ResponseEntity<>(diagnosisDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/diagnosis/{diagnosis_id}")
    public ResponseEntity<DiagnosisDTO> getDiagnosis(@PathVariable Long diagnosis_id) throws NoSuchDiagnosisException, NoSuchPatientException, NoSuchMedicineException {
        Diagnosis diagnosis = diagnosisService.getDiagnosis(diagnosis_id);
        Link link = linkTo(methodOn(DiagnosisController.class).getDiagnosis(diagnosis_id)).withSelfRel();

        DiagnosisDTO diagnosisDTO = new DiagnosisDTO(diagnosis, link);

        return new ResponseEntity<>(diagnosisDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/api/diagnosis/{diagnosis_id}")
    public  ResponseEntity<DiagnosisDTO> addDiagnosis(@RequestBody Diagnosis newDiagnosis) throws NoSuchDiagnosisException, NoSuchPatientException, NoSuchMedicineException {
        diagnosisService.createDiagnosis(newDiagnosis);
        Link link = linkTo(methodOn(DiagnosisController.class).getDiagnosis(newDiagnosis.getId())).withSelfRel();

        DiagnosisDTO diagnosisDTO = new DiagnosisDTO(newDiagnosis, link);

        return new ResponseEntity<>(diagnosisDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/api/diagnosis/{diagnosis_id}")
    public  ResponseEntity<DiagnosisDTO> updateDiagnosis(@RequestBody Diagnosis udiagnosis, @PathVariable Long diagnosis_id) throws NoSuchDiagnosisException, NoSuchPatientException, NoSuchMedicineException {
        diagnosisService.updateDiagnosis(udiagnosis, diagnosis_id);
        Diagnosis diagnosis = diagnosisService.getDiagnosis(diagnosis_id);
        Link link = linkTo(methodOn(DiagnosisController.class).getDiagnosis(diagnosis_id)).withSelfRel();

        DiagnosisDTO diagnosisDTO = new DiagnosisDTO(diagnosis, link);

        return new ResponseEntity<>(diagnosisDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/diagnosis/{diagnosis_id}")
    public  ResponseEntity deleteDiagnosis(@PathVariable Long diagnosis_id) throws NoSuchDiagnosisException, ExistsPatientsForDiagnosisException {
        diagnosisService.deleteDiagnosis(diagnosis_id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
