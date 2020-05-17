package com.kokotripadmin.service.interfaces.tourspot;

import com.kokotripadmin.dto.common.LocatableAutoCompleteDto;
import com.kokotripadmin.dto.tourspot.TourSpotDto;
import com.kokotripadmin.dto.tourspot.TourSpotInfoDto;
import com.kokotripadmin.exception.city.CityNotFoundException;
import com.kokotripadmin.exception.day_of_week.DayOfWeekNotFoundException;
import com.kokotripadmin.exception.region.RegionMismatchException;
import com.kokotripadmin.exception.region.RegionNotFoundException;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.tag.TagInfoNotFoundException;
import com.kokotripadmin.exception.tag.TagNotFoundException;
import com.kokotripadmin.exception.tour_spot.*;
import com.kokotripadmin.exception.trading_hour_type.TradingHourTypeNotFoundException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.util.List;

public interface TourSpotService {
    String getNameById(Integer tourSpotId) throws TourSpotNotFoundException;
    DataTablesOutput<TourSpotDto> findAllByPagination(DataTablesInput dataTablesInput);
    List<LocatableAutoCompleteDto> findAllAsLocatableAutoComplete(String search);
    TourSpotDto findByIdInDetail(Integer tourSpotId) throws TourSpotNotFoundException;
    TourSpotDto findById(Integer tourSpotId) throws TourSpotNotFoundException;
    Integer save(TourSpotDto tourSpotDto)
    throws TourSpotNameDuplicateException, CityNotFoundException, RegionNotFoundException,
            SupportLanguageNotFoundException, TourSpotNotFoundException, TagNotFoundException, TagInfoNotFoundException,
            TourSpotInfoAlreadyExistsException, RegionMismatchException, DayOfWeekNotFoundException,
            TradingHourTypeNotFoundException, TourSpotInfoNotFoundException;

    TourSpotInfoDto saveInfo(TourSpotInfoDto tourSpotInfoDto)
    throws TourSpotNotFoundException, SupportLanguageNotFoundException, TourSpotInfoAlreadyExistsException,
           TourSpotInfoNotFoundException, TagInfoNotFoundException, TourSpotInfoNotEditableException;

    void delete(Integer tourSpotId) throws TourSpotNotFoundException;

    void deleteInfo(Integer tourSpotInfoId) throws TourSpotInfoNotFoundException, TourSpotInfoNotDeletableException;
}
