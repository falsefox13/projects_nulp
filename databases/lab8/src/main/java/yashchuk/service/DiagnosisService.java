package yashchuk.service;

import yashchuk.Repository.DiagnosisRepository;
import yashchuk.Repository.PatientRepository;
import yashchuk.domain.Diagnosis;
import yashchuk.exceptions.ExistsPatientsForDiagnosisException;
import yashchuk.exceptions.NoSuchDiagnosisException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DiagnosisService {
    @Autowired
    DiagnosisRepository diagnosisRepository;
    private boolean ascending;

    @Autowired
    PatientRepository patientRepository;

    public List<Diagnosis> getAllDiagnosis() {
        return diagnosisRepository.findAll();
    }

    public Diagnosis getDiagnosis(Long diagnosis_id) throws NoSuchDiagnosisException {
//        Diagnosis diagnosis =diagnosisRepository.findOne(diagnosis_id);//1.5.9
        Diagnosis diagnosis = diagnosisRepository.findById(diagnosis_id).get();//2.0.0.M7
        if (diagnosis == null) throw new NoSuchDiagnosisException();
        return diagnosis;
    }

    @Transactional
    public void createDiagnosis(Diagnosis diagnosis) {
        diagnosisRepository.save(diagnosis);
    }

    @Transactional
    public void updateDiagnosis(Diagnosis uDiagnosis, Long diagnosis_id) throws NoSuchDiagnosisException {
//        Diagnosis diagnosis = diagnosisRepository.findOne(diagnosis_id);//1.5.9
        Diagnosis diagnosis = diagnosisRepository.findById(diagnosis_id).get();//2.0.0.M7

        if (diagnosis == null) throw new NoSuchDiagnosisException();
        diagnosis.setDiagnosis(uDiagnosis.getDiagnosis());
        diagnosisRepository.save(diagnosis);
    }

    @Transactional
    public void deleteDiagnosis(Long diagnosis_id) throws NoSuchDiagnosisException, ExistsPatientsForDiagnosisException {
//        Diagnosis diagnosis = diagnosisRepository.findOne(diagnosis_id);//1.5.9
        Diagnosis diagnosis = diagnosisRepository.findById(diagnosis_id).get();//2.0.0.M7
        if (diagnosis == null) throw new NoSuchDiagnosisException();
        if (diagnosis.getPatients().size() != 0) throw new ExistsPatientsForDiagnosisException();
        diagnosisRepository.delete(diagnosis);
    }


}
