package yashchuk.DTO;

import yashchuk.controller.MedicineController;
import yashchuk.domain.Patient;
import yashchuk.exceptions.NoSuchMedicineException;
import yashchuk.exceptions.NoSuchPatientException;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class PatientDTO extends ResourceSupport {
    Patient patient;
    public PatientDTO(Patient patient, Link selfLink) throws NoSuchPatientException, NoSuchMedicineException {
        this.patient=patient;
        add(selfLink);
        add(linkTo(methodOn(MedicineController.class).getMedicinesByPatientID(patient.getId())).withRel("medicines"));
    }

    public Long getPatientId() {
        return patient.getId();
    }

    public String getSurname() {
        return patient.getSurname();
    }

    public String getName() {
        return patient.getName();
    }

    public String getNumber() {
        return patient.getNumber();
    }

    public String getDiagnosis() {
        if(patient.getDiagnosis()==null) return "";
        return patient.getDiagnosis().getDiagnosis();
    }

}
