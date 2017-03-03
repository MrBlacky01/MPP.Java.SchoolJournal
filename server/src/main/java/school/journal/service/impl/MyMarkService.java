package school.journal.service.impl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import school.journal.entity.Mark;
import school.journal.repository.IRepository;
import school.journal.repository.exception.RepositoryException;
import school.journal.repository.specification.mark.MarkSpecificationByMarkId;
import school.journal.service.IMarkService;
import school.journal.service.Service;
import school.journal.service.exception.ServiceException;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MyMarkService extends Service<Mark> implements IMarkService{

    @Override
    public Mark create(Mark mark) throws ServiceException {
        validateId(mark.getPupilId());
        validateId(mark.getSubjectId());
        validateId(mark.getTeacherId());
        validateValue(mark.getValue());
        validateDate(mark.getDate());
        return super.create(mark);
    }

    @Override
    public Mark update(Mark mark) throws ServiceException {
        validateId(mark.getMarkId());
        validateId(mark.getPupilId());
        validateId(mark.getSubjectId());
        validateId(mark.getTeacherId());
        validateValue(mark.getValue());
        validateDate(mark.getDate());
        return super.update(mark);
    }

    @Override
    public void delete(int id) throws ServiceException {
        validateId(id);
        Mark mark = new Mark();
        mark.setMarkId(id);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            markRepository.delete(mark, session);
            transaction.commit();
        } catch (RepositoryException exc) {
            transaction.rollback();
            LOGGER.error(exc);
            throw new ServiceException(exc);
        } finally {
            session.close();
        }
    }


    @Override
    public List<Mark> read() throws ServiceException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Mark> markList;
        try {
            markList = markRepository.query(null, session);
            transaction.commit();
        } catch (RepositoryException exc) {
            transaction.rollback();
            LOGGER.error(exc);
            throw new ServiceException(exc);
        } finally {
            session.close();
        }
        return markList;
    }

    @Override
    public Mark getOne(int id) throws ServiceException {
        validateId(id);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            List<Mark> markList = markRepository.query(
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

    private void validateId(int id) throws ServiceException {
        if (id <= 0) throw new ServiceException("Invalid Id");
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
