package school.journal.repository.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Component;
import school.journal.entity.Clazz;
import school.journal.repository.RepositoryAbstractClass;
import school.journal.repository.exception.RepositoryException;
import school.journal.repository.specification.HibernateSpecification;

import java.util.List;
@Component
public class ClazzRepository extends RepositoryAbstractClass<Clazz> {

    @Override
    public Clazz create(Clazz clazz, Session session) throws RepositoryException {
        session.save(clazz);
        return clazz;
    }

    @Override
    public Clazz update(Clazz clazz, Session session) throws RepositoryException {
        session.update(clazz);
        return clazz;
    }

    @Override
    public Clazz delete(Clazz clazz, Session session) throws RepositoryException {
        session.delete(clazz);
        return clazz;
    }

    @Override
    public List<Clazz> query(HibernateSpecification specification, Session session) throws RepositoryException {
        Criteria criteria =  session.createCriteria(Clazz.class);
        Criterion criterion;
        if((specification != null) && ((criterion = specification.toCriteria()) != null)){
            criteria.add(criterion);
        }
        return criteria.list();
    }
}