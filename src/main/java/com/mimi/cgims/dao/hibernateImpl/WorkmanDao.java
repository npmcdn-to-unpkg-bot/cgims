package com.mimi.cgims.dao.hibernateImpl;

import com.mimi.cgims.dao.IWorkmanDao;
import com.mimi.cgims.model.WorkmanModel;
import org.springframework.stereotype.Repository;

@Repository
public class WorkmanDao extends BaseDao<WorkmanModel, String>
        implements IWorkmanDao {

}
