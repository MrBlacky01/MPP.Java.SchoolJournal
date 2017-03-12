package school.journal.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school.journal.entity.Subject;
import school.journal.repository.IRepository;
import school.journal.service.CRUDService;
import school.journal.service.ISubjectService;
import school.journal.service.exception.ServiceException;
import school.journal.utils.exception.ValidationException;

import java.util.List;

import static school.journal.utils.ValidateServiceUtils.*;

@Component("SubjectService")
public class SubjectService extends CRUDService<Subject> implements ISubjectService {

    @Autowired
    public SubjectService(IRepository<Subject> repository) {
        LOGGER = Logger.getLogger(SubjectService.class);
        this.repository = repository;
    }

    @Override
    public Subject create(Subject subject) throws ServiceException {
        try {
            validateString(subject.getName(), "Name");
            validateString(subject.getDescription(), "Description");
        } catch (ValidationException exc) {
            LOGGER.error(exc);
            throw new ServiceException(exc);
        }
        return super.create(subject);
    }

    @Override
    public Subject update(Subject subject) throws ServiceException {
        try {
            validateId(subject.getSubjectId(), "Subject");
            validateString(subject.getName(), "Name");
            validateString(subject.getDescription(), "Description");
        } catch (ValidationException exc) {
            LOGGER.error(exc);
            throw new ServiceException(exc);
        }
        return super.update(subject);
    }

    @Override
    public void delete(int subjectId) throws ServiceException {
        try {
            validateId(subjectId, "Subject");
        } catch (ValidationException exc) {
            LOGGER.error(exc);
            throw new ServiceException(exc);
        }
        Subject subject = new Subject();
        subject.setSubjectId(subjectId);
        super.delete(subject);
    }

    @Override
    public List<Subject> read() throws ServiceException {
        return super.read();
    }
}