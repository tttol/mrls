package io.github.tttol.mrls.presentation;

import java.util.List;

import io.github.tttol.mrls.dto.RequestDto;
import io.github.tttol.mrls.form.RequestInfoForm;

public interface IPresenter {
    List<RequestInfoForm> convert(final List<RequestDto> requests);
}
