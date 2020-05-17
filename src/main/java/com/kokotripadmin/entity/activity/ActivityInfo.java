package com.kokotripadmin.entity.activity;

import com.kokotripadmin.entity.common.BaseDescribableInfoEntity;
import com.kokotripadmin.entity.common.SupportLanguage;
import com.kokotripadmin.entity.tag.TagInfo;
import com.kokotripadmin.entity.tourspot.TourSpotInfo;
import com.kokotripadmin.entity.tag.Tag;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "activity_info")
@Getter
@Setter
public class ActivityInfo extends BaseDescribableInfoEntity {

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "\"order\"")
    private Integer order;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "average_rate")
    private double averageRate;

    @Column(name = "number_of_rate")
    private int numberOfRate;

    @Column(name = "popular_score")
    private double popularScore;

    @Column(name = "number_of_wish_list_saved")
    private int numberOfWishListSaved;

    @Column(name = "rep_image_path")
    private String repImagePath;

    @Column(name = "rep_image_file_type")
    private String repImageFileType;

    @Column(name = "tag_name")
    private String tagName;

    @Column(name = "activity_id", insertable = false, updatable = false)
    private Integer activityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_spot_info_id")
    private TourSpotInfo tourSpotInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_info_id")
    private TagInfo tagInfo;

    public void clone(Activity activity) {
        this.name = activity.getName();
        this.description = activity.getDescription();
    }

    @SuppressWarnings("Duplicates")
    public void denormalize(Activity activity) {
        this.enabled = activity.isEnabled();
        this.order = activity.getOrder();
        this.latitude = activity.getLatitude();
        this.longitude = activity.getLongitude();
        this.popularScore = activity.getPopularScore();
        this.averageRate = activity.getAverageRate();
        this.numberOfRate = activity.getNumberOfRate();
        this.numberOfWishListSaved = activity.getNumberOfWishListSaved();
        this.popularScore = activity.getPopularScore();
        this.repImagePath = activity.getRepImagePath();
        this.repImageFileType = activity.getRepImageFileType();
    }

    public void denormalize(Activity activity, String tagName) {
        denormalize(activity);
        this.tagName = tagName;
    }

    public void setForeignEntities(TourSpotInfo tourSpotInfo, Activity activity, TagInfo tagInfo, SupportLanguage supportLanguage) {
        this.tourSpotInfo = tourSpotInfo;
        this.activity = activity;
        this.tagInfo = tagInfo;
        this.supportLanguage = supportLanguage;
    }

}
