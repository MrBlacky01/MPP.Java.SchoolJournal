package school.journal.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school.journal.entity.SubjectInSchedule;
import school.journal.repository.IRepository;
import school.journal.service.CRUDService;
import school.journal.service.ISubjectInScheduleService;
import school.journal.service.exception.ServiceException;
import school.journal.utils.exception.ValidationException;

import java.sql.Time;
import java.util.List;

import static school.journal.utils.ValidateServiceUtils.validateId;
import static school.journal.utils.ValidateServiceUtils.validateNullableId;
import static school.journal.utils.ValidateServiceUtils.validateString;

@Component("SubjectInScheduleService")
public class SubjectInScheduleService extends CRUDService<SubjectInSchedule> implements ISubjectInScheduleService {

    @Autowired
    public SubjectInScheduleService(IRepository<SubjectInSchedule> repository) {
        LOGGER = Logger.getLogger(SubjectInSchedule.class);
        this.repository = repository;
    }

    private void checkTime(Time time) throws ServiceException{
        if(time.before(new Time(7,0,0))
                || time.after(new Time(20,0,0)) )
            throw new ServiceException("Invalid begin time of subject");
    }

    @Override
    public SubjectInSchedule create(SubjectInSchedule subject) throws ServiceException {
        try {
            validateId(subject.getClassId(),"Class");
            validateNullableId(subject.getTeacherId(),"Teacher");
            validateString(subject.getPlace(),"Place");
        } catch (ValidationException exc) {
            LOGGER.error(exc);
            throw new ServiceException(exc);
        }
        checkTime(subject.getBeginTime());
        return super.create(subject);
    }

    @Override
    public SubjectInSchedule update(SubjectInSchedule subject) throws ServiceException {
        try {
            validateId(subject.getSubectInScheduleId(),"SubjectInSchedule");
            validateId(subject.getClassId(),"Class");
            validateNullableId(subject.getTeacherId(),"Teacher");
            validateString(subject.getPlace(),"Place");
        } catch (ValidationException exc) {
            LOGGER.error(exc);
            throw new ServiceException(exc);
        }
        checkTime(subject.getBeginTime());
        return super.update(subject);
    }

    @Override
    public void delete(int id) throws ServiceException {
        try {
            validateId(id, "SubjectInSchedule");
        } catch (ValidationException exc) {
            LOGGER.error(exc);
            throw new ServiceException(exc);
        }
        SubjectInSchedule subject = new SubjectInSchedule();
        subject.setSubectInScheduleId(id);
        super.delete(subject);
    }

    @Override
    public List<SubjectInSchedule> read() throws ServiceException {
        return super.read();
    }
    
}
