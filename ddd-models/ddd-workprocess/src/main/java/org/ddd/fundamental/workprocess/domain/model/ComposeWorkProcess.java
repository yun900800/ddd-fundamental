package org.ddd.fundamental.workprocess.domain.model;

import org.ddd.fundamental.workprocess.value.WorkProcessId;

import java.util.*;

public class ComposeWorkProcess {

    private WorkProcessId workProcessId;

    private String processName;

    private List<WorkProcess> processList;

    public ComposeWorkProcess(String processName) {
        this.workProcessId = WorkProcessId.randomId(WorkProcessId.class);
        this.processName = processName;
    }

    public ComposeWorkProcess(String processName, List<WorkProcess> processList){
        this(processName);
        validate(processList);
        this.processList = processList;
    }

    private static void validate(List<WorkProcess> processList){
        int size = processList.size();
        for (int i = 1 ; i < size-1; i++) {
            WorkProcess pre = processList.get(i-1);
            WorkProcess cur = processList.get(i);
            if (!pre.acceptNext(cur) || !cur.acceptPre(pre)) {
                throw new RuntimeException("工序不正确");
            }
        }
    }

    public Set<WorkProcess> getPreProcesses() {
        WorkProcess pre = processList.get(0);
        return new HashSet<>(pre.getPreProcesses());
    }

    public Set<WorkProcess> getNextProcesses() {
        int size = processList.size();
        WorkProcess next = processList.get(size-1);
        return new HashSet<>(next.getNextProcesses());
    }

    public String getProcessName() {
        return processName;
    }

    public List<WorkProcess> getProcessList() {
        return new ArrayList<>(processList);
    }
}
