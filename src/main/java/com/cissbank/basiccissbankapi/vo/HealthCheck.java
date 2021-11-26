package com.cissbank.basiccissbankapi.vo;

import java.util.Objects;

public class HealthCheck {

    private final long id;
    private final String content;

    public HealthCheck(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HealthCheck)) return false;
        HealthCheck that = (HealthCheck) o;
        return id == that.id && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content);
    }

    @Override
    public String toString() {
        return "HealthCheck{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
