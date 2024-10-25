package org.ddd.fundamental.workprocess.domain.model;

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
        this.processList = processList;
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
