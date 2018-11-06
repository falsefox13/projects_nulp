package yashchuk.controller;

import yashchuk.DTO.MessageDTO;
import yashchuk.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchDiagnosisException.class)
    ResponseEntity<MessageDTO> handleNoSushDiagnosisException(){
        return new ResponseEntity<MessageDTO>(new MessageDTO("Such diagnosis not found"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchPatientException.class)
    ResponseEntity<MessageDTO> handleNoSushPatientException() {
        return new ResponseEntity<MessageDTO>(new MessageDTO("Such patient not found"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchMedicineException.class)
    ResponseEntity<MessageDTO> handleNoSushMedicineException() {
        return new ResponseEntity<MessageDTO>(new MessageDTO("Such medicine not found"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExistsPatientsForDiagnosisException.class)
    ResponseEntity<MessageDTO> handleExistsPatientsForDiagnosisException() {
        return new ResponseEntity<MessageDTO>(new MessageDTO("Delete imposible. There are patients for this diagnosis"), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ExistsMedicinesForPatientException.class)
    ResponseEntity<MessageDTO> handleExistsMedicinesForPatientException() {
        return new ResponseEntity<MessageDTO>(new MessageDTO("Delete imposible. There are medicines for this patient"), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ExistsPatientForMedicineException.class)
    ResponseEntity<MessageDTO> handleExistsPatientsForMedicineException() {
        return new ResponseEntity<MessageDTO>(new MessageDTO("Delete imposible. There are patients for this medicine"), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AlreadyExistsMedicineInPatientException.class)
    ResponseEntity<MessageDTO> handleAlreadyExistsMedicineInPatientExceptionException() {
        return new ResponseEntity<MessageDTO>(new MessageDTO("Add imposible. The patient already contain this medicine"), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MedicineAbsentException.class)
    ResponseEntity<MessageDTO> handleMedicineAbsentException() {
        return new ResponseEntity<MessageDTO>(new MessageDTO("Now this medicine is absent"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PatientHasNotMedicineException.class)
    ResponseEntity<MessageDTO> handlePatientHasNotMedicineException() {
        return new ResponseEntity<MessageDTO>(new MessageDTO("The patient hasn't this medicine"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchLogException.class)
    ResponseEntity<MessageDTO> handleNoSuchLogException() {
        return new ResponseEntity<MessageDTO>(new MessageDTO("Such log not found"), HttpStatus.NOT_FOUND);
    }

}
