package yashchuk.DTO;

import yashchuk.controller.PatientController;
import yashchuk.domain.Medicine;
import yashchuk.exceptions.NoSuchMedicineException;
import yashchuk.exceptions.NoSuchPatientException;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class MedicineDTO extends ResourceSupport {
    Medicine medicine;
    public MedicineDTO(Medicine medicine, Link selfLink) throws NoSuchMedicineException, NoSuchPatientException {
        this.medicine=medicine;
        add(selfLink);
        add(linkTo(methodOn(PatientController.class).getPatientsByMedicineID(medicine.getId())).withRel("patients"));
    }

    public Long getMedicineId() {
        return medicine.getId();
    }

    public String getMedicineName() {
        return medicine.getMedicineName();
    }

    public String getManufacturer() {
        return medicine.getManufacturer();
    }

    public Integer getAmount() {
        return medicine.getAmount();
    }
}
