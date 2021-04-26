package com.weather.api.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class ProfileHistoryResponse implements Serializable {

    private List<String> profileList;
}
