package yashchuk.service;

import yashchuk.Repository.MedicineRepository;
import yashchuk.Repository.PatientRepository;
import yashchuk.domain.Medicine;
import yashchuk.domain.Patient;
import yashchuk.exceptions.ExistsPatientForMedicineException;
import yashchuk.exceptions.NoSuchMedicineException;
import yashchuk.exceptions.NoSuchPatientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class MedicineService {
    @Autowired
    MedicineRepository medicineRepository;

    @Autowired
    PatientRepository patientRepository;

    public Set<Medicine> getMedicinesByPatientId(Long patient_id) throws NoSuchPatientException {
//        Patient patient = patientRepository.findOne(patient_id);//1.5.9
        Patient patient = patientRepository.findById(patient_id).get();//2.0.0.M7
        if (patient == null) throw new NoSuchPatientException();
        return patient.getMedicines();
    }

    public Medicine getMedicine(Long medicine_id) throws NoSuchMedicineException {
//        Medicine medicine = medicineRepository.findOne(medicine_id);//1.5.9
        Medicine medicine = medicineRepository.findById(medicine_id).get();//2.0.0.M7
        if (medicine == null) throw new NoSuchMedicineException();
        return medicine;
    }

    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    @Transactional
    public void createMedicine(Medicine medicine) {
        medicineRepository.save(medicine);
    }

    @Transactional
    public void updateMedicine(Medicine uMedicine, Long medicine_id) throws NoSuchMedicineException {
//        Medicine medicine = medicineRepository.findOne(medicine_id);//1.5.9
        Medicine medicine = medicineRepository.findById(medicine_id).get();//2.0.0.M7
        if (medicine == null) throw new NoSuchMedicineException();
        //update
        medicine.setMedicineName(uMedicine.getMedicineName());
        medicine.setManufacturer(uMedicine.getManufacturer());
        medicine.setAmount(uMedicine.getAmount());
    }

    @Transactional
    public void deleteMedicine(Long medicine_id) throws NoSuchMedicineException, ExistsPatientForMedicineException {
//        Medicine medicine = medicineRepository.findOne(medicine_id);//1.5.9
        Medicine medicine = medicineRepository.findById(medicine_id).get();//2.0.0.M7

        if (medicine == null) throw new NoSuchMedicineException();
        if (medicine.getPatients().size() != 0) throw new ExistsPatientForMedicineException();
        medicineRepository.delete(medicine);
    }
}
