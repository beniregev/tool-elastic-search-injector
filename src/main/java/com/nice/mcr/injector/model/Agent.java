package com.nice.mcr.injector.model;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@ToString
public class Agent implements Serializable {
    private long agentId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String osLogin;
    private String email;
    private int amountInList;

    public void incrementAmountInList() {
        this.amountInList++;
    }

    public Agent(long agentId, String firstName, String lastName) {
        this(agentId, firstName, null, lastName);
    }

    public Agent(long agentId, String firstName, String middleName, String lastName) {
        this.agentId = agentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.amountInList = 1;      //  initial value
        this.osLogin = firstName + "." + lastName;
        this.email = firstName + "." + lastName + '@' + "mail.com";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agent agent = (Agent) o;
        return agentId == agent.agentId &&
                firstName.equals(agent.firstName) &&
                Objects.equals(middleName, agent.middleName) &&
                lastName.equals(agent.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(agentId, firstName, middleName, lastName);
    }
}
