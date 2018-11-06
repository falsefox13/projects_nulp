package yashchuk.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id", nullable = false)
    private Long id;
    @Column(name = "surname", nullable = false, length = 25)
    private String surname;
    @Column(name = "name", nullable = false, length = 25)
    private String name;
    @Column(name = "mobile_number", nullable = true, length = 45)
    private String number;
    @ManyToOne
    @JoinColumn(name = "diagnosis_id", referencedColumnName = "diagnosis_id")
    private Diagnosis diagnosis;
    @ManyToMany
    @JoinTable(name = "prescription",
            joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "patient_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "medicine_id", referencedColumnName = "medicine_id", nullable = false))
    private Set<Medicine> medicines;

    Patient() {
    }

    Patient(String surname, String name, String number) {
        this.surname = surname;
        this.name = name;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long idPatient) {
        this.id = idPatient;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Set<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(Set<Medicine> medicines) {
        this.medicines = medicines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient that = (Patient) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        return result;
    }
}
