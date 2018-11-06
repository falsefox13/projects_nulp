package yashchuk.service;

import yashchuk.Repository.MedicineRepository;
import yashchuk.Repository.DiagnosisRepository;
import yashchuk.Repository.PatientRepository;
import yashchuk.domain.Medicine;
import yashchuk.domain.Diagnosis;
import yashchuk.domain.Patient;
import yashchuk.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class PatientService {
    @Autowired
    PatientRepository patientRepository;

    @Autowired
    DiagnosisRepository diagnosisRepository;

    @Autowired
    MedicineRepository medicineRepository;

    public List<Patient> getPatientByDiagnosisId(Long diagnosis_id) throws NoSuchDiagnosisException {
//        Diagnosis diagnosis = diagnosisRepository.findOne(diagnosis_id);//1.5.9
        Diagnosis diagnosis = diagnosisRepository.findById(diagnosis_id).get();//2.0.0.M7
        if (diagnosis == null) throw new NoSuchDiagnosisException();
        return diagnosis.getPatients();
    }

    public Patient getPatient(Long patient_id) throws NoSuchPatientException {
//        Patient patient = patientRepository.findOne(patient_id);//1.5.9
        Patient patient = patientRepository.findById(patient_id).get();//2.0.0.M7
        if (patient == null) throw new NoSuchPatientException();
        return patient;
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Set<Patient> getPatientsByMedicineId(Long medicine_id) throws NoSuchMedicineException {
//        Medicine medicine = medicineRepository.findOne(medicine_id);//1.5.9
        Medicine medicine = medicineRepository.findById(medicine_id).get();//2.0.0.M7
        if (medicine == null) throw new NoSuchMedicineException();
        return medicine.getPatients();
    }

    @Transactional
    public void createPatient(Patient patient, Long diagnosis_id) throws NoSuchDiagnosisException {
        if (diagnosis_id > 0) {
//            Diagnosis diagnosis = diagnosisRepository.findOne(diagnosis_id);//1.5.9
            Diagnosis diagnosis = diagnosisRepository.findById(diagnosis_id).get();//2.0.0.M7
            if (diagnosis == null) throw new NoSuchDiagnosisException();
            patient.setDiagnosis(diagnosis);
        }
        patientRepository.save(patient);
    }

    @Transactional
    public void updatePatient(Patient uPatient, Long patient_id, Long diagnosis_id) throws NoSuchDiagnosisException, NoSuchPatientException {
//        Diagnosis diagnosis = diagnosisRepository.findOne(diagnosis_id);//1.5.9
        Diagnosis diagnosis = diagnosisRepository.findById(diagnosis_id).get();//2.0.0.M7
        if (diagnosis_id > 0) {
            if (diagnosis == null) throw new NoSuchDiagnosisException();
        }
//        Patient patient = patientRepository.findOne(patient_id);//1.5.9
        Patient patient = patientRepository.findById(patient_id).get();//2.0.0.M7
        if (patient == null) throw new NoSuchPatientException();
        //update
        patient.setSurname(uPatient.getSurname());
        patient.setName(uPatient.getName());
        patient.setNumber(uPatient.getNumber());
        if (diagnosis_id > 0) patient.setDiagnosis(diagnosis);
        else patient.setDiagnosis(null);
        patientRepository.save(patient);
    }

    @Transactional
    public void deletePatient(Long patient_id) throws NoSuchPatientException, ExistsMedicinesForPatientException {
//        Patient patient = patientRepository.findOne(patient_id);//1.5.9
        Patient patient = patientRepository.findById(patient_id).get();//2.0.0.M7
        if (patient == null) throw new NoSuchPatientException();
        if (patient.getMedicines().size() != 0) throw new ExistsMedicinesForPatientException();
        patientRepository.delete(patient);
    }

    @Transactional
    public void addMedicineForPatient(Long patient_id, Long medicine_id)
            throws NoSuchPatientException, NoSuchMedicineException, AlreadyExistsMedicineInPatientException, MedicineAbsentException {
//        Patient patient = patientRepository.findOne(patient_id);//1.5.9
        Patient patient = patientRepository.findById(patient_id).get();//2.0.0.M7
        if (patient == null) throw new NoSuchPatientException();
//        Medicine medicine = medicineRepository.findOne(medicine_id);//1.5.9
        Medicine medicine = medicineRepository.findById(medicine_id).get();//2.0.0.M7
        if (medicine == null) throw new NoSuchMedicineException();
        if (patient.getMedicines().contains(medicine) == true) throw new AlreadyExistsMedicineInPatientException();
        if (medicine.getAmount() <= medicine.getPatients().size()) throw new MedicineAbsentException();
        patient.getMedicines().add(medicine);
        patientRepository.save(patient);
    }

    @Transactional
    public void removeMedicineForPatient(Long patient_id, Long medicine_id)
            throws NoSuchPatientException, NoSuchMedicineException, PatientHasNotMedicineException {
//        Patient patient = patientRepository.findOne(patient_id);//1.5.9
        Patient patient = patientRepository.findById(patient_id).get();//2.0.0.M7
        if (patient == null) throw new NoSuchPatientException();
//        Medicine medicine = medicineRepository.findOne(medicine_id);//1.5.9
        Medicine medicine = medicineRepository.findById(medicine_id).get();//2.0.0.M7
        if (medicine == null) throw new NoSuchMedicineException();
        if (patient.getMedicines().contains(medicine) == false) throw new PatientHasNotMedicineException();
        patient.getMedicines().remove(medicine);
        patientRepository.save(patient);
    }
}
