package yashchuk.DTO;

import yashchuk.controller.PatientController;
import yashchuk.domain.Diagnosis;
import yashchuk.exceptions.NoSuchMedicineException;
import yashchuk.exceptions.NoSuchDiagnosisException;
import yashchuk.exceptions.NoSuchPatientException;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class DiagnosisDTO extends ResourceSupport {
    Diagnosis diagnosis;
    public DiagnosisDTO(Diagnosis diagnosis, Link selfLink) throws NoSuchPatientException, NoSuchMedicineException, NoSuchDiagnosisException {
        this.diagnosis=diagnosis;
        add(selfLink);
        add(linkTo(methodOn(PatientController.class).getPatientsByDiagnosisID(diagnosis.getId())).withRel("patients"));
    }

    public Long getDiagnosisId() { return diagnosis.getId(); }

    public String getDiagnosis() {
        return diagnosis.getDiagnosis();
    }
}
