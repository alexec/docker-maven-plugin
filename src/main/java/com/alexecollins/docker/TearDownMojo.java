package com.alexecollins.docker;

import com.alexecollins.docker.model.Id;

import java.util.Collections;
import java.util.List;
import java.util.Map;

abstract class TearDownMojo extends AbstractDockersMojo {

    @Override
    protected List<Id> sort(Map<Id, List<Id>> links) {
        // reverse order for tear-down type mojos
        final List<Id> sort = super.sort(links);
        Collections.reverse(sort);
        return sort;
    }

}
