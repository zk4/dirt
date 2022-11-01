package com.zk.experiment.provider;

import com.zk.dirt.core.DirtEnumValue;
import com.zk.dirt.intef.iNoArgDatasource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SQLProvider implements iNoArgDatasource<String, DirtEnumValue> {

    @Autowired
    EntityManager entityManager;
    @Override
    public Map<String, DirtEnumValue> getSource() {
        HashMap source = new HashMap<Long, DirtEnumValue>();
        Query select_id_from_githubRepo = entityManager.createQuery("select name from GithubRepo");
        List<String> resultList = select_id_from_githubRepo.getResultList();
        for (String value : resultList) {
            source.put(value,
                    new DirtEnumValue(value, value, ""));
        }
         return source;
    }

    @Override
    public String initialValue() {
        return "";
    }
}
