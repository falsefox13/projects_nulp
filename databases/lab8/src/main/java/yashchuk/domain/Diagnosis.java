package yashchuk.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Diagnosis")
public class Diagnosis {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diagnosis_id", nullable = false)
    private Long id;
    @Column(name = "diagnosis", nullable = false, length = 100)
    private String diagnosis;
    @OneToMany(mappedBy = "diagnosis")
    private List<Patient> patients;

    Diagnosis(){}
    Diagnosis(String diagnosis){
        this.diagnosis=diagnosis;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long idDiagnosis) {
        this.id = idDiagnosis;
    }

    public String getDiagnosis() {
        return diagnosis;
    }
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public List<Patient> getPatients() {
        return patients;
    }
    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Diagnosis that = (Diagnosis) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (diagnosis != null ? !diagnosis.equals(that.diagnosis) : that.diagnosis != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (diagnosis != null ? diagnosis.hashCode() : 0);
        return result;
    }
}
