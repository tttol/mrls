package io.github.tttol.mrls.dto;

import java.util.List;

import io.github.tttol.mrls.form.MrInfoForm;

public interface IRequest {
    List<MrInfoForm> toForm();
}
