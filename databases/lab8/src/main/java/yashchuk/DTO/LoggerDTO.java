package yashchuk.DTO;

import yashchuk.domain.Logger;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

public class LoggerDTO extends ResourceSupport {
    Logger logger;
    public LoggerDTO(Logger logger, Link selfLink) {
        this.logger=logger;
        add(selfLink);
    }

    public Long getLoggerId() {
        return logger.getId();
    }

    public String getPerson() {
        return logger.getPatient();
    }

    public String getBook() {
        return logger.getMedicine();
    }

    public String getAction() {
        return logger.getAction();
    }

    public String getUser() {
        return logger.getUser();
    }

    public String getTime() {
        return logger.getTimeStamp().toString();
    }
}
