package com.kokotripadmin.entity.tourspot;


import com.kokotripadmin.entity.common.DayOfWeek;
import com.kokotripadmin.entity.common.TradingHourType;
import com.kokotripadmin.entity.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "tour_spot_trading_hour")
@Getter
@Setter
public class TourSpotTradingHour extends BaseEntity {

    @Column(name = "day_of_week_id", insertable = false, updatable = false)
    private Integer dayOfWeekId;

    @Column(name = "trading_hour_type_id", insertable = false, updatable = false)
    private Integer tradingHourTypeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_spot_id")
    private TourSpot tourSpot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "day_of_week_id")
    private DayOfWeek dayOfWeek;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trading_hour_type_id")
    private TradingHourType tradingHourType;

    @Column(name = "open_time")
    private Time openTime;

    @Column(name = "close_time")
    private Time closeTime;


    public void setForeignEntities(TourSpot tourSpot, DayOfWeek dayOfWeek, TradingHourType tradingHourType) {
        this.tourSpot = tourSpot;
        this.dayOfWeek = dayOfWeek;
        this.tradingHourType = tradingHourType;
    }

}
