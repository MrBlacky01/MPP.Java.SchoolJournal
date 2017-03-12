package school.journal.service.impl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school.journal.entity.Mark;
import school.journal.repository.IRepository;
import school.journal.repository.exception.RepositoryException;
import school.journal.repository.specification.mark.MarkSpecificationByMarkId;
import school.journal.service.IMarkService;
import school.journal.service.CRUDService;
import school.journal.service.exception.ServiceException;
import school.journal.utils.exception.ValidationException;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static school.journal.utils.ValidateServiceUtils.validateId;

@Component("MarkService")
public class MarkService extends CRUDService<Mark> implements IMarkService {

    @Autowired
    public MarkService(IRepository<Mark> repository) {
        LOGGER = Logger.getLogger(MarkService.class);
        this.repository = repository;
    }

    @Override
    public Mark create(Mark mark) throws ServiceException {
        try {
            validateId(mark.getPupilId(),"Pupil");
            validateId(mark.getSubjectId(),"Subject");
            validateId(mark.getTeacherId(),"Teacher");
        } catch (ValidationException exc) {
            LOGGER.error(exc);
            throw new ServiceException(exc);
        }
        validateValue(mark.getValue());
        validateDate(mark.getDate());
        return super.create(mark);
    }

    @Override
    public Mark update(Mark mark) throws ServiceException {
        try {
            validateId(mark.getMarkId(),"Mark");
            validateId(mark.getPupilId(),"Pupil");
            validateId(mark.getSubjectId(),"Subject");
            validateId(mark.getTeacherId(),"Teacher");
        } catch (ValidationException exc) {
            LOGGER.error(exc);
            throw new ServiceException(exc);
        }
        validateValue(mark.getValue());
        validateDate(mark.getDate());
        return super.update(mark);
    }

    @Override
    public void delete(int id) throws ServiceException {
        try {
            validateId(id,"Mark");
        } catch (ValidationException exc) {
            LOGGER.error(exc);
            throw new ServiceException(exc);
        }
        Mark mark = new Mark();
        mark.setMarkId(id);
        delete(mark);
    }


    @Override
    public List<Mark> read() throws ServiceException {
        return super.read();
    }

    @Override
    public Mark getOne(int id) throws ServiceException {
        try {

            validateId(id,"Mark");
        } catch (ValidationException exc) {
            LOGGER.error(exc);
            throw new ServiceException(exc);
        }
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            List<Mark> markList = repository.query(
                    new MarkSpecificationByMarkId(id), session);
            transaction.commit();
            return markList.size() > 0 ? markList.get(0) : null;
        } catch (RepositoryException exc) {
            transaction.rollback();
            LOGGER.error(exc);
            throw new ServiceException(exc);
        } finally {
            session.close();
        }
    }

    private void validateDate(Date date) throws ServiceException {
        Calendar c = new GregorianCalendar();
        c.add(Calendar.DAY_OF_YEAR, 1);
        if (date.after(c.getTime())) {
            throw new ServiceException("Invalid date");
        }
    }

    private void validateValue(int value) throws ServiceException {
        if (value <= 0 || value >= 11) {
            throw new ServiceException("Invalid value");
        }
    }

}